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
import yargo.inc.common.network.models.order.OrdersItem;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderItemView;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.OrdersViewModel;
import yargo.inc.orders.fragments.order_list.vacant_orders.custom_view.CustomToolbarVacantOrders;

public class VacantOrderList extends BaseFragment {


    @BindView(R2.id.imageView2)
    ImageView imageView2;
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
    public OrdersViewModel ordersViewModel;

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
        ordersViewModel.observSearchText(this, this::replaceSubscription);

        customVacantToolbar.setTitle(getString(R.string.vacant_orders));

        ordersItemAdapter = new OrdersItemAdapter();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerOrders.getContext(), layoutManager.getOrientation());
        recyclerOrders.addItemDecoration(dividerItemDecoration);
        recyclerOrders.setLayoutManager(layoutManager);
        recyclerOrders.setAdapter(ordersItemAdapter);

        startListening();
        ordersViewModel.onViewCreated();
    }
    private void startListening(){
        ordersViewModel.getIsLoading().observe(this, VacantOrderList.this::setLoadingState);
        ordersViewModel.getOrders().observe(this, ordersItems -> ordersItemAdapter.submitList(ordersItems));
    }
    public void replaceSubscription(String orderDescription){
        ordersViewModel.replaceSubscription(this);
        startListening();
    }

    public static class OrdersItemAdapter extends PagedListAdapter<OrdersItem, BaseViewHolder<OrdersItem>>  {
        protected OrdersItemAdapter() {
            super(DIFF_CALLBACK);
        }

        @Inject
        public OrdersViewModel ordersViewModel;

        @NonNull
        @Override
        public BaseViewHolder<OrdersItem> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            OrderListsFragment.getOrdersComponent().inject(this);
            return new BaseViewHolder<OrdersItem>(new OrderItemView(parent.getContext())) {
                @Override
                public void bind(OrdersItem item) {
                    ((OrderItemView)itemView).bind(item);
                }
            };
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder<OrdersItem> holder, int position) {
                holder.bind(getItem(position));
        }

    }

    public static final DiffUtil.ItemCallback<OrdersItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<OrdersItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull OrdersItem oldItem, @NonNull OrdersItem newItem) {
            return oldItem.getID() == newItem.getID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull OrdersItem oldItem, @NonNull OrdersItem newItem) {
            return oldItem.getID() == newItem.getID();
        }
    };

    public void setLoadingState(boolean isLoading) {
        if (isLoading) {
            recyclerOrders.setVisibility(ordersItemAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
            imageView2.setVisibility(ordersItemAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
        } else {
            recyclerOrders.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.GONE);
        }
    }
}
