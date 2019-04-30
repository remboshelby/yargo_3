package yargo.inc.orders.fragments.order_list.order_detailse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.network.models.order_detail.OrdersItem;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.order_detailse.custom_view.CustomToolbarOrderDetail;
import yargo.inc.orders.fragments.order_list.order_detailse.utils.OrderDetailAdapter;
import yargo.inc.orders.fragments.order_list.order_detailse.utils.OrderDetailItem;

public class OrderDetailView extends BaseFragment implements CustomToolbarOrderDetail.onCustomToolbarClick {


    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.progressBar)
    ProgressBar progressBar;
    @BindView(R2.id.bottomNav)
    BottomNavigationView bottomNav;
    @BindView(R2.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R2.id.customToolbar)
    CustomToolbarOrderDetail customToolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.deatilRecyclerView)
    RecyclerView deatilRecyclerView;
    @Inject
    protected OrderDetailViewModel orderDetailViewModel;

    private ArrayList<OrderDetailItem> list = new ArrayList<>();
    private OrderDetailAdapter orderDetailAdapter;

    private static final String TAG = OrderDetailView.class.getSimpleName();

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.order_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
        super.onViewCreated(view, savedInstanceState);

        progressBar.setVisibility(View.VISIBLE);
        bottomNav.setVisibility(View.GONE);

        orderDetailViewModel.getOrderDetail();

        customToolbar.setToolbarTitle("Загрузка...");



        if (orderDetailAdapter!=null) orderDetailAdapter.notifyItemRangeRemoved(0, list.size());

        orderDetailViewModel.observOrderDetailData(this, orderDetailResponse -> {
            OrdersItem ordersItem = orderDetailResponse.getResponse().getOrders().get(0);
            customToolbar.setToolbarTitle("Заявка №"+ ordersItem.getID());

            Log.d(TAG, "observOrderDetailData");

            list.add(new OrderDetailItem(OrderDetailItem.HEADER_ITEM_VIEW, orderDetailResponse));
            list.add(new OrderDetailItem(OrderDetailItem.MAP_ITEM_VIEW, orderDetailResponse));
            list.add(new OrderDetailItem(OrderDetailItem.DISCRIPTION_ITEM_VIEW, orderDetailResponse));
            list.add(new OrderDetailItem(OrderDetailItem.PAY_TYPE_ITEM_VIEW, orderDetailResponse));
            list.add(new OrderDetailItem(OrderDetailItem.CLIENT_ABOUT_ITEM_VIEW, orderDetailResponse));

            LinearLayoutManager layoutManager = new LinearLayoutManager(getRoot());
            orderDetailAdapter = new OrderDetailAdapter(list);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(deatilRecyclerView.getContext(), layoutManager.getOrientation());
            deatilRecyclerView.addItemDecoration(dividerItemDecoration);
            deatilRecyclerView.setLayoutManager(layoutManager);
            deatilRecyclerView.setAdapter(orderDetailAdapter);


            progressBar.setVisibility(View.GONE);
            bottomNav.setVisibility(View.VISIBLE);
        });


    }

    private void init(@NonNull View view) {
        OrderListsFragment.getOrdersComponent().inject(this);
        ButterKnife.bind(this,view);
        customToolbar.setOnCustomToolbarClick(this);
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }

    @Override
    public void doSomething() {
        getRoot().onBackPressed();
    }
}
