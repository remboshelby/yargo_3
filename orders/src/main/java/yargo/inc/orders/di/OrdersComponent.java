package yargo.inc.orders.di;

import yargo.inc.orders.fragments.order_list.vacant_orders.VacantOrderList;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.common.di.CommonComponent;

import dagger.BindsInstance;
import dagger.Component;

@OrdersScope
@Component(dependencies = CommonComponent.class, modules = {OrdersModule.class})
public interface OrdersComponent {

    void inject(OrderListsFragment orderListsFragment);
    void inject(VacantOrderList vacantOrderList);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder root(OrderListsFragment orderListsFragment);

        Builder commonComponent(CommonComponent commonComponent);

        OrdersComponent build();
    }
}
