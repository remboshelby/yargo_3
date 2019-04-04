package com.example.orders.di;

import com.example.orders.fragments.order_list.OrderListsFragment;
import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.repository.OrdersRepository;
import com.example.orders.fragments.order_list.OrderList;
import com.example.orders.fragments.order_list.OrdersViewModel;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.Provides;
@Module
public class OrdersModule {
    @Provides
    @OrdersScope
    public OrdersViewModel provideOrdersViewModel(OrderListsFragment host,
                                                  final OrdersRepository ordersRepository,
                                                  CommonSharedPreferences commonSharedPreferences){
        return ViewModelProviders.of(host, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @Singleton
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new OrdersViewModel(ordersRepository, commonSharedPreferences);
            }
        }).get(OrdersViewModel.class);
    }
}
