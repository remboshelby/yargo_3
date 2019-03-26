package com.inc.evil.login.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.inc.evil.common.base.BaseFragment;
import com.inc.evil.common.di.CommonApplication;
import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.data.Login.LoginResponse;
import com.inc.evil.common.network.data.Login.Response;
import com.inc.evil.login.LoginViewModel;
import com.inc.evil.login.R;
import com.inc.evil.login.R2;
import com.inc.evil.login.di.DaggerLoginComponent;
import com.inc.evil.login.di.LoginComponent;

import java.util.UUID;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment {


    @BindView(R2.id.textInputLayoutEmail)
    TextInputLayout textInputLayoutEmail;
    @BindView(R2.id.textInputLayoutPassword)
    TextInputLayout textInputLayoutPassword;
    @BindView(R2.id.imageView3)
    ImageView imageView3;
    @BindView(R2.id.email)
    AutoCompleteTextView email;
    @BindView(R2.id.password)
    EditText password;
    @BindView(R2.id.email_sign_in_button)
    Button email_sign_in_button;
    @BindView(R2.id.email_sign_button)
    Button email_sign_button;

    @Inject
    protected LoginViewModel viewModel;
    protected static LoginComponent loginComponent;
    @Inject
    protected CommonSharedPreferences preferences;

    private ProgressDialog progressDialog;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        viewModel.observeData(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {

                preferences.putObject("user_about_response", loginResponse.getResponse());
                preferences.putObject("auth_key", loginResponse.getResponse().getAuthKey());

                if (!loginResponse.getResponse().getType().equals("OK")) {
                    getRoot().pushFragment(new OrderListsFragment());
                    getRoot().removeFragment(LoginFragment.this);
                }
                else {
                    textInputLayoutEmail.setErrorEnabled(true);
                    textInputLayoutPassword.setErrorEnabled(true);
                }
                progressDialog.dismiss();

            }
        });
    }

    @Override
    protected void inject() {
        CommonApplication application = (CommonApplication) getRoot().getApplication();

        loginComponent = DaggerLoginComponent
                .builder()
                .commonComponent(application.component())
                .root(this)
                .build();

        loginComponent.inject(this);
    }

    @OnClick(R2.id.email_sign_in_button)
    void onSingInBtnClick() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        viewModel.makeLoginWithPassword("admin", "admin", UUID.randomUUID().toString());
    }


    @OnClick(R2.id.email_sign_button)
    void click() {
        String t = "DSFSDF";
    }
}
