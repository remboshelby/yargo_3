package yargo.inc.common.network.repository;

import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.api.ComissionApiService;
import yargo.inc.common.network.api.OrderActionApiService;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;

public class CommissionRepository {
    private CommonSharedPreferences commonSharedPreferences;
    private OrderActionApiService orderActionApiService;
    private ComissionApiService comissionApiService;

    public CommissionRepository(CommonSharedPreferences commonSharedPreferences, OrderActionApiService orderActionApiService, ComissionApiService comissionApiService) {
        this.commonSharedPreferences = commonSharedPreferences;
        this.orderActionApiService = orderActionApiService;
        this.comissionApiService = comissionApiService;
    }

    public Observable<OrderDetailResponse> getOrderDetail(int idOrder){
        String appId = UUID.randomUUID().toString();
        String authKey = (String) commonSharedPreferences.getObject(CommonSharedPreferences.AUTH_KEY, String.class);

        return orderActionApiService.getOrderDetail(authKey,idOrder,appId).doOnNext(new Consumer<OrderDetailResponse>() {
            @Override
            public void accept(OrderDetailResponse orderDetailResponse) throws Exception {
                pushAuthToken(orderDetailResponse.getResponse().getAuthKey());
            }
        });
    }
    public void pushAuthToken(String authKey){
        commonSharedPreferences.putObject(CommonSharedPreferences.AUTH_KEY, authKey);
    }
}
