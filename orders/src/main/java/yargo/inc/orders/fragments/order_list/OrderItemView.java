package yargo.inc.orders.fragments.order_list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.base.BaseViewHolder;
import yargo.inc.common.network.models.order.OrdersItem;
import yargo.inc.common.utils.AutoResizeTextView;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;

public class OrderItemView extends ConstraintLayout {
    @BindView(R2.id.imgOrderType)
    ImageView imgOrderType;
    @BindView(R2.id.tvOrderAbout)
    TextView tvOrderAbout;
    @BindView(R2.id.tvOrderData)
    TextView tvOrderData;
    @BindView(R2.id.tvOrderPrice)
    AutoResizeTextView tvOrderPrice;
    @BindView(R2.id.imgPayType)
    ImageView imgPayType;
    @BindView(R2.id.pbItemIsLoading)
    ProgressBar pbItemIsLoading;

    OrdersItem ordersItem;

    public OrderItemView(Context context) {
        super(context);
    }

    public OrderItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setOrdersItem(OrdersItem ordersItem) {
        this.ordersItem = ordersItem;
    }


//    @Override
//    public void setOnClickListener(View.OnClickListener listener) {
//        super.setOnClickListener(listener);
//    }


//    public OrderItemView(@NonNull View itemView) {
//        super(itemView);
//        ButterKnife.bind(this, itemView);
//        itemView.setOnClickListener(v -> {
//
//        });
//    }

//    @Override
//    public void bind(OrdersItem item) {
//    }



//            if (ordersItem != null) {
//        holder.tvOrderAbout.setVisibility(View.VISIBLE);
//        holder.pbItemIsLoading.setVisibility(View.GONE);
//
//        holder.tvOrderAbout.setText(ordersItem.getAddress());
//        holder.imgOrderType.setImageResource(ordersViewModel.getIconByOrderType(ordersItem.getIdSpecialization()));
//        holder.tvOrderAbout.setText(ordersItem.getDescription());
//        holder.tvOrderData.setText(ordersViewModel.dateTimeTransformer.dateCreator(ordersItem.getStartworking()) + " - " + ordersViewModel.dateTimeTransformer.dateCreator(ordersItem.getDeadline()));
//        holder.tvOrderPrice.setText(String.valueOf(ordersItem.getPrice()) + Html.fromHtml(" &#x20bd"));
//        holder.imgPayType.setImageResource(ordersItem.getIdPaymentMethod() == 1 ? R.drawable.ic_credit_card_yellow_24dp : android.R.color.transparent);
//        holder.setOrdersItem(ordersItem);
//    } else {
//        holder.tvOrderAbout.setVisibility(View.GONE);
//        holder.pbItemIsLoading.setVisibility(View.VISIBLE);
//    }
}
