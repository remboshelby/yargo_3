package yargo.inc.orders.fragments.order_list;

import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.network.models.order.OrdersItem;
import yargo.inc.common.network.repository.OrdersRepository;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrdersViewModel extends BaseViewModel {
    private OrdersRepository ordersRepository;

    private MutableLiveData<List<OrdersItem>> dataVacanListOrders = new MutableLiveData<>();

    private String appId;
    private String authKey;

    public OrdersViewModel(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void fecthVacantOrders() {
        addDisposible(ordersRepository.getAllVacantOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ordersItemList -> dataVacanListOrders.postValue(ordersItemList),
                        throwable -> throwable.printStackTrace()));
    }

    public void observVacantOrders(LifecycleOwner owner, Observer<List<OrdersItem>> observer) {
        dataVacanListOrders.observe(owner, observer);
    }
}
