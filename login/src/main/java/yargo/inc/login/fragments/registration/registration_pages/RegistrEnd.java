package yargo.inc.login.fragments.registration.registration_pages;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.PasswordTransformationMethod;
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
import androidx.lifecycle.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.login.R;
import yargo.inc.login.R2;
import yargo.inc.login.fragments.LoginFragment;
import yargo.inc.login.fragments.registration.RegistrationFragment;
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

    public RegistrationEndListener listener;

    public interface RegistrationEndListener {
        void showProgressDialog();

    }

    protected RegistrationViewModel registrationViewModel;

    public RegistrEnd(RegistrationEndListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDestroyView() {
        registrationViewModel = null;
        super.onDestroyView();
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.registr_end, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        registrationViewModel = RegistrationFragment.getRegistrationViewModel();
    }

    @OnTextChanged(R2.id.passwordTv)
    void onPasswordChange(Editable editable) {
        passNotMach.setVisibility(View.GONE);
        registrationViewModel.setPassword(editable.toString());
    }

    @OnTextChanged(R2.id.passConfirmation)
    void onPasswordConfirmationChange() {
        passNotMach.setVisibility(View.GONE);
    }

    @OnClick(R2.id.endRegistration)
    void onBtnRegistrationClick() {
        int passwodConfirmation = registrationViewModel.isPasswodCorrect(passConfirmation.getText().toString());
        if (passwodConfirmation ==registrationViewModel.PASSWORD_CORRECT) {
            listener.showProgressDialog();
            registrationViewModel.makeRegistr();
        } else if (passwodConfirmation==registrationViewModel.PASSWORD_IS_SHORT){
            passNotMach.setVisibility(View.VISIBLE);
            passNotMach.setText(getString(R.string.password_is_short));
        }
        else if (passwodConfirmation==registrationViewModel.PASSWORDS_NOT_MATCH){
            passNotMach.setVisibility(View.VISIBLE);
            passNotMach.setText(getString(R.string.password_not_match));
        }
    }

    @OnCheckedChanged(R2.id.chkShowPswd)
    void onCheckShowPswd(boolean checked) {
        if (checked) {
            passwordTv.setTransformationMethod(null);
            passConfirmation.setTransformationMethod(null);
        } else {
            passwordTv.setTransformationMethod(new PasswordTransformationMethod());
            passConfirmation.setTransformationMethod(new PasswordTransformationMethod());
        }
    }
}
