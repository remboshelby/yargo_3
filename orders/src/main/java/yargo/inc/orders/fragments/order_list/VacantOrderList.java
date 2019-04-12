package yargo.inc.orders.fragments.order_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.google.android.material.appbar.AppBarLayout;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.network.models.order.OrdersItem;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;

public class VacantOrderList extends BaseFragment {


    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.progressBarLoadOrders)
    ProgressBar progressBarLoadOrders;
    @BindView(R2.id.RecyclerOrders)
    RecyclerView RecyclerOrders;
    @BindView(R2.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    @Inject
    OrdersViewModel ordersViewModel;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        OrderListsFragment.ordersComponent.inject(this);
        return inflater.inflate(R.layout.order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        getRoot().setSupportActionBar(toolbar);

        toolbar.setTitle(getString(R.string.vacant_orders));

        ordersViewModel.fecthVacantOrders();
        ordersViewModel.observVacantOrders(this, new Observer<List<OrdersItem>>() {
            @Override
            public void onChanged(List<OrdersItem> ordersItemList) {
                String t = "fdfsdf";
            }
        });
    }

}
