package yargo.inc.orders.fragments.order_list.order_detailse;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.common.network.models.order_detail.OrdersItem;
import yargo.inc.common.network.repository.OrderActionRepository;

public class OrderDetailViewModel extends BaseViewModel {

    private MutableLiveData<Integer> orderId = new MutableLiveData<>();
    private MutableLiveData<OrderDetailResponse> orderDetailData = new MutableLiveData<>();

    private OrderActionRepository orderActionRepository;

    public OrderDetailViewModel(OrderActionRepository orderActionRepository) {
        this.orderActionRepository = orderActionRepository;
    }

    public void observOrderDetailData (LifecycleOwner owner, Observer<OrderDetailResponse> observer){
        orderDetailData.observe(owner, observer);
    }

    public void getOrderDetail() {
        addDisposible(orderActionRepository.getOrderDetail(orderId.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<OrderDetailResponse>() {
                               @Override
                               public void accept(OrderDetailResponse value) throws Exception {
                                   orderDetailData.postValue(value);
                               }
                           },
                        throwable -> throwable.printStackTrace()));
    }

    public int getOrderStatus() {
        return orderDetailData.getValue()==null ? -1 : orderDetailData.getValue().getResponse().getOrders().get(0).getIdOrderStatus();
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



}
