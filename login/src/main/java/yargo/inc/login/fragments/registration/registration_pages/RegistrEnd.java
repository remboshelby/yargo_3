package yargo.inc.login.fragments.registration.registration_pages;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.login.R;
import yargo.inc.login.R2;
import yargo.inc.login.fragments.LoginFragment;
import yargo.inc.login.fragments.registration.RegistrationViewModel;

public class RegistrEnd extends BaseFragment {


    @BindView(R2.id.passNotMach)
    TextView passNotMach;
    @BindView(R2.id.passwordTv)
    AutoCompleteTextView passwordTv;
    @BindView(R2.id.passConfirmation)
    AutoCompleteTextView passConfirmation;
    @BindView(R2.id.chkShowPswd)
    CheckBox chkShowPswd;
    @BindView(R2.id.endRegistration)
    Button endRegistration;

    @Inject
    protected RegistrationViewModel registrationViewModel;
    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        LoginFragment.getLoginComponent().inject(this);
        return inflater.inflate(R.layout.registr_end, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        registrationViewModel.observeBtnStatus(this, endRegistration::setEnabled);

    }
    @OnTextChanged(R2.id.passwordTv)
    void onPasswordChange(Editable editable){
        passNotMach.setVisibility(View.GONE);
        registrationViewModel.setPassword(editable.toString());
    }
    @OnTextChanged(R2.id.passConfirmation)
    void onPasswordConfirmationChange(){
        passNotMach.setVisibility(View.GONE);
    }
    @OnClick(R2.id.endRegistration)
    void onBtnRegistrationClick(){
        if (registrationViewModel.isPasswodCorrect(passConfirmation.getText().toString())){

        }else {
            passNotMach.setVisibility(View.VISIBLE);
        }
    }
}
