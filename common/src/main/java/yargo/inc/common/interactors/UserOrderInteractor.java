package yargo.inc.common.interactors;

import io.reactivex.Observable;
import yargo.inc.common.base.BaseSharedPreference;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.app.AppResponse;
import yargo.inc.common.network.repository.OrdersRepository;

public class UserOrderInteractor  extends BaseSharedPreference {
    private OrdersRepository ordersRepository;

    public UserOrderInteractor(OrdersRepository ordersRepository, CommonSharedPreferences commonSharedPreferences) {
        this.ordersRepository = ordersRepository;
        setCommonSharedPreferences(commonSharedPreferences);
    }

    public Observable<AppResponse> pushAppData(String authKey,
                                               String appId, String fcmToken){
      return  ordersRepository.pushAppData(authKey, appId, fcmToken);
    }
    public int getPageSize() {
        return ordersRepository.getPageSize();
    }

    public OrdersRepository getOrdersRepository() {
        return ordersRepository;
    }
}
