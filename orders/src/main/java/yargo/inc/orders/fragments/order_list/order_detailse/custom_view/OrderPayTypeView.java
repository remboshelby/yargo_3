package yargo.inc.orders.fragments.order_list.order_detailse.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.common.network.models.order_detail.OrdersItem;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.order_detailse.utils.OrderDetailItem;

public class OrderPayTypeView extends ConstraintLayout {

    @BindView(R2.id.tvPayType)
    TextView tvPayType;
    @BindView(R2.id.tvPhoneNumber)
    TextView tvPhoneNumber;

    private OrdersItem ordersItem;

    public OrderPayTypeView(Context context) {
        super(context);
        init(context);
    }

    public OrderPayTypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        LayoutInflater.from(context).inflate(R.layout.order_pay_info, this);
        ButterKnife.bind(this);
    }

    public void bind(OrderDetailItem item) {
        OrderDetailResponse orderDetailResponse = item.getOrderDetailResponse();
        ordersItem = orderDetailResponse.getResponse().getOrders().get(0);

        tvPayType.setText(ordersItem.getIdPaymentMethod() == 1 ? "Наличный расчет" : "Безналичный расчет");
        tvPhoneNumber.setVisibility(GONE);
    }
}
