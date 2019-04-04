package com.example.orders.fragments.order_list.paging_orders;

import com.inc.evil.common.base.BaseViewModel;
import com.inc.evil.common.network.repository.OrdersRepository;
import com.example.orders.fragments.order_list.paging_orders.data.Order;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class OrderDataModel extends BaseViewModel {
    private static final int PER_PAGE = 20;
    private OrdersRepository ordersRepository;

    private LiveData<PagedList<Order>> orders;
//    private LiveData<Boolean> isLoading;

    public OrderDataModel() {
        OrderDataSourceFactory factory = new OrderDataSourceFactory(ordersRepository, getCompositeDisposable());

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(PER_PAGE)
                .build();

        orders = new LivePagedListBuilder<>(factory, config).build();
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

    public LiveData<PagedList<Order>> getOrders() {
        return orders;
    }
}
