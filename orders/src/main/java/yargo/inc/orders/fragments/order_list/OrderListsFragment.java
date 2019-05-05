package yargo.inc.orders.fragments.order_list;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.di.ApplicationNavigator;
import yargo.inc.common.di.CommonApplication;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.di.DaggerOrdersComponent;
import yargo.inc.orders.di.OrdersComponent;
import yargo.inc.orders.fragments.order_list.filters.FiltersView;
import yargo.inc.orders.fragments.order_list.order_detailse.OrderDetailViewModel;
import yargo.inc.orders.fragments.order_list.user_orders.UserOrderList;
import yargo.inc.orders.fragments.order_list.user_orders.UserOrdersViewModel;
import yargo.inc.orders.fragments.order_list.vacant_orders.VacantOrdersViewModel;
import yargo.inc.orders.fragments.order_list.vacant_orders.VacantOrderList;

public class OrderListsFragment extends BaseFragment {

    private static final String TAG  = OrderListsFragment.class.getName();

    @BindView(R2.id.imgBtnMap)
    ImageButton imgBtnMap;
    @BindView(R2.id.imgBtnFilter)
    ImageButton imgBtnFilter;
    @BindView(R2.id.toolbar_main)
    Toolbar toolbar_main;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.list_container)
    FrameLayout list_container;
    @BindView(R2.id.navigationView)
    NavigationView navigationView;
    @BindView(R2.id.drawerLayout)
    DrawerLayout drawerLayout;
    protected static OrdersComponent ordersComponent;

    @Inject
    protected OrderDetailViewModel orderDetailViewModel;
    @Inject
    protected VacantOrdersViewModel vacantOrdersViewModel;
    @Inject
    protected CommonSharedPreferences preferences;
    private ApplicationNavigator navigator;

    @Override
    protected int containerResId() {
        return R.id.list_container;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.oder_lists_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getRoot(), drawerLayout, toolbar_main, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (orderDetailViewModel.getOrderStatus()==-1 || orderDetailViewModel.getOrderStatus()==1){
            pushFragmentIntoFragment(new VacantOrderList());
        }
        else {
            pushFragmentIntoFragment(new UserOrderList());
        }

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.menu_orders) {
                pushFragmentIntoFragment(new VacantOrderList());
            } else if (menuItem.getItemId() == R.id.menu_my_orders) {
                pushFragmentIntoFragment(new UserOrderList());
            } else if (menuItem.getItemId() == R.id.menu_call) {

            } else if (menuItem.getItemId() == R.id.menu_exit) {
                showExitDialog();
            } else if (menuItem.getItemId() == R.id.menu_instruction) {

            }
            drawerLayout.closeDrawer(Gravity.LEFT);
            return false;
        });
    }

    public static OrdersComponent getOrdersComponent() {
        return ordersComponent;
    }

    private void showExitDialog() {
        AlertDialog.Builder accoutExitDialog = new AlertDialog.Builder(getRoot());
        accoutExitDialog.setMessage(getResources().getString(R.string.exit_question)).setCancelable(false).
                setPositiveButton(getResources().getString(R.string.YES), (dialog, which) -> {
                    preferences.putObject(CommonSharedPreferences.AUTH_KEY, "");
                    navigator.openFragment(getRoot(), "Login");
                })
                .setNegativeButton(getResources().getString(R.string.NO), (dialog, which) -> dialog.cancel()).create();

        accoutExitDialog.setTitle(getResources()
                .getString(R.string.menu_exit))
                .show();
    }

    @OnClick(R2.id.imgBtnMap)
    void onBtnMapClick(){

    }
    @OnClick(R2.id.imgBtnFilter)
    void onImgBtnFilter(){
        getRoot().pushFragment(new FiltersView(), true);
    }

    @Override
    protected void inject() {
        CommonApplication commonApplication = (CommonApplication) getRoot().getApplication();
        navigator = commonApplication.component().navigator();
        ordersComponent = DaggerOrdersComponent
                .builder()
                .commonComponent(commonApplication.component())
                .root(this)
                .build();

        ordersComponent.inject(this);
    }
}
