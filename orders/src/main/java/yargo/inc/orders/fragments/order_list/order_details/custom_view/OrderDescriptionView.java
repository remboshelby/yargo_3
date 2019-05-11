package yargo.inc.orders.fragments.order_list.order_details.custom_view;

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
import yargo.inc.orders.fragments.order_list.common.utils.OrderDetailItem;

public class OrderDescriptionView extends ConstraintLayout {

    private OrdersItem ordersItem;

    @BindView(R2.id.orderDescription)
    TextView orderDescription;
    @BindView(R2.id.orderComment)
    TextView orderComment;
    @BindView(R2.id.tilteDescription)
    TextView tilteDescription;
    @BindView(R2.id.titleComment)
    TextView titleComment;

    public OrderDescriptionView(Context context) {
        super(context);
        init(context);
    }

    public OrderDescriptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        LayoutInflater.from(context).inflate(R.layout.order_discription, this);
        ButterKnife.bind(this);
    }

    public void bind(OrderDetailItem item) {
        OrderDetailResponse orderDetailResponse = item.getOrderDetailResponse();
        ordersItem = orderDetailResponse.getResponse().getOrders().get(0);

        orderDescription.setText(ordersItem.getDescription());
        orderComment.setText(ordersItem.getComments());
    }
}
