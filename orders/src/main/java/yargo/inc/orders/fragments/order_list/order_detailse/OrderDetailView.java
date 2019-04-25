package yargo.inc.orders.fragments.order_list.order_detailse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import yargo.inc.orders.fragments.order_list.order_detailse.custom_view.CustomToolbarOrderDetail;

public class OrderDetailView extends BaseFragment implements CustomToolbarOrderDetail.onCustomToolbarClick {


    @BindView(R2.id.customToolbar)
    CustomToolbarOrderDetail customToolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.deatilRecyclerView)
    RecyclerView deatilRecyclerView;
    @BindView(R2.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    protected OrderDetailViewModel orderDetailViewModel;
    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.order_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
        super.onViewCreated(view, savedInstanceState);
//        int orderId = orderDetailViewModel.getOrderId();

    }

    private void init(@NonNull View view) {
        OrderListsFragment.getOrdersComponent().inject(this);
        ButterKnife.bind(this,view);
        customToolbar.setOnCustomToolbarClick(this);
    }


    @Override
    public void doSomething() {
        getRoot().onBackPressed();
    }
}
