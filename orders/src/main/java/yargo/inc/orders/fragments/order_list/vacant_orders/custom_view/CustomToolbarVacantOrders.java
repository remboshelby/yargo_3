package yargo.inc.orders.fragments.order_list.vacant_orders.custom_view;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import javax.inject.Inject;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.OrdersViewModel;
import yargo.inc.orders.fragments.order_list.vacant_orders.VacantOrderList;

public class CustomToolbarVacantOrders extends ConstraintLayout {

    @Inject
    protected OrdersViewModel ordersViewModel;

    @BindView(R2.id.tvSearch)
    AutoCompleteTextView tvSearch;
    @BindView(R2.id.tvToolBarTitle)
    TextView tvToolBarTitle;

    @BindView(R2.id.imgBtnCloseSearch)
    ImageButton imgBtnCloseSearch;
    @BindView(R2.id.customSearch)
    ConstraintLayout customSearch;
    @BindView(R2.id.imgBtnSearch)
    ImageButton imgBtnSearch;

    public CustomToolbarVacantOrders(Context context) {
        super(context);
        init(context);
    }

    public CustomToolbarVacantOrders(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.custom_vacant_toolbar, this);
        ButterKnife.bind(this);
        OrderListsFragment.getOrdersComponent().inject(this);

        ImageSpan imageSpan = new ImageSpan(context, R.drawable.ic_search_24dp);
        SpannableString spannableString = new SpannableString(" "+ getResources().getString(R.string.cutom_toolbar_search));
        spannableString.setSpan(imageSpan, 0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvSearch.setHint(spannableString);
    }

    public void setTitle(String title) {
        tvToolBarTitle.setText(title);
    }
    @OnClick(R2.id.imgBtnSearch)
    public void onBtnSearchClick(){
        toolbarState(true);
    }
    @OnClick(R2.id.imgBtnCloseSearch)
    public void onBtnCloseSearch(){
        toolbarState(false);
    }
    public void toolbarState(boolean isSearched){
        if (isSearched){
            imgBtnSearch.setVisibility(GONE);
            tvToolBarTitle.setVisibility(GONE);
            customSearch.setVisibility(VISIBLE);
        }
        else {
            imgBtnSearch.setVisibility(VISIBLE);
            tvToolBarTitle.setVisibility(VISIBLE);
            customSearch.setVisibility(GONE);
            tvSearch.setText("");

        }
    }
    @OnTextChanged(R2.id.tvSearch)
    public void onSearchTextChanged(){
        ordersViewModel.setOrderDescription(tvSearch.getText().toString());
    }
}