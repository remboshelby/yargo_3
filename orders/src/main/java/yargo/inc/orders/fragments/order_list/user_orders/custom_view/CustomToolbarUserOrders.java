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
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.OrdersViewModel;

public class CustomToolbarUserOrders extends ConstraintLayout {

    @Inject
    protected OrdersViewModel ordersViewModel;

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

        spOrderCategory.setAdapter(new ArrayAdapter<String>(context, R.layout.spiner_item, getResources().getStringArray(R.array.ordersCategory)));
        tvToolBarTitle.setText(getResources().getString(R.string.my_orders));

//        registrationViewModel.setCityId(RegistrPersonalData.this.getResources().getStringArray(R.array.citiesId)[position]);
    }
}
