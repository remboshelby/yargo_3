package yargo.inc.orders.fragments.order_list.order_detailse.utils;

import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.common.network.repository.OrderActionRepository;

public class OrderDetailItem {
        public static final int  HEADER_ITEM_VIEW = 0;
        public static final int  MAP_ITEM_VIEW = 1;
        public static final int  DISCRIPTION_ITEM_VIEW = 2;
        public static final int  PAY_TYPE_ITEM_VIEW = 3;
        public static final int  CLIENT_ABOUT_ITEM_VIEW =4;

        public int type;
        public OrderDetailResponse orderDetailResponse;

        public OrderDetailItem(int type, OrderDetailResponse orderDetailResponse) {
                this.type = type;
                this.orderDetailResponse = orderDetailResponse;
        }

        public OrderDetailResponse getOrderDetailResponse() {
                return orderDetailResponse;
        }
}
