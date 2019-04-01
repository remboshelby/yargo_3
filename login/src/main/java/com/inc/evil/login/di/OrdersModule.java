package com.inc.evil.login.di;

import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.repository.OrdersRepository;
import com.inc.evil.login.fragments.order_list.OrderList;
import com.inc.evil.login.fragments.order_list.OrdersViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.Provides;
@Module
public class OrdersModule {
    @Provides
    @LoginScope
    public OrdersViewModel provideOrdersViewModel(OrderList host,
                                                  final OrdersRepository ordersRepository,
                                                  CommonSharedPreferences commonSharedPreferences){
        return ViewModelProviders.of(host, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new OrdersViewModel(ordersRepository, commonSharedPreferences);
            }
        }).get(OrdersViewModel.class);
    }
}
