package com.inc.evil.login.fragments.order_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.inc.evil.common.base.BaseFragment;
import com.inc.evil.common.di.CommonApplication;
import com.inc.evil.login.R;
import com.inc.evil.login.R2;
import com.inc.evil.login.di.DaggerLoginComponent;
import com.inc.evil.login.di.LoginComponent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderList extends BaseFragment {

    protected static LoginComponent loginComponent;

    @BindView(R2.id.progressBarLoadOrders)
    ProgressBar progressBarLoadOrders;
    @BindView(R2.id.RecyclerOrders)
    RecyclerView RecyclerOrders;
    @BindView(R2.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
    }

    @Override
    protected void inject() {
//        CommonApplication application = (CommonApplication)getRoot().getApplication();
//
//        loginComponent = DaggerLoginComponent
//                .builder()
//                .commonComponent(application.component())
//                .root(this)
//                .build();
//        loginComponent.inject(this);
    }
}
