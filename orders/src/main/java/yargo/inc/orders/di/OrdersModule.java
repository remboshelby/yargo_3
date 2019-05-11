package yargo.inc.orders.di;

import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.orders.fragments.order_list.OrderListViewModel;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.common.network.repository.OrdersRepository;
import yargo.inc.orders.fragments.order_list.filters.FiltersViewModel;
import yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel;
import yargo.inc.orders.fragments.order_list.user_orders.UserOrdersViewModel;
import yargo.inc.orders.fragments.order_list.vacant_orders.VacantOrdersViewModel;

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
    public VacantOrdersViewModel provideOrdersViewModel(OrderListsFragment host,
                                                        final OrdersRepository ordersRepository){
        return ViewModelProviders.of(host, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @Singleton
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new VacantOrdersViewModel(ordersRepository);
            }
        }).get(VacantOrdersViewModel.class);
    }
    @Provides
    @OrdersScope
    public UserOrdersViewModel provideUserOrdersViewModel(OrderListsFragment host,
                                                          final OrdersRepository ordersRepository){
        return ViewModelProviders.of(host, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @Singleton
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new UserOrdersViewModel(ordersRepository);
            }
        }).get(UserOrdersViewModel.class);
    }
    @Provides
    @OrdersScope
    public OrderListViewModel provideOrderListViewModel(OrderListsFragment host,
                                                           CommonSharedPreferences commonSharedPreferences){
        return ViewModelProviders.of(host, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @Singleton
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new OrderListViewModel(commonSharedPreferences);
            }
        }).get(OrderListViewModel.class);
    }
    @Provides
    @OrdersScope
    public FiltersViewModel provideFiltersViewModel(OrderListsFragment host,
                                                    CommonSharedPreferences commonSharedPreferences){
        return ViewModelProviders.of(host, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @Singleton
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new FiltersViewModel(commonSharedPreferences);
            }
        }).get(FiltersViewModel.class);
    }
}
