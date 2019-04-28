package yargo.inc.orders.fragments.order_list.vacant_orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.appbar.AppBarLayout;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.network.models.vacant_order.VacantOrderItem;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.order_detailse.OrderDetailViewModel;
import yargo.inc.orders.fragments.order_list.vacant_orders.custom_view.CustomToolbarVacantOrders;
import yargo.inc.orders.fragments.order_list.vacant_orders.utils.VacantOrdersItemAdapter;

public class VacantOrderList extends BaseFragment implements VacantOrdersItemAdapter.itemClickListener {


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
    protected OrderDetailViewModel orderDetailViewModel;


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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                replaceSubscription();
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerOrders.getContext(), layoutManager.getOrientation());
        recyclerOrders.addItemDecoration(dividerItemDecoration);
        recyclerOrders.setLayoutManager(layoutManager);
        recyclerOrders.setAdapter(vacantOrdersItemAdapter);

        startListening();

        vacantOrdersViewModel.onViewCreated();
    }
    private void startListening(){
        vacantOrdersViewModel.getIsLoading().observe(this, VacantOrderList.this::setLoadingState);
        vacantOrdersViewModel.getVacantOrders().observe(this, ordersItems -> vacantOrdersItemAdapter.submitList(ordersItems));
    }
    public void replaceSubscription(){
        vacantOrdersViewModel.replaceVacantSubscription(this);
        startListening();
    }

    public void setLoadingState(boolean isLoading) {
        if (isLoading) {
            swipeRefreshLayout.setRefreshing(true);
         } else {
            swipeRefreshLayout.setRefreshing(false);

            imgBanner.setImageResource(R.drawable.noorder);
            imgBanner.setVisibility(vacantOrdersItemAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void showItemDetails(VacantOrderItem vacantOrderItem) {

    }
}
