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
import butterknife.OnTextChanged;

import static com.inc.evil.common.dto.CommonSharedPreferences.APP_ID;
import static com.inc.evil.common.dto.CommonSharedPreferences.AUTH_KEY;
import static com.inc.evil.common.dto.CommonSharedPreferences.USER_ABOUT_RESPONSE;

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
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(getContext());

        viewModel.initLoginInfo();
        viewModel.observeData(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                preferences.putObject(USER_ABOUT_RESPONSE, loginResponse.getResponse());

                if (loginResponse.getResponse().getType().equals("OK")) {
                    preferences.putObject(AUTH_KEY, loginResponse.getResponse().getAuthKey());
                    getRoot().pushFragment(new OrderListsFragment());
                    getRoot().removeFragment(LoginFragment.this);
                } else {
                    preferences.putObject(AUTH_KEY, "");
                    textInputLayoutEmail.setErrorEnabled(true);
                    textInputLayoutPassword.setErrorEnabled(true);

                    viewComponentVisibility(View.VISIBLE);
                }
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });
        viewModel.observeFieldsContent(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) email_sign_in_button.setEnabled(false);
                else email_sign_in_button.setEnabled(true);
            }
        });

        if (viewModel.isAuthKeyExist()) {
//            viewComponentVisibility(View.INVISIBLE);
//            viewModel.makeLoginWithToken();
        }
    }

    private void viewComponentVisibility(int viewVisibility) {
        textInputLayoutEmail.setVisibility(viewVisibility);
        textInputLayoutPassword.setVisibility(viewVisibility);
        email_sign_button.setVisibility(viewVisibility);
        email_sign_in_button.setVisibility(viewVisibility);

        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
    }

    @OnClick(R2.id.email_sign_in_button)
    void onSingInBtnClick() {
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();

        viewModel.makeLoginWithPassword();
    }


    @OnClick(R2.id.email_sign_button)
    void onClickSignInButton() {
    }

    @OnTextChanged(R2.id.email)
    void onEmailChanged() {
        viewModel.setEmail(email.getText().toString());
    }

    @OnTextChanged(R2.id.password)
    void onPasswordChanged() {
        viewModel.setPassword(password.getText().toString());
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


}
