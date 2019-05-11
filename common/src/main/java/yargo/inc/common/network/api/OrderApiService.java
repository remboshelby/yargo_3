package yargo.inc.common.network.api;

import yargo.inc.common.network.models.order_list.OrderResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OrderApiService {
    @GET("/orders")
    Observable<OrderResponse> getVacantOrders(@Query("LoginForm[auth_key]") String auth_key,
                                              @Query("app_id") String app_id);
    @GET("/orders/urgent")
    Observable<OrderResponse> getUsersOrders(@Query("LoginForm[auth_key]") String auth_key,
                                             @Query("app_id") String app_id);
    @GET("/orders/watch")
    Observable<OrderResponse> getOrderWatchedCount(@Query("LoginForm[auth_key]") String auth_key,
                                                   @Query("app_id") String app_id,
                                                   @Query("id_order") String id_order);
}
