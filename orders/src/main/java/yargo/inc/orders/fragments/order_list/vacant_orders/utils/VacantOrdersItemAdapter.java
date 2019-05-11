package yargo.inc.orders.fragments.order_list.vacant_orders.utils;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import yargo.inc.common.base.BaseViewHolder;
import yargo.inc.common.network.models.order_list.OrderItem;
import yargo.inc.orders.fragments.order_list.common.OrderItemView;

public class VacantOrdersItemAdapter extends PagedListAdapter<OrderItem, BaseViewHolder<OrderItem>> {

    public itemClickListener itemClickListener;

    public interface itemClickListener{
        void showItemDetails(OrderItem orderItem);
    }
    public VacantOrdersItemAdapter(itemClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder<OrderItem> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder<OrderItem>(new OrderItemView(parent.getContext())) {
            @Override
            public void bind(OrderItem item) {
                ((OrderItemView)itemView).bind(item);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<OrderItem> holder, int position) {
        OrderItem orderItem = getItem(position);
        holder.bind(orderItem);
        holder.setOnClickListener(v -> itemClickListener.showItemDetails(orderItem));

    }


    public static final DiffUtil.ItemCallback<OrderItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<OrderItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull OrderItem oldItem, @NonNull OrderItem newItem) {
            return oldItem.getID() == newItem.getID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull OrderItem oldItem, @NonNull OrderItem newItem) {
            return oldItem.getID() == newItem.getID();
        }
    };
}
