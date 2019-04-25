package yargo.inc.orders.fragments.order_list.user_orders.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import yargo.inc.common.base.BaseViewHolder;
import yargo.inc.common.network.models.user_order.UserOrdersItem;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.common.OrderItemView;

public class UserOrdersItemAdapter extends PagedListAdapter<UserOrdersItem, BaseViewHolder<UserOrdersItem>> {

    public UserOrdersItemAdapter(itemClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    public itemClickListener itemClickListener;

    public interface itemClickListener {
        void showItemDetails(UserOrdersItem userOrdersItem);
    }
    //TODO добавить событие клика на orderItem
    //TODO +проверку статуса заказа, возможно не прошло обновление статуса заказа

    @NonNull
    @Override
    public BaseViewHolder<UserOrdersItem> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderListsFragment.getOrdersComponent().inject(this);
        return new BaseViewHolder<UserOrdersItem>(new OrderItemView(parent.getContext())) {
            @Override
            public void bind(UserOrdersItem item) {
                ((OrderItemView) itemView).bind(item);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<UserOrdersItem> holder, int position) {
        UserOrdersItem userOrdersItem = getItem(position);
        holder.bind(userOrdersItem);
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.showItemDetails(userOrdersItem);
            }
        });

    }

    public static final DiffUtil.ItemCallback<UserOrdersItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserOrdersItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserOrdersItem oldItem, @NonNull UserOrdersItem newItem) {
            return oldItem.getID() == newItem.getID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserOrdersItem oldItem, @NonNull UserOrdersItem newItem) {
            return oldItem.getID() == newItem.getID();
        }
    };
}