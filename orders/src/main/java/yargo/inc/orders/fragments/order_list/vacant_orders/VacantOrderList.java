package yargo.inc.orders.fragments.order_list.vacant_orders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;

import javax.inject.Inject;

import butterknife.BindView;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.network.models.order_list.OrderItem;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListViewModel;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.order_details.OrderDetailsView;
import yargo.inc.orders.fragments.order_list.vacant_orders.custom_view.CustomToolbarVacantOrders;
import yargo.inc.orders.fragments.order_list.vacant_orders.utils.VacantOrdersItemAdapter;

public class VacantOrderList extends BaseFragment implements VacantOrdersItemAdapter.itemClickListener {
    public static final String TAG = VacantOrderList.class.getSimpleName();
    @BindView(R2.id.imgBanner)
    ImageView imgBanner;
    @BindView(R2.id.customVacantToolbar)
    CustomToolbarVacantOrders customVacantToolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.recyclerOrders)
    RecyclerView recyclerOrders;
    @BindView(R2.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private VacantOrdersItemAdapter vacantOrdersItemAdapter;

    @Inject
    public VacantOrdersViewModel vacantOrdersViewModel;
    @Inject
    protected OrderListViewModel orderListViewModel;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        OrderListsFragment.getOrdersComponent().inject(this);
        return inflater.inflate(R.layout.order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getRoot());

        recyclerOrders.setNestedScrollingEnabled(true);
        vacantOrdersViewModel.observSearchText(this, t -> replaceSubscription());

        customVacantToolbar.setTitle(getString(R.string.vacant_orders));

        vacantOrdersItemAdapter = new VacantOrdersItemAdapter(this);
        swipeRefreshLayout.setOnRefreshListener(() -> replaceSubscription());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerOrders.getContext(), layoutManager.getOrientation());
        recyclerOrders.addItemDecoration(dividerItemDecoration);
        recyclerOrders.setLayoutManager(layoutManager);
        recyclerOrders.setAdapter(vacantOrdersItemAdapter);

        startListening();
        vacantOrdersViewModel.onViewCreated();
    }

    private void startListening() {
        vacantOrdersViewModel.getVacantOrders().observe(this, ordersItems -> vacantOrdersItemAdapter.submitList(ordersItems));
        vacantOrdersViewModel.getIsLoading().observe(this, VacantOrderList.this::setLoadingState);
        vacantOrdersViewModel.observVacantOrderCount(this, count -> {
            Log.d(TAG, " count " + count);
            imgBanner.setVisibility(count > 0 ? View.GONE : View.VISIBLE);
        });
    }

    public void replaceSubscription() {
        vacantOrdersViewModel.replaceVacantSubscription(this);
        startListening();
    }

    public void setLoadingState(boolean isLoading) {
        if (isLoading) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showItemDetails(OrderItem orderItem) {
        orderListViewModel.setOrder(orderItem);
        getRoot().pushFragment(new OrderDetailsView(), true);
    }
}
