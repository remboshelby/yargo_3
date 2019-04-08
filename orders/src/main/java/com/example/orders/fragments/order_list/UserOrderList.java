package com.example.orders.fragments.order_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.orders.R;
import com.example.orders.R2;
import com.google.android.material.appbar.AppBarLayout;
import com.inc.evil.common.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserOrderList extends BaseFragment {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.progressBarLoadOrders)
    ProgressBar progressBarLoadOrders;
    @BindView(R2.id.RecyclerUserOrders)
    RecyclerView RecyclerUserOrders;
    @BindView(R2.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.user_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        getRoot().setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.my_orders));
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user_orders, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
