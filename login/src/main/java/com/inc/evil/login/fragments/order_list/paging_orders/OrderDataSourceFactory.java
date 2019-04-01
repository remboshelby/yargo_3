package com.inc.evil.login.fragments.order_list.paging_orders;

import com.inc.evil.common.network.repository.OrdersRepository;
import com.inc.evil.login.fragments.order_list.paging_orders.data.Order;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import io.reactivex.disposables.CompositeDisposable;

public class OrderDataSourceFactory extends DataSource.Factory<Integer, Order> {
    private OrdersRepository ordersRepository;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<OrdersDataSource> dataSourceLiveData;

    public OrderDataSourceFactory(OrdersRepository ordersRepository, CompositeDisposable compositeDisposable) {
        this.ordersRepository = ordersRepository;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public DataSource<Integer, Order> create() {
        OrdersDataSource ordersDataSource = new OrdersDataSource(ordersRepository, compositeDisposable);
        dataSourceLiveData.postValue(ordersDataSource);
        return ordersDataSource;
    }
    public MutableLiveData<OrdersDataSource> getDataSourceLiveData(){
        return dataSourceLiveData;
    }
}
