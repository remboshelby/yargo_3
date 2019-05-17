package yargo.inc.orders.fragments.order_list.order_details;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
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
import yargo.inc.common.network.models.order_detail.OrderDetailResponse;
import yargo.inc.common.network.models.order_detail.OrdersItem;
import yargo.inc.common.network.repository.OrderActionRepository;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListViewModel;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.common.utils.OrderDetailAdapter;
import yargo.inc.orders.fragments.order_list.common.utils.OrderDetailItem;
import yargo.inc.orders.fragments.order_list.order_details.custom_view.CustomToolbarOrderDetail;
import yargo.inc.orders.fragments.order_list.order_details.custom_view.CusttomBottomBar;

import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_GET_ISBUSY;
import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_IS_ASSIGNED;
import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_IS_INWORK;
import static yargo.inc.orders.fragments.order_list.order_details.OrderDetailsViewModel.ORDER_WAIT_PAY;

public class OrderDetailsView extends BaseFragment implements CustomToolbarOrderDetail.onCustomToolbarClick, CusttomBottomBar.onClickBtnListener {


    @BindView(R2.id.customBottomBar)
    CusttomBottomBar customBottomBar;
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

    private ProgressDialog progressDialog;

    @Inject
    protected OrderActionRepository orderActionRepository;
    @Inject
    protected OrderListViewModel orderListViewModel;


    protected static OrderDetailsViewModel orderDetailsViewModel;

    private ArrayList<OrderDetailItem> list = new ArrayList<>();

    private OrderDetailAdapter orderDetailAdapter;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.order_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);

        orderDetailsViewModel.observOrderDetailData(this, new Observer<OrderDetailResponse>() {
            @Override
            public void onChanged(OrderDetailResponse orderDetailResponse) {
                customBottomBar.initBottombar();


                OrdersItem ordersItem = orderDetailResponse.getResponse().getOrders().get(0);
                customToolbar.setToolbarTitle("Заявка №" + ordersItem.getID());

                list.add(new OrderDetailItem(OrderDetailItem.HEADER_ITEM_VIEW, orderDetailResponse));
                list.add(new OrderDetailItem(OrderDetailItem.MAP_ITEM_VIEW, orderDetailResponse));
                list.add(new OrderDetailItem(OrderDetailItem.DISCRIPTION_ITEM_VIEW, orderDetailResponse));
                list.add(new OrderDetailItem(OrderDetailItem.PAY_TYPE_ITEM_VIEW, orderDetailResponse));
                list.add(new OrderDetailItem(OrderDetailItem.CLIENT_ABOUT_ITEM_VIEW, orderDetailResponse));

                orderDetailAdapter = new OrderDetailAdapter(list);

                LinearLayoutManager layoutManager = new LinearLayoutManager(OrderDetailsView.this.getRoot());
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(deatilRecyclerView.getContext(), layoutManager.getOrientation());
                deatilRecyclerView.addItemDecoration(dividerItemDecoration);
                deatilRecyclerView.setLayoutManager(layoutManager);

                deatilRecyclerView.setAdapter(orderDetailAdapter);

                progressBar.setVisibility(View.GONE);
                bottomNav.setVisibility(View.VISIBLE);
            }
        });
        orderDetailsViewModel.observOrderChangeResult(this, result -> {
            progressDialog.cancel();
            switch (result) {
                case OrderDetailsViewModel.ORDER_GET_SUCCESS:
                    orderListViewModel.setOrderStatusId(ORDER_IS_ASSIGNED);
                    getRoot().onBackPressed();
                    break;
                case OrderDetailsViewModel.ORDER_ACTION_SOMETHING_WRONG:
                    showErrorDialog("Заказ не может быть взят, что то пошло не так!");
                    break;
                case OrderDetailsViewModel.ORDER_GET_FAIL:
                    showErrorDialog("Заказ не может быть взят!");
                    break;
                case ORDER_GET_ISBUSY:
                    showErrorDialogExtended("Заказ не может быть взят, так как уже находится у другого исполнителя!");
                    break;
                case OrderDetailsViewModel.ORDER_GET_UFULL:
                    showErrorDialog("Заказ не может быть взят, так как у вас достигнут лимит заказов!");
                    break;
                case OrderDetailsViewModel.ORDER_STAR_ALREADY_SET:
                    showErrorDialogExtended("Заказ уже переведен в статус \"В РАБОТЕ\"!");
                    break;
                case OrderDetailsViewModel.ORDER_FINISHED_ALREADY_SET:
                    showErrorDialogExtended("Заказ уже переведен в статус \"ОЖИДАЕТ ОПЛАТЫ\"!");
                    break;
                case OrderDetailsViewModel.ORDER_START_SUCCESS:
                    orderListViewModel.setOrderStatusId(ORDER_IS_INWORK);
                    getRoot().onBackPressed();
                    break;
                case OrderDetailsViewModel.ORDER_FINISED_SUCCESS:
                    orderListViewModel.setOrderStatusId(ORDER_WAIT_PAY);
                    getRoot().onBackPressed();
                    break;


            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
    public void showErrorDialogExtended(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setTitle("Ошибка");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getRoot().onBackPressed();
                }
            });
        builder.setCancelable(true);
        builder.create().show();
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

        orderDetailsViewModel.getOrderDetail(orderListViewModel.getOrderId());
        customToolbar.setToolbarTitle("Загрузка...");

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

    @Override
    public void onDestroyView() {
        orderDetailsViewModel = null;
        super.onDestroyView();
    }
}
