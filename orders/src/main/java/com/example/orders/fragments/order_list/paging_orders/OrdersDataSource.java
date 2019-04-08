package com.example.orders.fragments.order_list.paging_orders;

import com.inc.evil.common.network.models.order.OrdersItem;
import com.inc.evil.common.network.repository.OrdersRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PositionalDataSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrdersDataSource extends PositionalDataSource<OrdersItem> {
    private OrdersRepository ordersRepository;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public OrdersDataSource(OrdersRepository ordersRepository, CompositeDisposable compositeDisposable) {
        this.ordersRepository = ordersRepository;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<OrdersItem> callback) {
        isLoading.postValue(true);
//        compositeDisposable.add()
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<OrdersItem> callback) {
        isLoading.postValue(true);
        compositeDisposable.add(ordersRepository.getAllVacantOrders().
                observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(ordersItemList -> {
                    callback.onResult(ordersItemList);
                    isLoading.postValue(false);
                }));
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
