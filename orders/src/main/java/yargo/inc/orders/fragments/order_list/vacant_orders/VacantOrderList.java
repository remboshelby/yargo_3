package yargo.inc.orders.fragments.order_list.vacant_orders;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
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
import yargo.inc.orders.fragments.order_list.vacant_orders.custom_view.CustomToolbarVacantOrders;

public class VacantOrderList extends BaseFragment {


    @BindView(R2.id.customVacantToolbar)
    CustomToolbarVacantOrders customVacantToolbar;
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
        ordersViewModel.observSearchText(this, s -> replaceSubscription());

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
        ordersViewModel.getOrders().observe(this, ordersItems -> ordersItemAdapter.submitList(ordersItems));
        ordersViewModel.getIsLoading().observe(this, this::setLoadingState);
    }
    public void replaceSubscription(){
        ordersViewModel.replaceSubscription(this);
        startListening();
    }

    public static class OrdersItemAdapter extends PagedListAdapter<OrdersItem, OrdersItemViewHolder> implements Filterable {
        private List<OrdersItem> orderList;
        private List<OrdersItem> orderListFiltred;

        protected OrdersItemAdapter() {
            super(DIFF_CALLBACK);
        }

        @Inject
        public OrdersViewModel ordersViewModel;

        @NonNull
        @Override
        public OrdersItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            OrderListsFragment.getOrdersComponent().inject(this);
            return new OrdersItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull OrdersItemViewHolder holder, int position) {
            OrdersItem ordersItem = getItem(position);

            if (ordersItem != null) {
                holder.tvOrderAbout.setVisibility(View.VISIBLE);
                holder.pbItemIsLoading.setVisibility(View.GONE);

                holder.tvOrderAbout.setText(ordersItem.getAddress());
                holder.imgOrderType.setImageResource(ordersViewModel.getIconByOrderType(ordersItem.getIdSpecialization()));
                holder.tvOrderAbout.setText(ordersItem.getDescription());
                holder.tvOrderData.setText(ordersViewModel.dateTimeTransformer.dateCreator(ordersItem.getStartworking()) + " - " + ordersViewModel.dateTimeTransformer.dateCreator(ordersItem.getDeadline()));
                holder.tvOrderPrice.setText(String.valueOf(ordersItem.getPrice()) + Html.fromHtml(" &#x20bd"));
                holder.imgPayType.setImageResource(ordersItem.getIdPaymentMethod() == 1 ? R.drawable.ic_credit_card_yellow_24dp : android.R.color.transparent);
                holder.setOrdersItem(ordersItem);
            } else {
                holder.tvOrderAbout.setVisibility(View.GONE);
                holder.pbItemIsLoading.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String charString = constraint.toString();
                    if (charString.isEmpty()){

                    }
                    return null;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    notifyDataSetChanged();
                }
            };
        }
    }

    protected static class OrdersItemViewHolder extends BaseViewHolder<OrdersItem> {
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

        OrdersItem ordersItem;

        public void setOrdersItem(OrdersItem ordersItem) {
            this.ordersItem = ordersItem;
        }


        @Override
        public void setOnClickListener(View.OnClickListener listener) {
            super.setOnClickListener(listener);
        }


        public OrdersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {

            });
        }

        @Override
        public void bind(OrdersItem item) {
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
            progressBarLoadOrders.setVisibility(ordersItemAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
        } else {
            recyclerOrders.setVisibility(View.VISIBLE);
            progressBarLoadOrders.setVisibility(View.GONE);
        }
    }
}
