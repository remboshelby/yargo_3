package yargo.inc.login.fragments.registration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yargo.inc.common.base.BaseFragment;
import yargo.inc.login.R;

public class RegistrPersonalData extends BaseFragment {
    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.registr_personal_data, container, false);
    }
}
