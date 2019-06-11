package yargo.inc.orders.fragments.order_list.user_orders;

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
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.order_commission.OrderCommissionView;
import yargo.inc.orders.fragments.order_list.order_details.OrderDetailsView;
import yargo.inc.orders.fragments.order_list.user_orders.custom_view.CustomToolbarUserOrders;
import yargo.inc.orders.fragments.order_list.user_orders.utils.UserOrdersItemAdapter;

import static yargo.inc.orders.fragments.order_list.user_orders.UserOrdersViewModel.ORDER_WAIT_PAY;

public class UserOrderList extends BaseFragment implements UserOrdersItemAdapter.itemClickListener {
    private static final String TAG = UserOrderList.class.getSimpleName();

    @BindView(R2.id.recyclerUserOrders)
    RecyclerView recyclerUserOrders;
    @BindView(R2.id.imgBanner)
    ImageView imgBanner;
    @BindView(R2.id.customUserToolbar)
    CustomToolbarUserOrders customUserToolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    protected UserOrdersViewModel userOrdersViewModel;

    private UserOrdersItemAdapter userOrdersItemAdapter;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        OrderListsFragment.getOrdersComponent().inject(this);
        return inflater.inflate(R.layout.user_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userOrdersViewModel.observOrderCategoryId(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("setOrderCategoryId", "observOrderCategoryId " + integer);
                UserOrderList.this.replaceSubscription();
                customUserToolbar.setCustomSelection(integer);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                imgBanner.setVisibility(View.GONE);
                UserOrderList.this.replaceSubscription();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getRoot());
        recyclerUserOrders.setNestedScrollingEnabled(true);

        userOrdersItemAdapter = new UserOrdersItemAdapter(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerUserOrders.getContext(), layoutManager.getOrientation());
        recyclerUserOrders.addItemDecoration(dividerItemDecoration);
        recyclerUserOrders.setLayoutManager(layoutManager);
        recyclerUserOrders.setAdapter(userOrdersItemAdapter);

        userOrdersViewModel.onViewCreated();
    }

    private void startListening() {
        userOrdersViewModel.getIsLoading().observe(this, aBoolean -> setLoadingState(aBoolean));
        userOrdersViewModel.getUserOrders().observe(this, userOrdersItems -> userOrdersItemAdapter.submitList(userOrdersItems));
        userOrdersViewModel.observUserOrderCount(this, count -> {
            imgBanner.setVisibility(count > 0 ? View.GONE : View.VISIBLE);
        });
    }

    public void replaceSubscription() {
        userOrdersViewModel.replaceUserOrdersSubscription(this);
        startListening();
    }

    @Override
    public void showItemDetails(OrderItem userOrdersItem) {
        userOrdersViewModel.setOrder(userOrdersItem);
        if (userOrdersItem.getIdOrderStatus() != ORDER_WAIT_PAY) {
            getRoot().pushFragment(new OrderDetailsView(), true);
        } else {
            getRoot().pushFragment(new OrderCommissionView(), true);
        }
    }

    public void setLoadingState(boolean isLoading) {
        if (isLoading) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
