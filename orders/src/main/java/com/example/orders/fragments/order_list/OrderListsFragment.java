package com.example.orders.fragments.order_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.orders.R;
import com.example.orders.R2;
import com.example.orders.di.DaggerOrdersComponent;
import com.example.orders.di.OrdersComponent;
import com.example.orders.fragments.order_list.OrderList;
import com.example.orders.fragments.order_list.OrdersViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.inc.evil.common.base.BaseFragment;
import com.inc.evil.common.di.CommonApplication;
import com.inc.evil.common.di.CommonComponent;
import com.inc.evil.common.dto.CommonSharedPreferences;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListsFragment extends BaseFragment {


    protected static OrdersComponent ordersComponent;

    @Inject
    protected OrdersViewModel ordersViewModel;
    @Inject
    protected CommonSharedPreferences preferences;

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.list_container)
    FrameLayout list_container;
    @BindView(R2.id.navigationView)
    NavigationView navigationView;
    @BindView(R2.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        pushFragment(new OrderList());
        return inflater.inflate(R.layout.oder_lists_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ButterKnife.bind(this, view);

        getRoot().setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getRoot(), drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        ordersViewModel.fecthVacantOrders();

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.menu_orders) {

            }
            else if (menuItem.getItemId() == R.id.menu_my_orders){

            }
            else if (menuItem.getItemId() == R.id.menu_call){
                    String t ="";
            }
            else if (menuItem.getItemId() == R.id.menu_exit){

            }
            else if (menuItem.getItemId() == R.id.menu_instruction){

            }
            return false;
        });

    }
    public void pushFragment(BaseFragment fragment){
            getRoot().getSupportFragmentManager().beginTransaction()
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .replace(R.id.list_container, fragment)
                    .commit();
    }

    @Override
    protected void inject() {
        CommonApplication commonApplication = (CommonApplication) getRoot().getApplication();

        ordersComponent = DaggerOrdersComponent
                .builder()
                .commonComponent(commonApplication.component())
                .root(this)
                .build();

        ordersComponent.inject(this);
    }
}
