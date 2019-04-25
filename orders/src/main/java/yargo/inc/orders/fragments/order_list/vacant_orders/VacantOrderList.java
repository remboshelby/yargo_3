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
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.base.BaseViewHolder;
import yargo.inc.common.network.models.vacant_order.VacantOrderItem;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.common.OrderItemView;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.vacant_orders.custom_view.CustomToolbarVacantOrders;

public class VacantOrderList extends BaseFragment {


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

    private OrdersItemAdapter ordersItemAdapter;

    @Inject
    public VacantOrdersViewModel vacantOrdersViewModel;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        OrderListsFragment.getOrdersComponent().inject(this);
        return inflater.inflate(R.layout.order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getRoot());

//        getRoot().setSupportActionBar(toolbar);
        recyclerOrders.setNestedScrollingEnabled(true);
        vacantOrdersViewModel.observSearchText(this, t -> replaceSubscription());

        customVacantToolbar.setTitle(getString(R.string.vacant_orders));

        ordersItemAdapter = new OrdersItemAdapter();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                replaceSubscription();
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerOrders.getContext(), layoutManager.getOrientation());
        recyclerOrders.addItemDecoration(dividerItemDecoration);
        recyclerOrders.setLayoutManager(layoutManager);
        recyclerOrders.setAdapter(ordersItemAdapter);

        startListening();
        vacantOrdersViewModel.onViewCreated();
    }
    private void startListening(){
        vacantOrdersViewModel.getIsLoading().observe(this, VacantOrderList.this::setLoadingState);
        vacantOrdersViewModel.getVacantOrders().observe(this, ordersItems -> ordersItemAdapter.submitList(ordersItems));
    }
    public void replaceSubscription(){
        vacantOrdersViewModel.replaceVacantSubscription(this);
        startListening();
    }

    public static class OrdersItemAdapter extends PagedListAdapter<VacantOrderItem, BaseViewHolder<VacantOrderItem>>  {
        protected OrdersItemAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public BaseViewHolder<VacantOrderItem> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BaseViewHolder<VacantOrderItem>(new OrderItemView(parent.getContext())) {
                @Override
                public void bind(VacantOrderItem item) {
                    ((OrderItemView)itemView).bind(item);
                }
            };
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder<VacantOrderItem> holder, int position) {
                holder.bind(getItem(position));
        }

    }

    public static final DiffUtil.ItemCallback<VacantOrderItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<VacantOrderItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull VacantOrderItem oldItem, @NonNull VacantOrderItem newItem) {
            return oldItem.getID() == newItem.getID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull VacantOrderItem oldItem, @NonNull VacantOrderItem newItem) {
            return oldItem.getID() == newItem.getID();
        }
    };

    public void setLoadingState(boolean isLoading) {
        if (isLoading) {
            swipeRefreshLayout.setRefreshing(true);
            recyclerOrders.setVisibility(ordersItemAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            recyclerOrders.setVisibility(View.VISIBLE);
            int t = ordersItemAdapter.getItemCount();
            imgBanner.setImageResource(R.drawable.noorder);
            imgBanner.setVisibility(ordersItemAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
        }
    }
}
