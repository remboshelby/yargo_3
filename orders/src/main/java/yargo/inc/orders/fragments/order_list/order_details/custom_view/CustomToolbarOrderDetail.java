package yargo.inc.orders.fragments.order_list.order_details.custom_view;

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

public class CustomToolbarOrderDetail extends ConstraintLayout {

    public void setOnCustomToolbarClick(onCustomToolbarClick onCustomToolbarClick) {
        this.onCustomToolbarClick = onCustomToolbarClick;
    }

    public onCustomToolbarClick onCustomToolbarClick;

    public interface onCustomToolbarClick {
        void onBackPressed();
    }


    @BindView(R2.id.imgBtnBackPress)
    ImageButton imgBtnBackPress;
    @BindView(R2.id.tvToolBarTitle)
    TextView tvToolBarTitle;

    public CustomToolbarOrderDetail(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.cutstom_order_detail_toolbar, this);
        ButterKnife.bind(this);
    }

    public CustomToolbarOrderDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    @OnClick(R2.id.imgBtnBackPress)
    void onBtnBackPress(){
        onCustomToolbarClick.onBackPressed();
    }
    public void setToolbarTitle (String toolbarTitle){
        tvToolBarTitle.setText(toolbarTitle);
    }
}
