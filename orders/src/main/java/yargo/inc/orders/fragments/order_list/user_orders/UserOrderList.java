package yargo.inc.orders.fragments.order_list.user_orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.base.BaseViewHolder;
import yargo.inc.common.network.models.user_order.UserOrdersItem;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.common.OrderItemView;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.user_orders.custom_view.CustomToolbarUserOrders;

public class UserOrderList extends BaseFragment {


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
    protected UserOrdersViewModel ordersViewModel;

    private UserOrdersItemAdapter userOrdersItemAdapter;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        OrderListsFragment.getOrdersComponent().inject(this);
        return inflater.inflate(R.layout.user_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        ordersViewModel.observUserOrderCount(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                imgBanner.setVisibility(count > 0 ? View.GONE : View.VISIBLE);
            }
        });

        ordersViewModel.observOrderCategoryId(this, integer -> replaceSubscription());

        swipeRefreshLayout.setOnRefreshListener(() -> replaceSubscription());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getRoot());
        recyclerUserOrders.setNestedScrollingEnabled(true);
        userOrdersItemAdapter = new UserOrdersItemAdapter();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerUserOrders.getContext(), layoutManager.getOrientation());
        recyclerUserOrders.addItemDecoration(dividerItemDecoration);
        recyclerUserOrders.setLayoutManager(layoutManager);
        recyclerUserOrders.setAdapter(userOrdersItemAdapter);

        startListening();
        ordersViewModel.onViewCreated();
    }
    private void startListening(){
        ordersViewModel.getIsLoading().observe(this, aBoolean -> setLoadingState(aBoolean));
        ordersViewModel.getUserOrders().observe(this, new Observer<PagedList<UserOrdersItem>>() {
            @Override
            public void onChanged(PagedList<UserOrdersItem> userOrdersItems) {
                ordersViewModel.setUserOrdersCount(userOrdersItems.size());
                userOrdersItemAdapter.submitList(userOrdersItems);
            }
        });
    }
    public void replaceSubscription(){
        ordersViewModel.replaceUserOrdersSubscription(this);
        startListening();
    }

    public static class UserOrdersItemAdapter extends PagedListAdapter<UserOrdersItem, BaseViewHolder<UserOrdersItem>>{

        protected UserOrdersItemAdapter() {
            super(DIFF_CALLBACK);
        }


        //TODO добавить событие клика на orderItem
        //TODO +проверку статуса заказа, возможно не прошло обновление статуса заказа
        @NonNull
        @Override
        public BaseViewHolder<UserOrdersItem> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BaseViewHolder<UserOrdersItem>(new OrderItemView(parent.getContext())) {
                @Override
                public void bind(UserOrdersItem item) {
                    ((OrderItemView)itemView).bind(item);
                }
            };
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder<UserOrdersItem> holder, int position) {
            holder.bind(getItem(position));
        }
    }
    public static final DiffUtil.ItemCallback<UserOrdersItem> DIFF_CALLBACK=  new DiffUtil.ItemCallback<UserOrdersItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserOrdersItem oldItem, @NonNull UserOrdersItem newItem) {
            return oldItem.getID() == newItem.getID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserOrdersItem oldItem, @NonNull UserOrdersItem newItem) {
            return oldItem.getID() == newItem.getID();
        }
    };
    public void setLoadingState(boolean isLoading) {
        if (isLoading) {
            swipeRefreshLayout.setRefreshing(true);
//            recyclerUserOrders.setVisibility(userOrdersItemAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            recyclerUserOrders.setVisibility(View.VISIBLE);
            int t =userOrdersItemAdapter.getItemCount();
            imgBanner.setVisibility(userOrdersItemAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);

        }
    }
}
