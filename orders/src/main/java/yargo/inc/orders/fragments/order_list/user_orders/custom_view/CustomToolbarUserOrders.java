package yargo.inc.orders.fragments.order_list.user_orders.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.user_orders.UserOrdersViewModel;

public class CustomToolbarUserOrders extends ConstraintLayout {

    @Inject
    protected UserOrdersViewModel userOrdersViewModel;

    @BindView(R2.id.tvToolBarTitle)
    TextView tvToolBarTitle;
    @BindView(R2.id.spOrderCategory)
    Spinner spOrderCategory;

    public CustomToolbarUserOrders(Context context) {
        super(context);
        init(context);
    }

    public CustomToolbarUserOrders(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_users_toolbar, this);
        ButterKnife.bind(this);
        OrderListsFragment.getOrdersComponent().inject(this);

        spOrderCategory.setAdapter(new ArrayAdapter<>(context, R.layout.spiner_item, getResources().getStringArray(R.array.ordersCategory)));
        tvToolBarTitle.setText(getResources().getString(R.string.my_orders));
        spOrderCategory.setSelection(userOrdersViewModel.getStartPositon());
    }
    @OnItemSelected(value = R2.id.spOrderCategory, callback = OnItemSelected.Callback.ITEM_SELECTED)
    void spinnerItemSelected(int position) {
            userOrdersViewModel.setOrderCategoryId(Integer.valueOf(getResources().getStringArray(R.array.ordersCategoryId)[position]));
            userOrdersViewModel.setStartPositon(position);
    }

}
