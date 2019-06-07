package yargo.inc.orders.fragments.order_list.order_commission;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.appbar.AppBarLayout;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Provides;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.interactors.CommissionInteractor;
import yargo.inc.common.interactors.DateInteractor;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListViewModel;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.order_commission.fragments.CommissionDetailView;
import yargo.inc.orders.fragments.order_list.order_commission.fragments.SuccessTokinizeView;
import yargo.inc.orders.fragments.order_list.order_details.custom_view.CustomToolbarOrderDetail;

public class OrderCommissionView extends BaseFragment implements CustomToolbarOrderDetail.onCustomToolbarClick {
    @BindView(R2.id.customToolbar)
    CustomToolbarOrderDetail customToolbar;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.commissonContainer)
    FrameLayout commissonContainer;

    @Inject
    protected OrderListViewModel orderListViewModel;
    @Inject
    protected CommissionInteractor commissionInteractor;
    @Inject
    protected DateInteractor dateInteractor;

    public CommissionViewModel commissionViewModel;

    @Override
    protected int containerResId() {
        return R.id.commissonContainer;
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.order_commission, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(@NonNull View view) {
        ButterKnife.bind(this, view);
        OrderListsFragment.getOrdersComponent().inject(this);
        getRoot().setSupportActionBar(toolbar);
        customToolbar.setOnCustomToolbarClick(this);

        commissionViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Singleton
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new CommissionViewModel(dateInteractor, commissionInteractor);
            }
        }).get(CommissionViewModel.class);

        commissionViewModel.observPayData(this, payEntity -> pushFragmentIntoFragment(new SuccessTokinizeView(payEntity,commissionViewModel)));
        commissionViewModel.observIsPayed(this, aBoolean -> {
            if (aBoolean){
                orderListViewModel.setOrderStatusId(4);
                ShowDialog();
            }
        });
        customToolbar.setToolbarTitle(getString(R.string.request_number,orderListViewModel.getOrderId()));

        pushFragmentIntoFragment(new CommissionDetailView(commissionViewModel));

        commissionViewModel.getOrderDetail(orderListViewModel.getOrderId());
    }
    public void ShowDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.commission_payed_seccussfully));
        builder.setTitle(getString(R.string.request));
        builder.setPositiveButton(getString(R.string.OK), (dialog, which) -> onBackPressed());
        builder.setCancelable(true);
        builder.create().show();
    }
    @Override
    public void onBackPressed() {
        getRoot().onBackPressed();
    }
}
