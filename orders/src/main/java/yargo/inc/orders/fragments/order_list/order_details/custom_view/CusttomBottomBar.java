package yargo.inc.orders.fragments.order_list.order_details.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yargo.inc.orders.R2;
import yargo.inc.orders.R;
import yargo.inc.orders.fragments.order_list.order_details.OrderDetailsView;
import yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel;

import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_CLIENT_CANCEL;
import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_IS_ASSIGNED;
import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_IS_DONE;
import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_IS_INWORK;
import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_IS_VACANT;
import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_WAIT_PAY;

public class CusttomBottomBar extends ConstraintLayout {
    @BindView(R2.id.detailBottomBar)
    Button detailBottomBar;

    private OrderDetailsViewModel orderDetailsViewModel;
    public onClickBtnListener listener;

    public interface onClickBtnListener {
        void actionBtnClick();
    }

    public CusttomBottomBar(Context context) {
        super(context);
        init(context);
    }

    public CusttomBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        LayoutInflater.from(context).inflate(R.layout.order_detail_bottom, this);
        ButterKnife.bind(this);
    }

    public void initBottombar() {
        orderDetailsViewModel = OrderDetailsView.getOrderDetailsViewModel();
        switch (orderDetailsViewModel.getOrderStatusId()) {
            case ORDER_IS_VACANT:
                detailBottomBar.setVisibility(VISIBLE);
                detailBottomBar.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                detailBottomBar.setText(getContext().getString(R.string.take_order));
                break;
            case ORDER_IS_ASSIGNED:
                detailBottomBar.setVisibility(VISIBLE);
                detailBottomBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                detailBottomBar.setText(getContext().getString(R.string.start_order));
                break;
            case ORDER_IS_INWORK:
                detailBottomBar.setVisibility(VISIBLE);
                detailBottomBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                detailBottomBar.setText(getContext().getString(R.string.finish_order));
                break;
            case ORDER_WAIT_PAY:
                detailBottomBar.setVisibility(VISIBLE);
                detailBottomBar.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                detailBottomBar.setText(getContext().getString(R.string.order_pay_comission));
                break;
            case ORDER_IS_DONE:
                detailBottomBar.setVisibility(GONE);
                break;
            case ORDER_CLIENT_CANCEL:
                detailBottomBar.setVisibility(GONE);
                break;
        }
    }

    @OnClick(R2.id.detailBottomBar)
    void onActionClick() {
        listener.actionBtnClick();
        switch (orderDetailsViewModel.getOrderStatusId()) {
            case ORDER_IS_VACANT:
                orderDetailsViewModel.actionGetOrder();
                break;
            case ORDER_IS_ASSIGNED:
                orderDetailsViewModel.actionStartOrder();
                break;
            case ORDER_IS_INWORK:
                orderDetailsViewModel.actionAccomplishedOrder();
                break;
            case ORDER_WAIT_PAY:
                break;
        }
    }

    public void setListener(onClickBtnListener listener) {
        this.listener = listener;
    }
}
