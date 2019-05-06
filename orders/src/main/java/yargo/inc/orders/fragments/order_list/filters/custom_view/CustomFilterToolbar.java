package yargo.inc.orders.fragments.order_list.filters.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yargo.inc.orders.R2;
import yargo.inc.orders.R;

public class CustomFilterToolbar extends ConstraintLayout {

    @BindView(R2.id.imageButton)
    ImageButton imageButton;
    @BindView(R2.id.tvToobarName)
    TextView tvToobarName;
//    @BindView(R2.id.BtnConfirmFilter)
//    Button BtnConfirmFilter;

    public void setOnCustomFilterToolbarClick(onCustomFilterToolbarClick onCustomFilterToolbarClick) {
        this.onCustomFilterToolbarClick = onCustomFilterToolbarClick;
    }

    public onCustomFilterToolbarClick onCustomFilterToolbarClick;

    public interface onCustomFilterToolbarClick{
        void btnBackClick();
    }


    public CustomFilterToolbar(Context context) {
        super(context);
        init(context);
    }

    public CustomFilterToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public void setTitle(String title){
        tvToobarName.setText(title);
    }
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_filter_toolbar, this);
        ButterKnife.bind(this);
    }
    @OnClick(R2.id.imageButton)
    void onBtnCloseClick(){
        onCustomFilterToolbarClick.btnBackClick();
    }

}
