package com.example.orders.di;

import com.example.orders.fragments.order_list.OrderList;
import com.example.orders.fragments.order_list.OrderListsFragment;
import com.inc.evil.common.di.CommonComponent;

import dagger.BindsInstance;
import dagger.Component;

@OrdersScope
@Component(dependencies = CommonComponent.class, modules = {OrdersModule.class})
public interface OrdersComponent {

    void inject(OrderListsFragment orderListsFragment);
    void inject(OrderList orderList);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder root(OrderListsFragment orderListsFragment);

        Builder commonComponent(CommonComponent commonComponent);

        OrdersComponent build();
    }
}
