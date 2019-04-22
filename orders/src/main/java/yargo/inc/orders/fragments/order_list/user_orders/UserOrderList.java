package yargo.inc.orders.fragments.order_list.user_orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.OrdersViewModel;
import yargo.inc.orders.fragments.order_list.user_orders.custom_view.CustomToolbarUserOrders;

public class UserOrderList extends BaseFragment {


    @BindView(R2.id.imgBanner)
    ImageView imgBanner;
    @BindView(R2.id.customUserToolbar)
    CustomToolbarUserOrders customUserToolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.RecyclerUserOrders)
    RecyclerView RecyclerUserOrders;
    @BindView(R2.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    protected OrdersViewModel ordersViewModel;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.user_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        OrderListsFragment.getOrdersComponent().inject(this);
    }


}
