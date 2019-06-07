package yargo.inc.orders.fragments.order_list.profile_editor.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;

public class CustomToolBarEditor extends ConstraintLayout {
    public profileListener listener;

    public interface profileListener{

        void onBackPressed();
        void onAcceptPressed();
    }
    @BindView(R2.id.imgBtnClose)
    AppCompatImageView imgBtnClose;

    @BindView(R2.id.tvToolBarTitle)
    TextView tvToolBarTitle;
    @BindView(R2.id.imgBtnCheck)
    AppCompatImageView imgBtnCheck;
    public CustomToolBarEditor(Context context) {
        super(context);
        init(context);
    }

    public CustomToolBarEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_toolbar_profile, this);
        ButterKnife.bind(this);
        OrderListsFragment.getOrdersComponent().inject(this);
    }

    @OnClick(R2.id.imgBtnClose)
    void onImgBtnCloseClick(){
        listener.onBackPressed();
    }

    @OnClick(R2.id.imgBtnCheck)
    void onAcceptBtnClick(){
        listener.onAcceptPressed();
    }
    public void setBtnCheckVisibility(boolean isVisible){
        if(isVisible) imgBtnCheck.setVisibility(VISIBLE);
        else imgBtnCheck.setVisibility(GONE);
    }
    public void setListener(profileListener listener) {
        this.listener = listener;
    }
}
