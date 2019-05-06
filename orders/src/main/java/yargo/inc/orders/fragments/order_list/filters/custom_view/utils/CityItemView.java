package yargo.inc.orders.fragments.order_list.filters.custom_view.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import yargo.inc.orders.R2;
import yargo.inc.orders.R;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.filters.FiltersViewModel;
import yargo.inc.orders.fragments.order_list.filters.custom_view.models.CityModel;

public class CityItemView extends ConstraintLayout {

    private CityModel item;
    @Inject
    protected FiltersViewModel filtersViewModel;

    @BindView(R2.id.cityTitle)
    TextView cityTitle;
    @BindView(R2.id.chckCity)
    CheckBox chckCity;

    public CityItemView(Context context) {
        super(context);
        init(context);
    }

    public CityItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        OrderListsFragment.getOrdersComponent().inject(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        LayoutInflater.from(context).inflate(R.layout.city_item_layout, this);
        ButterKnife.bind(this);
    }

    public void bind(CityModel item) {
        this.item = item;
        cityTitle.setText(item.getCityName());
        chckCity.setChecked(item.isChecked());
    }
}
