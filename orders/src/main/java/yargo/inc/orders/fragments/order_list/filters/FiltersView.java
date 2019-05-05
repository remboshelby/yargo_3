package yargo.inc.orders.fragments.order_list.filters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yargo.inc.orders.R2;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.orders.R;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.filters.custom_view.CustomFilterToolbar;
import yargo.inc.orders.fragments.order_list.filters.custom_view.FilterCategory;
import yargo.inc.orders.fragments.order_list.filters.custom_view.FilterCity;
import yargo.inc.orders.fragments.order_list.filters.custom_view.FilterSettings;

public class FiltersView extends BaseFragment implements CustomFilterToolbar.onCustomFilterToolbarClick {



    @BindView(R2.id.customFilterToolbar)
    CustomFilterToolbar customFilterToolbar;
    @BindView(R2.id.toolbarFilter)
    Toolbar toolbarFilter;
    @BindView(R2.id.appbarFilter)
    AppBarLayout appbarFilter;
    @BindView(R2.id.filterViewContainer)
    NestedScrollView filterViewContainer;
    @BindView(R2.id.bottomFilterNav)
    BottomNavigationView bottomFilterNav;


    @Override
    protected int containerResId() {
        return R.id.filterViewContainer;
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.filter_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        pushFragmentIntoFragment(new FilterSettings());
    }

    private void init(@NonNull View view) {
        OrderListsFragment.getOrdersComponent().inject(this);
        ButterKnife.bind(this, view);
        getRoot().setSupportActionBar(toolbarFilter);
        customFilterToolbar.setOnCustomFilterToolbarClick(this);
    }
    public void pushCityFragment(){
        pushFragmentIntoFragment(new FilterCity());
    }
    public void pushCategoryFragment(){
        pushFragmentIntoFragment(new FilterCategory());
    }
    @Override
    public void btnBackClick() {
        getRoot().onBackPressed();
    }
    @OnClick(R2.id.bottomFilterNav)
    void onBtnBottomBarClick(){
    }
}
