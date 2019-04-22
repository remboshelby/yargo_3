package yargo.inc.orders.di;

import yargo.inc.orders.fragments.order_list.user_orders.UserOrderList;
import yargo.inc.orders.fragments.order_list.user_orders.custom_view.CustomToolbarUserOrders;
import yargo.inc.orders.fragments.order_list.vacant_orders.VacantOrderList;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.common.di.CommonComponent;

import dagger.BindsInstance;
import dagger.Component;
import yargo.inc.orders.fragments.order_list.vacant_orders.custom_view.CustomToolbarVacantOrders;

@OrdersScope
@Component(dependencies = CommonComponent.class, modules = {OrdersModule.class})
public interface OrdersComponent {

    void inject(OrderListsFragment orderListsFragment);
    void inject(VacantOrderList vacantOrderList);
    void inject(VacantOrderList.OrdersItemAdapter ordersItemAdapter);
    void inject(CustomToolbarVacantOrders customToolbarVacantOrders);
    void inject(CustomToolbarUserOrders customToolbarUserOrders);
    void inject(UserOrderList userOrderList);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder root(OrderListsFragment orderListsFragment);

        Builder commonComponent(CommonComponent commonComponent);

        OrdersComponent build();
    }
}
