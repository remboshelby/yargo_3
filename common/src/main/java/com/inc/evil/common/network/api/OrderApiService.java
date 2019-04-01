package com.inc.evil.common.network.api;

import com.inc.evil.common.network.data.order.OrdersResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OrderApiService {
    @GET("/orders")
    Observable<OrdersResponse> getVacantOrders(@Query("LoginForm[auth_key]") String auth_key,
                                               @Query("app_id") String app_id);
    @GET("/orders/user-orders")
    Observable<OrdersResponse> getUsersOrders(@Query("LoginForm[auth_key]") String auth_key,
                                               @Query("app_id") String app_id);
    @GET("/orders/watch")
    Observable<OrdersResponse> getOrderWatchedCount(@Query("LoginForm[auth_key]") String auth_key,
                                               @Query("app_id") String app_id,
                                               @Query("id_order") String id_order);
}
