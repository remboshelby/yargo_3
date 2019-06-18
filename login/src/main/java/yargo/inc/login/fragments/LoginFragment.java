package yargo.inc.login.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.di.ApplicationNavigator;
import yargo.inc.common.di.CommonApplication;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.login.LoginViewModel;
import yargo.inc.login.R;
import yargo.inc.login.R2;
import yargo.inc.login.di.DaggerLoginComponent;
import yargo.inc.login.di.LoginComponent;
import yargo.inc.login.fragments.registration.RegistrationFragment;

public class LoginFragment extends BaseFragment {


    @BindView(R2.id.root_swipe_layout)
    SwipeRefreshLayout root_swipe_layout;
    @BindView(R2.id.no_internet_img)
    ImageView no_internet_img;
    @BindView(R2.id.textInputLayoutEmail)
    TextInputLayout textInputLayoutEmail;
    @BindView(R2.id.textInputLayoutPassword)
    TextInputLayout textInputLayoutPassword;
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
    private static ApplicationNavigator navigator;

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

        root_swipe_layout.setOnRefreshListener(this::makeLogin);
        root_swipe_layout.setEnabled(false);

        viewModel.initLoginInfo();

        viewModel.observeIsNetworkError(this, aBoolean -> {
            if (aBoolean) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if (root_swipe_layout.isRefreshing())
                    root_swipe_layout.setRefreshing(false);
                root_swipe_layout.setEnabled(true);

                viewComponentVisibility(View.INVISIBLE, false);
                no_internet_img.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "no internet", Toast.LENGTH_LONG).show();
            } else {
                viewComponentVisibility(View.VISIBLE, false);
            }
        });
        viewModel.observeData(this, loginResponse -> {
            if (loginResponse.getResponse().getType().equals("OK")) {
                viewModel.pushAuthToken(loginResponse.getResponse().getAuthKey());
                navigator.openFragment(getRoot(), "Orders");
                viewModel.sendTokenToServer();
            } else {
                viewModel.pushAuthToken("");
                textInputLayoutEmail.setErrorEnabled(true);
                textInputLayoutPassword.setErrorEnabled(true);

                showErrorDialog("Неправильный логин или пароль");
                viewComponentVisibility(View.VISIBLE, false);
            }
            if (progressDialog.isShowing())
                progressDialog.dismiss();

        });
        viewModel.observeFieldsContent(this, aBoolean -> {
            if (!aBoolean) email_sign_in_button.setEnabled(false);
            else email_sign_in_button.setEnabled(true);
        });
    }

    private void viewComponentVisibility(int viewVisibility, boolean showProgress) {
        textInputLayoutEmail.setVisibility(viewVisibility);
        textInputLayoutPassword.setVisibility(viewVisibility);
        email_sign_button.setVisibility(viewVisibility);
        email_sign_in_button.setVisibility(viewVisibility);

        if (no_internet_img.getVisibility() == View.VISIBLE && viewVisibility == View.VISIBLE) {
            no_internet_img.setVisibility(View.GONE);
            root_swipe_layout.setRefreshing(false);
            root_swipe_layout.setEnabled(false);
        }
        if (showProgress) {
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
        }
    }

    @OnClick(R2.id.email_sign_in_button)
    void onSingInBtnClick() {
        hideKeyboard();
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();

        makeLogin();
    }

    @OnClick(R2.id.email_sign_button)
    void onClickSignInButton() {
        getRoot().pushFragment(new RegistrationFragment(), false);
    }

    @OnTextChanged(R2.id.email)
    void onEmailChanged() {
        viewModel.setEmail(email.getText().toString());
    }

    private void makeLogin() {
        if (viewModel.isAuthKeyExist()) {
            viewComponentVisibility(View.INVISIBLE, true);
            viewModel.makeLoginWithToken();
        } else {
            if (email_sign_in_button.isEnabled()) {
                viewModel.makeLoginWithPassword();
            }
        }
    }

    @Override
    protected void inject() {
        CommonApplication application = (CommonApplication) getRoot().getApplication();
        navigator = application.component().navigator();
        loginComponent = DaggerLoginComponent
                .builder()
                .commonComponent(application.component())
                .root(this)
                .build();

        loginComponent.inject(this);
    }

    public static LoginComponent getLoginComponent() {
        return loginComponent;
    }

    public static ApplicationNavigator getNavigator() {
        return navigator;
    }
}
