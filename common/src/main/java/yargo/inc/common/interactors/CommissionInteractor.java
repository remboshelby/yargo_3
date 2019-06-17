package yargo.inc.common.interactors;

import io.reactivex.Observable;
import yargo.inc.common.base.BaseSharedPreference;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.common.network.repository.OrderActionRepository;

public class CommissionInteractor extends BaseSharedPreference {


    private OrderActionRepository orderActionRepository;

    public CommissionInteractor(OrderActionRepository orderActionRepository, CommonSharedPreferences commonSharedPreferences) {
        this.orderActionRepository = orderActionRepository;
        setCommonSharedPreferences(commonSharedPreferences);
    }
    public Observable<OrderDetailResponse> getOrderDetail(int idOrder) {
        return orderActionRepository.getOrderDetail(idOrder);
    }
}
