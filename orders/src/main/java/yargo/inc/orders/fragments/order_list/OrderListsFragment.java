package yargo.inc.orders.fragments.order_list;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.di.ApplicationNavigator;
import yargo.inc.common.di.CommonApplication;
import yargo.inc.common.network.models.login.User;
import yargo.inc.common.network.models.order_list.OrderItem;
import yargo.inc.common.utils.CircleImageView_;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.di.DaggerOrdersComponent;
import yargo.inc.orders.di.OrdersComponent;
import yargo.inc.orders.fragments.order_list.filters.FiltersView;
import yargo.inc.orders.fragments.order_list.instructions.InstructionView;
import yargo.inc.orders.fragments.order_list.profile_editor.ProfileEditorView;
import yargo.inc.orders.fragments.order_list.user_orders.UserOrderList;
import yargo.inc.orders.fragments.order_list.user_orders.UserOrdersViewModel;
import yargo.inc.orders.fragments.order_list.vacant_orders.VacantOrderList;

public class OrderListsFragment extends BaseFragment {
    private static final String TAG = OrderListsFragment.class.getName();

    @BindView(R2.id.imgBtnMap)
    ImageButton imgBtnMap;
    @BindView(R2.id.imgBtnFilter)
    ImageButton imgBtnFilter;
    @BindView(R2.id.toolbar_main)
    Toolbar toolbar_main;
    @BindView(R2.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R2.id.list_container)
    FrameLayout list_container;
    @BindView(R2.id.navigationView)
    NavigationView navigationView;
    @BindView(R2.id.drawerLayout)
    DrawerLayout drawerLayout;
    protected static OrdersComponent ordersComponent;

    @Inject
    protected OrderListViewModel orderListViewModel;

    @Inject
    protected UserOrdersViewModel ordersViewModel;

    private ApplicationNavigator navigator;

    @Override
    protected int containerResId() {
        return R.id.list_container;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.oder_lists_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getRoot(), drawerLayout, toolbar_main, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        orderListViewModel.observeOrderStatus(this, new Observer<OrderItem>() {
            @Override
            public void onChanged(OrderItem orderItem) {
                if (orderItem.getIdOrderStatus() == 0 || orderItem.getIdOrderStatus() == 1)
                    pushFragmentIntoFragment(new VacantOrderList());
                else {
                    Integer orderStatus = orderItem.getIdOrderStatus();
                    String[] array = getResources().getStringArray(R.array.ordersCategoryId);
                    Integer position = Arrays.asList(array).indexOf(String.valueOf(orderStatus));
                    ordersViewModel.setStartPositon(position);
                    pushFragmentIntoFragment(new UserOrderList());
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.menu_orders) {
                pushFragmentIntoFragment(new VacantOrderList());
            } else if (menuItem.getItemId() == R.id.menu_my_orders) {
                pushFragmentIntoFragment(new UserOrderList());
            } else if (menuItem.getItemId() == R.id.menu_call) {
                dialContactPhone(getString(R.string.help_number));
            } else if (menuItem.getItemId() == R.id.menu_exit) {
                showExitDialog();
            } else if (menuItem.getItemId() == R.id.menu_instruction) {
                getRoot().pushFragment(new InstructionView(), true);
            }
            drawerLayout.closeDrawer(Gravity.LEFT);
            return false;
        });
        initHeader();
    }

    public static OrdersComponent getOrdersComponent() {
        return ordersComponent;
    }

    private void showExitDialog() {
        AlertDialog.Builder accoutExitDialog = new AlertDialog.Builder(getRoot());
        accoutExitDialog.setMessage(getResources().getString(R.string.exit_question)).setCancelable(false).
                setPositiveButton(getResources().getString(R.string.YES), (dialog, which) -> {
                    orderListViewModel.pushAuthToken("");
                    orderListViewModel.clearTokenToServer();
                    navigator.openFragment(getRoot(), "Login");
                })
                .setNegativeButton(getResources().getString(R.string.NO), (dialog, which) -> dialog.cancel()).create();

        accoutExitDialog.setTitle(getResources()
                .getString(R.string.menu_exit))
                .show();
    }

    @OnClick(R2.id.imgBtnMap)
    void onBtnMapClick() {
        //TODO MAP BTN
    }

    @OnClick(R2.id.imgBtnFilter)
    void onImgBtnFilter() {
        getRoot().pushFragment(new FiltersView(), true);
    }

    private void initHeader() {
        User user = orderListViewModel.getUser();

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.tvDrawlerTitle)).setText(user.getSurname() + " " + user.getUsername());
        CircleImageView_ headerImageView = navigationView.getHeaderView(0).findViewById(R.id.clientAvatar);
        headerImageView.setImageResource(R.drawable.person);
        headerImageView.setOnClickListener(v -> {
            getRoot().pushFragment(new ProfileEditorView(), true);
            drawerLayout.closeDrawer(Gravity.LEFT);
        });
    }

    @Override
    protected void inject() {
        CommonApplication commonApplication = (CommonApplication) getRoot().getApplication();
        navigator = commonApplication.component().navigator();
        ordersComponent = DaggerOrdersComponent
                .builder()
                .commonComponent(commonApplication.component())
                .root(this)
                .application(getRoot().getApplication())
                .build();

        ordersComponent.inject(this);
    }
    public void dialContactPhone(final String phoneNumber){
        getContext().startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phoneNumber, null)));
    }


}
