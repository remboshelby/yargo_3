package com.inc.evil.login.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.inc.evil.common.base.BaseFragment;
import com.inc.evil.login.R;
import com.inc.evil.login.R2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListsFragment extends BaseFragment {

    
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.list_container)
    FrameLayout list_container;
    @BindView(R2.id.navigationView)
    NavigationView navigationView;
    @BindView(R2.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
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
}
