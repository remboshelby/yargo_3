package com.inc.evil.login.fragments.registration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inc.evil.common.base.BaseFragment;
import com.inc.evil.login.R;

public class RegistrationFragment extends BaseFragment {
    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.registration_fragment, container, false);
    }
}
