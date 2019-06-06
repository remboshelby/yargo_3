package yargo.inc.orders.fragments.order_list.instructions;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import yargo.inc.common.base.BaseViewHolder;
import yargo.inc.orders.fragments.order_list.instructions.custom_view.LowItemView;
import yargo.inc.orders.fragments.order_list.instructions.custom_view.MiddleItemView;
import yargo.inc.orders.fragments.order_list.instructions.custom_view.TopItemView;
import yargo.inc.orders.fragments.order_list.instructions.models.SubHeadersItem;

public class OffertAdapter extends RecyclerView.Adapter<BaseViewHolder<SubHeadersItem>> {

    public static final int TOP_LEVEL = 1;
    public static final int MIDDLE_LEVEL = 2;
    public static final int LOW_LEVEL = 3;

    private List<SubHeadersItem> dataSet;

    public OffertAdapter(List<SubHeadersItem> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public BaseViewHolder<SubHeadersItem> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TOP_LEVEL:
                return new BaseViewHolder<SubHeadersItem>(new TopItemView(parent.getContext())) {
                    @Override
                    public void bind(SubHeadersItem item) {
                        ((TopItemView) itemView).bind(item);
                    }
                };
            case MIDDLE_LEVEL:
                return new BaseViewHolder<SubHeadersItem>(new MiddleItemView(parent.getContext())) {
                    @Override
                    public void bind(SubHeadersItem item) {
                        ((MiddleItemView) itemView).bind(item);
                    }
                };
            case LOW_LEVEL:
                return new BaseViewHolder<SubHeadersItem>(new LowItemView(parent.getContext())) {
                    @Override
                    public void bind(SubHeadersItem item) {
                        ((LowItemView) itemView).bind(item);
                    }
                };
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<SubHeadersItem> holder, int position) {
        SubHeadersItem subHeadersItem = dataSet.get(position);
        if (subHeadersItem != null) {
            (holder).bind(subHeadersItem);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (dataSet.get(position).getLevel()) {
            case 1:
                return TOP_LEVEL;
            case 2:
                return MIDDLE_LEVEL;
            case 3:
                return LOW_LEVEL;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
