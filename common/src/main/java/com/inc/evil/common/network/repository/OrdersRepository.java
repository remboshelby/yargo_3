package com.inc.evil.common.network.repository;

import com.inc.evil.common.network.api.OrderApiService;
import com.inc.evil.common.network.data.order.OrdersResponse;

import io.reactivex.Observable;

public class OrdersRepository {
    private OrderApiService orderApiService;

    public OrdersRepository(OrderApiService orderApiService) {
        this.orderApiService = orderApiService;
    }
    public Observable <OrdersResponse> getVacantOrders(String auth_key, String app_id){
        return orderApiService.getVacantOrders(auth_key, app_id);
    }
    public Observable <OrdersResponse> getUsersOrders(String auth_key, String app_id){
        return orderApiService.getUsersOrders(auth_key, app_id);
    }
    public Observable <OrdersResponse> getOrderWatchedCount(String auth_key, String app_id,String id_order){
        return orderApiService.getOrderWatchedCount(auth_key, app_id, id_order);
    }
}
