package yargo.inc.common.network.repository;

import java.util.UUID;

import io.reactivex.Observable;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.api.OrderActionApiService;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;

public class OrderActionRepository {
    private static final String TAG = OrderActionRepository.class.getSimpleName();
    private CommonSharedPreferences commonSharedPreferences;
    private OrderActionApiService orderActionApiService;

    public OrderActionRepository(OrderActionApiService orderActionApiService, CommonSharedPreferences commonSharedPreferences) {
        this.orderActionApiService = orderActionApiService;
    }
    public Observable<OrderDetailResponse> getOrderDetail(int idOrder){
        String appId = UUID.randomUUID().toString();
        String authKey = (String) commonSharedPreferences.getObject(CommonSharedPreferences.AUTH_KEY, String.class);

        return orderActionApiService.getOrderDetail(authKey,idOrder,appId);
    }
}
