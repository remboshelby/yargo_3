package yargo.inc.orders.fragments.order_list.order_detailse.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import yargo.inc.common.base.BaseViewHolder;
import yargo.inc.orders.fragments.order_list.order_detailse.custom_view.ClientAboutView;
import yargo.inc.orders.fragments.order_list.order_detailse.custom_view.HeaderItemView;
import yargo.inc.orders.fragments.order_list.order_detailse.custom_view.MapItemView;
import yargo.inc.orders.fragments.order_list.order_detailse.custom_view.OrderDescriptionView;
import yargo.inc.orders.fragments.order_list.order_detailse.custom_view.OrderPayTypeView;

public class OrderDetailAdapter extends RecyclerView.Adapter<BaseViewHolder<OrderDetailItem>> {


    private ArrayList<OrderDetailItem> dataSet;

    public OrderDetailAdapter(ArrayList<OrderDetailItem> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public BaseViewHolder<OrderDetailItem> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case OrderDetailItem.HEADER_ITEM_VIEW:
                return new BaseViewHolder<OrderDetailItem>(new HeaderItemView(parent.getContext())) {
                    @Override
                    public void bind(OrderDetailItem item) {
                        ((HeaderItemView)itemView).bind(item);
                    }
                };
            case OrderDetailItem.MAP_ITEM_VIEW:
                return new BaseViewHolder<OrderDetailItem>(new MapItemView(parent.getContext())) {
                    @Override
                    public void bind(OrderDetailItem item) {
                        ((MapItemView)itemView).bind(item);
                    }
                };
            case OrderDetailItem.DISCRIPTION_ITEM_VIEW:
                return new BaseViewHolder<OrderDetailItem>(new OrderDescriptionView(parent.getContext())) {
                    @Override
                    public void bind(OrderDetailItem item) {
                        ((OrderDescriptionView)itemView).bind(item);
                    }
                };
            case OrderDetailItem.PAY_TYPE_ITEM_VIEW:
                return new BaseViewHolder<OrderDetailItem>(new OrderPayTypeView(parent.getContext())) {
                    @Override
                    public void bind(OrderDetailItem item) {
                        ((OrderPayTypeView)itemView).bind(item);
                    }
                };
            case OrderDetailItem.CLIENT_ABOUT_ITEM_VIEW:
                return new BaseViewHolder<OrderDetailItem>(new ClientAboutView(parent.getContext())) {
                    @Override
                    public void bind(OrderDetailItem item) {
                        ((ClientAboutView)itemView).bind(item);
                    }
                };
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<OrderDetailItem> holder, int position) {
        OrderDetailItem orderDetailItem = dataSet.get(position);
        if (orderDetailItem !=null){
//            switch (orderDetailItem.type){
                (holder).bind(orderDetailItem);
//                case OrderDetailItem.HEADER_ITEM_VIEW:
//                    (holder).bind(orderDetailItem);
//                    break;
//                case OrderDetailItem.MAP_ITEM_VIEW:
//                    (holder).bind(orderDetailItem);
//                    break;
//                case OrderDetailItem.DISCRIPTION_ITEM_VIEW:
//                    (holder).bind(orderDetailItem);
//                    break;
//                case OrderDetailItem.PAY_TYPE_ITEM_VIEW:
//                    (holder).bind(orderDetailItem);
//                    break;
//                case OrderDetailItem.CLIENT_ABOUT_ITEM_VIEW:
//                    (holder).bind(orderDetailItem);
//                    break;
//            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (dataSet.get(position).type) {
            case 0:
                return OrderDetailItem.HEADER_ITEM_VIEW;
            case 1:
                return OrderDetailItem.MAP_ITEM_VIEW;
            case 2:
                return OrderDetailItem.DISCRIPTION_ITEM_VIEW;
            case 3:
                return OrderDetailItem.PAY_TYPE_ITEM_VIEW;
            case 4:
                return OrderDetailItem.CLIENT_ABOUT_ITEM_VIEW;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class HeaderViewHolder extends BaseViewHolder{

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bind(Object item) {

        }
    }
    public static class MapItemViewHolder extends BaseViewHolder{

        public MapItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bind(Object item) {

        }
    }
    public static class DiscriptionItemViewHolder extends BaseViewHolder{

        public DiscriptionItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bind(Object item) {

        }
    }
    public static class PayTypeItemViewHolder extends BaseViewHolder{

        public PayTypeItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bind(Object item) {

        }
    }
    public static class ClientAboutItemViewHolder extends BaseViewHolder {
        public ClientAboutItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bind(Object item) {

        }
    }
}