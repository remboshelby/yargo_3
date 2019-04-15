package yargo.inc.orders.fragments.order_list;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import yargo.inc.common.di.ApplicationNavigator;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.di.DaggerOrdersComponent;
import yargo.inc.orders.di.OrdersComponent;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.di.CommonApplication;
import yargo.inc.common.dto.CommonSharedPreferences;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.orders.fragments.order_list.user_orders.UserOrderList;
import yargo.inc.orders.fragments.order_list.vacant_orders.VacantOrderList;

public class OrderListsFragment extends BaseFragment {


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
    protected OrdersViewModel ordersViewModel;
    @Inject
    protected CommonSharedPreferences preferences;
    private ApplicationNavigator navigator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int containerResId() {
        return R.id.list_container;
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.oder_lists_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        init();
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

    private void init() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getRoot(), drawerLayout, toolbar_main, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        pushFragmentIntoFragment(new VacantOrderList());

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
}
