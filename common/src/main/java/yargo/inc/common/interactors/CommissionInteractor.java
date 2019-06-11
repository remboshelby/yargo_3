package yargo.inc.common.interactors;

import io.reactivex.Observable;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.common.network.repository.OrderActionRepository;

public class CommissionInteractor {
    private OrderActionRepository orderActionRepository;

    public CommissionInteractor(OrderActionRepository orderActionRepository) {
        this.orderActionRepository = orderActionRepository;
    }
    public Observable<OrderDetailResponse> getOrderDetail(int idOrder) {
        return orderActionRepository.getOrderDetail(idOrder);
    }
}
