package yargo.inc.orders.fragments.order_list.vacant_orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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
import yargo.inc.common.utils.AutoResizeTextView;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.OrdersViewModel;

public class VacantOrderList extends BaseFragment {


    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.progressBarLoadOrders)
    ProgressBar progressBarLoadOrders;
    @BindView(R2.id.recyclerOrders)
    RecyclerView recyclerOrders;
    @BindView(R2.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private OrdersItemAdapter ordersItemAdapter;

    @Inject
    OrdersViewModel ordersViewModel;

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

        getRoot().setSupportActionBar(toolbar);

        toolbar.setTitle(getString(R.string.vacant_orders));

        ordersItemAdapter = new OrdersItemAdapter();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerOrders.getContext(), layoutManager.getOrientation());
        recyclerOrders.addItemDecoration(dividerItemDecoration);
        recyclerOrders.setLayoutManager(layoutManager);
        recyclerOrders.setAdapter(ordersItemAdapter);

        ordersViewModel.getOrders().observe(this, ordersItems -> ordersItemAdapter.submitList(ordersItems));
        ordersViewModel.getIsLoading().observe(this, this::setLoadingState);
        ordersViewModel.onViewCreated();
    }

    public static class OrdersItemAdapter extends PagedListAdapter<OrdersItem, OrdersItemViewHolder>{



        protected OrdersItemAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public OrdersItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new OrdersItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull OrdersItemViewHolder holder, int position) {
            OrdersItem ordersItem = getItem(position);

            if(ordersItem!=null) {
                holder.tvOrderAbout.setVisibility(View.VISIBLE);
                holder.pbItemIsLoading.setVisibility(View.GONE);

                holder.tvOrderAbout.setText(ordersItem.getAddress());

            }
            else {
                holder.tvOrderAbout.setVisibility(View.GONE);
                holder.pbItemIsLoading.setVisibility(View.VISIBLE);
            }
        }
    }

    protected static class OrdersItemViewHolder extends BaseViewHolder<OrdersItem>{
        @BindView(R2.id.imgOrderType)
        ImageView imgOrderType;
        @BindView(R2.id.tvOrderAbout)
        TextView tvOrderAbout;
        @BindView(R2.id.tvOrderData)
        TextView tvOrderData;
        @BindView(R2.id.tvOrderPrice)
        AutoResizeTextView tvOrderPrice;
        @BindView(R2.id.imgPayType)
        ImageView imgPayType;
        @BindView(R2.id.pbItemIsLoading)
        ProgressBar pbItemIsLoading;

        public OrdersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        @Override
        public void bind(OrdersItem item) {
//            tvOrderAbout.setText(item.getAddress());
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
    public void setLoadingState(boolean isLoading){
        if (isLoading){
            recyclerOrders.setVisibility(ordersItemAdapter.getItemCount()>0 ? View.VISIBLE : View.GONE);
            progressBarLoadOrders.setVisibility(ordersItemAdapter.getItemCount()>0 ? View.GONE : View.VISIBLE);
        }else {
            recyclerOrders.setVisibility(View.VISIBLE);
            progressBarLoadOrders.setVisibility(View.GONE);
        }
    }
}
