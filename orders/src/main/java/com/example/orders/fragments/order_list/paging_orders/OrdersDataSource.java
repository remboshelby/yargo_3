package com.example.orders.fragments.order_list.paging_orders;

import com.inc.evil.common.network.repository.OrdersRepository;
import com.example.orders.fragments.order_list.paging_orders.data.Order;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PositionalDataSource;
import io.reactivex.disposables.CompositeDisposable;

public class OrdersDataSource extends PositionalDataSource<Order> {
    private OrdersRepository ordersRepository;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public OrdersDataSource(OrdersRepository ordersRepository, CompositeDisposable compositeDisposable) {
        this.ordersRepository = ordersRepository;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Order> callback) {

    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Order> callback) {

    }
}
