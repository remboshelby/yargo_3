package yargo.inc.orders.fragments.order_list.filters.custom_view.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.constraintlayout.widget.ConstraintLayout;

import yargo.inc.orders.R;

public class CustomBottomNavigation extends ConstraintLayout {
    public CustomBottomNavigation(Context context) {
        super(context);
        init(context);
    }

    public CustomBottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_filter_bottom_bar, this);
    }
}
