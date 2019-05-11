package yargo.inc.common.network.api;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import yargo.inc.common.network.models.order_action.ActionResponse;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;

public interface OrderActionApiService {
    @GET("/orders/watch")
    Observable<OrderDetailResponse> getOrderDetail(@Query("LoginForm[auth_key]") String auth_key,
                                                   @Query("id_order") int id_order,
                                                   @Query("app_id") String app_id);
    @GET("/orders/take")
    Observable<ActionResponse> actionGetOrder (@Query("LoginForm[auth_key]") String auth_key,
                                               @Query("id_order") int id_order,
                                               @Query("app_id") String app_id);
    @GET("/orders/start")
    Observable<ActionResponse> actionStartOrder (@Query("LoginForm[auth_key]") String auth_key,
                                               @Query("id_order") int id_order,
                                               @Query("app_id") String app_id);
    @GET("/orders/accomplished")
    Observable<ActionResponse> actionAccomplishedOrder (@Query("LoginForm[auth_key]") String auth_key,
                                                 @Query("id_order") int id_order,
                                                 @Query("app_id") String app_id);
}

