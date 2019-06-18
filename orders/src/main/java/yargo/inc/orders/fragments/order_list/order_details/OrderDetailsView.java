package yargo.inc.orders.fragments.order_list.order_details;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import yargo.inc.common.base.BaseActivity;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.common.network.models.order_detail.OrdersItem;
import yargo.inc.common.network.repository.OrderActionRepository;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.common.utils.OrderDetailAdapter;
import yargo.inc.orders.fragments.order_list.common.utils.OrderDetailItem;
import yargo.inc.orders.fragments.order_list.instructions.OffertView;
import yargo.inc.orders.fragments.order_list.order_details.custom_view.CustomToolbarOrderDetail;
import yargo.inc.orders.fragments.order_list.order_details.custom_view.CusttomBottomBar;
import yargo.inc.orders.fragments.order_list.user_orders.UserOrdersViewModel;

import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_GET_ISBUSY;
import static yargo.inc.orders.fragments.order_list.user_orders.UserOrdersViewModel.ORDER_IS_ASSIGNED;
import static yargo.inc.orders.fragments.order_list.user_orders.UserOrdersViewModel.ORDER_IS_INWORK;
import static yargo.inc.orders.fragments.order_list.user_orders.UserOrdersViewModel.ORDER_WAIT_PAY;

public class OrderDetailsView extends BaseFragment implements CustomToolbarOrderDetail.onCustomToolbarClick, CusttomBottomBar.onClickBtnListener {
    @BindView(R2.id.customBottomBar)
    CusttomBottomBar customBottomBar;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.progressBar)
    ContentLoadingProgressBar progressBar;
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

    private ProgressDialog progressDialog;

    @Inject
    protected UserOrdersViewModel userOrdersViewModel;
    @Inject
    protected OrderActionRepository orderActionRepository;


    protected static OrderDetailsViewModel orderDetailsViewModel;


    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.order_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);

        orderDetailsViewModel.observOrderDetailData(this, orderDetailResponse -> {
            OrdersItem ordersItem = orderDetailResponse.getResponse().getOrders().get(0);
            customBottomBar.initBottombar();


            customToolbar.setToolbarTitle(getString(R.string.request_number, ordersItem.getID()));

            createOrderRecyclerView(orderDetailResponse);

            bottomNav.setVisibility(View.VISIBLE);
        });
        orderDetailsViewModel.observOrderChangeResult(this, result -> {
            progressDialog.cancel();
            switch (result) {
                case OrderDetailsViewModel.ORDER_GET_SUCCESS:
                    userOrdersViewModel.setOrderCategoryId(ORDER_IS_ASSIGNED);
                    getRoot().onBackPressed();
                    break;
                case OrderDetailsViewModel.ORDER_ACTION_SOMETHING_WRONG:
                    showErrorDialog(getString(R.string.order_cant_take_something_goes_wrong));
                    break;
                case OrderDetailsViewModel.ORDER_GET_FAIL:
                    showErrorDialog(getString(R.string.order_cant_be_taken));
                    break;
                case ORDER_GET_ISBUSY:
                    showErrorDialogExtended(getString(R.string.order_cant_take_other_user_get_it), getRoot());
                    break;
                case OrderDetailsViewModel.ORDER_GET_UFULL:
                    showErrorDialog(getString(R.string.order_cant_take_limit_reached));
                    break;
                case OrderDetailsViewModel.ORDER_STAR_ALREADY_SET:
                    showErrorDialogExtended(getString(R.string.order_status_is_allready_at_work), getRoot());
                    break;
                case OrderDetailsViewModel.ORDER_FINISHED_ALREADY_SET:
                    showErrorDialogExtended(getString(R.string.order_status_is_allready_wait_pay), getRoot());
                    break;
                case OrderDetailsViewModel.ORDER_START_SUCCESS:
                    userOrdersViewModel.setOrderCategoryId(ORDER_IS_INWORK);
                    getRoot().onBackPressed();
                    break;
                case OrderDetailsViewModel.ORDER_FINISED_SUCCESS:
                    userOrdersViewModel.setOrderCategoryId(ORDER_WAIT_PAY);
                    getRoot().onBackPressed();
                    break;


            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void init(@NonNull View view) {
        OrderListsFragment.getOrdersComponent().inject(this);
        ButterKnife.bind(this, view);
        getRoot().setSupportActionBar(toolbar);
        orderDetailsViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new OrderDetailsViewModel(orderActionRepository);
            }
        }).get(OrderDetailsViewModel.class);

        customToolbar.setOnCustomToolbarClick(this);
        customBottomBar.setListener(this);

        progressBar.setVisibility(View.VISIBLE);

        orderDetailsViewModel.getOrderDetail(userOrdersViewModel.getOrderId());
        customToolbar.setToolbarTitle(getString(R.string.loading));

    }

    public void showErrorDialogExtended(String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle("Ошибка");
        builder.setPositiveButton("OK", (dialog, which) -> ((BaseActivity) context).onBackPressed());
        builder.setCancelable(true);
        builder.create().show();
    }

    @Override
    public void actionBtnClick() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.loading));
    }

    @Override
    public void onBackPressed() {
        getRoot().onBackPressed();
    }

    public static OrderDetailsViewModel getOrderDetailsViewModel() {
        return orderDetailsViewModel;
    }

    private void createOrderRecyclerView(OrderDetailResponse orderDetailResponse) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderDetailsView.this.getRoot());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(deatilRecyclerView.getContext(), layoutManager.getOrientation());
        deatilRecyclerView.addItemDecoration(dividerItemDecoration);
        deatilRecyclerView.setLayoutManager(layoutManager);
        deatilRecyclerView.setAdapter(createOrderDetailAdapter(orderDetailResponse));

        showContent();
    }

    private void showContent() {
        bottomNav.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        deatilRecyclerView.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.GONE);
    }

    @NotNull
    private OrderDetailAdapter createOrderDetailAdapter(OrderDetailResponse orderDetailResponse) {
        OrderDetailAdapter orderDetailAdapter;
        ArrayList<OrderDetailItem> list = new ArrayList<>();
        list.add(new OrderDetailItem(OrderDetailItem.HEADER_ITEM_VIEW, orderDetailResponse));
        list.add(new OrderDetailItem(OrderDetailItem.MAP_ITEM_VIEW, orderDetailResponse));
        list.add(new OrderDetailItem(OrderDetailItem.DISCRIPTION_ITEM_VIEW, orderDetailResponse));
        list.add(new OrderDetailItem(OrderDetailItem.PAY_TYPE_ITEM_VIEW, orderDetailResponse));
        list.add(new OrderDetailItem(OrderDetailItem.CLIENT_ABOUT_ITEM_VIEW, orderDetailResponse));

        orderDetailAdapter = new OrderDetailAdapter(list);
        return orderDetailAdapter;
    }

    @Override
    public void onDestroyView() {
        orderDetailsViewModel = null;
        super.onDestroyView();
    }

    public void showOffert() {
        startActivity(new Intent(getContext(), OffertView.class));
    }
}
