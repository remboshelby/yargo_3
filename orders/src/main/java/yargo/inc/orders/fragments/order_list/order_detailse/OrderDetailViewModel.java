package yargo.inc.orders.fragments.order_list.order_detailse;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.common.network.repository.OrderActionRepository;

public class OrderDetailViewModel extends BaseViewModel {

    private MutableLiveData<Integer> orderId = new MutableLiveData<>();

    private OrderActionRepository orderActionRepository;

    public OrderDetailViewModel(OrderActionRepository orderActionRepository) {
        this.orderActionRepository = orderActionRepository;
        orderId.setValue(-1);
    }

    public void getOrderDetail(int orderId) {
        addDisposible(orderActionRepository.getOrderDetail(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<OrderDetailResponse>() {
                    @Override
                    public void accept(OrderDetailResponse orderDetailResponse) throws Exception {
                        //TODO добавить Binding во вью
                    }
                }));
    }

    public void startToDoOrder() {

    }

    public void finishOrder() {

    }

    public void orderStopByClientCancel() {

    }

    public void orderStopByUserCancel() {

    }

    public void payCommissionForOrder() {

    }

    public void setOrderId(int orderIdValue) {
        orderId.postValue(orderIdValue);
    }

    public int getOrderId() {
        return orderId.getValue();
    }


}
