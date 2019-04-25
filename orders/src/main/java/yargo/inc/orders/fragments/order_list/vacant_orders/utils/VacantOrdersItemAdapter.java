package yargo.inc.orders.fragments.order_list.vacant_orders.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import yargo.inc.common.base.BaseViewHolder;
import yargo.inc.common.network.models.vacant_order.VacantOrderItem;
import yargo.inc.orders.fragments.order_list.common.OrderItemView;

public class VacantOrdersItemAdapter extends PagedListAdapter<VacantOrderItem, BaseViewHolder<VacantOrderItem>> {

    public itemClickListener itemClickListener;

    public interface itemClickListener{
        void showItemDetails(VacantOrderItem vacantOrderItem);
    }
    public VacantOrdersItemAdapter(itemClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder<VacantOrderItem> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder<VacantOrderItem>(new OrderItemView(parent.getContext())) {
            @Override
            public void bind(VacantOrderItem item) {
                ((OrderItemView)itemView).bind(item);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<VacantOrderItem> holder, int position) {
        VacantOrderItem vacantOrderItem = getItem(position);
        holder.bind(vacantOrderItem);
        holder.setOnClickListener(v -> itemClickListener.showItemDetails(vacantOrderItem));

    }


    public static final DiffUtil.ItemCallback<VacantOrderItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<VacantOrderItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull VacantOrderItem oldItem, @NonNull VacantOrderItem newItem) {
            return oldItem.getID() == newItem.getID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull VacantOrderItem oldItem, @NonNull VacantOrderItem newItem) {
            return oldItem.getID() == newItem.getID();
        }
    };
}
