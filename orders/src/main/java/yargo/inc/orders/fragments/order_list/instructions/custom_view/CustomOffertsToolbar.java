package yargo.inc.orders.fragments.order_list.instructions.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;

public class CustomOffertsToolbar extends ConstraintLayout {
    @BindView(R2.id.imageButton)
    ImageButton imageButton;
    @BindView(R2.id.tvToobarName)
    TextView tvToobarName;

    private OnCustomOffertsToolbarClick listener;

    public void setOnCustomOffertsToolbarClick(OnCustomOffertsToolbarClick onCustomOffertsToolbarClick){
        this.listener = onCustomOffertsToolbarClick;
    }
    public interface OnCustomOffertsToolbarClick{
        void onBtnClick();
    }

    public CustomOffertsToolbar(Context context) {
        super(context);
        init(context);
    }

    public CustomOffertsToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_offerts_toolbar, this);
        ButterKnife.bind(this);
    }
    public void setTitle(String title){
        tvToobarName.setText(title);
    }
    @OnClick(R2.id.imageButton)
    void onBtnCloseClick(){
        listener.onBtnClick();
    }
}
