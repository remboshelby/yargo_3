package com.example.orders.fragments.order_list;

import com.inc.evil.common.base.BaseViewModel;
import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.models.order.OrdersItem;
import com.inc.evil.common.network.repository.OrdersRepository;

import java.util.List;
import java.util.UUID;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.inc.evil.common.dto.CommonSharedPreferences.AUTH_KEY;

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
