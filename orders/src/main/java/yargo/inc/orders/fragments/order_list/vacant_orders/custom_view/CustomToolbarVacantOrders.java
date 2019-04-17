package yargo.inc.orders.fragments.order_list.vacant_orders.custom_view;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yargo.inc.orders.R;
import yargo.inc.orders.R2;

public class CustomToolbarVacantOrders extends ConstraintLayout {


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
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);

    }
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.custom_vacant_toolbar, this);
        ButterKnife.bind(this);

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
        }
    }
}
