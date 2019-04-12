package yargo.inc.orders.di;

import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.common.network.repository.OrdersRepository;
import yargo.inc.orders.fragments.order_list.OrdersViewModel;

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
                                                  final OrdersRepository ordersRepository){
        return ViewModelProviders.of(host, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @Singleton
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new OrdersViewModel(ordersRepository);
            }
        }).get(OrdersViewModel.class);
    }
}
