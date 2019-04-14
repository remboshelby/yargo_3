package yargo.inc.login.fragments.registration.registration_pages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import yargo.inc.common.base.BaseFragment;
import yargo.inc.login.R;
import yargo.inc.login.fragments.LoginFragment;
import yargo.inc.login.fragments.registration.RegistrationViewModel;

public class RegistrConfirmMobile extends BaseFragment {
    @Inject
    protected RegistrationViewModel registrationViewModel;
    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        LoginFragment.getLoginComponent().inject(this);
        return inflater.inflate(R.layout.registr_confirm_mobile, container, false);
    }
}
