package yargo.inc.common.network.api;

import yargo.inc.common.network.models.user_order.UserOrderResponse;
import yargo.inc.common.network.models.user_order.UserOrdersItem;
import yargo.inc.common.network.models.vacant_order.OrdersResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OrderApiService {
    @GET("/orders")
    Observable<OrdersResponse> getVacantOrders(@Query("LoginForm[auth_key]") String auth_key,
                                               @Query("app_id") String app_id);
    @GET("/orders/urgent")
    Observable<UserOrderResponse> getUsersOrders(@Query("LoginForm[auth_key]") String auth_key,
                                                 @Query("app_id") String app_id);
    @GET("/orders/watch")
    Observable<OrdersResponse> getOrderWatchedCount(@Query("LoginForm[auth_key]") String auth_key,
                                               @Query("app_id") String app_id,
                                               @Query("id_order") String id_order);
}
