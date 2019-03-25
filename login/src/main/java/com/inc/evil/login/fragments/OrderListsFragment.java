package com.inc.evil.login.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inc.evil.common.base.BaseFragment;
import com.inc.evil.login.R;

public class OrderListsFragment extends BaseFragment {
    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
//        getRoot().removePreviousFragment(OrderListsFragment.class.getSimpleName());
        return inflater.inflate(R.layout.oder_lists_fragment, container, false);
    }
}
