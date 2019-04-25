package yargo.inc.orders.fragments.order_list.order_detailse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yargo.inc.common.base.BaseFragment;
import yargo.inc.orders.R;

public class OrderDetailView extends BaseFragment {
    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.order_details, container, false);
    }
}
