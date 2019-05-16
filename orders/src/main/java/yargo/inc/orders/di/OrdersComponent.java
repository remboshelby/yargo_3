package yargo.inc.orders.di;

import android.app.Application;

import yargo.inc.orders.fragments.order_list.filters.FiltersView;
import yargo.inc.orders.fragments.order_list.filters.custom_view.FilterCategory;
import yargo.inc.orders.fragments.order_list.filters.custom_view.FilterCity;
import yargo.inc.orders.fragments.order_list.filters.custom_view.FilterSettings;
import yargo.inc.orders.fragments.order_list.filters.custom_view.utils.CategoryItemView;
import yargo.inc.orders.fragments.order_list.filters.custom_view.utils.CityItemView;
import yargo.inc.orders.fragments.order_list.order_commission.OrderCommissionView;
import yargo.inc.orders.fragments.order_list.order_commission.SuccessTokinizeView;
import yargo.inc.orders.fragments.order_list.order_details.OrderDetailsView;
import yargo.inc.orders.fragments.order_list.profile_editor.ProfileEditorView;
import yargo.inc.orders.fragments.order_list.profile_editor.custom_view.CustomToolBarEditor;
import yargo.inc.orders.fragments.order_list.user_orders.UserOrderList;
import yargo.inc.orders.fragments.order_list.user_orders.utils.UserOrdersItemAdapter;
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
    void inject(CustomToolbarVacantOrders customToolbarVacantOrders);
    void inject(CustomToolbarUserOrders customToolbarUserOrders);
    void inject(UserOrderList userOrderList);
    void inject(OrderDetailsView orderDetailsView);
    void inject(UserOrdersItemAdapter userOrdersItemAdapter);
    void inject(FiltersView filtersView);
    void inject(FilterSettings filterSettings);
    void inject(FilterCategory filterCategory);
    void inject(CategoryItemView categoryItemView);
    void inject(FilterCity filterCity);
    void inject(CityItemView cityItemView);

    void inject(OrderCommissionView orderCommissionView);

    void inject(CustomToolBarEditor customToolBarEditor);

    void inject(ProfileEditorView profileEditorView);

    void inject(SuccessTokinizeView successTokinizeView);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder root(OrderListsFragment orderListsFragment);

        @BindsInstance
        Builder application(Application application);

        Builder commonComponent(CommonComponent commonComponent);

        OrdersComponent build();
    }
}
