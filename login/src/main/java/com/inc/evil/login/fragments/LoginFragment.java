package com.inc.evil.login.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.inc.evil.common.base.BaseFragment;
import com.inc.evil.common.di.CommonApplication;
import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.data.Login.LoginResponse;
import com.inc.evil.common.network.data.Login.Response;
import com.inc.evil.common.network.repository.LoginRepository;
import com.inc.evil.login.LoginViewModel;
import com.inc.evil.login.R;
import com.inc.evil.login.R2;

import com.inc.evil.login.di.DaggerLoginComponent;
import com.inc.evil.login.di.LoginComponent;

import java.lang.reflect.Type;
import java.util.UUID;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment {


    @BindView(R2.id.button)
    Button button;
    @BindView(R2.id.button2)
    Button button2;
    @Inject
    protected LoginViewModel viewModel;
    protected static LoginComponent loginComponent;
    @Inject
    protected CommonSharedPreferences preferences;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.observeData(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                String t = "fsdfsfdsf";

                preferences.putObject("Response", loginResponse.getResponse());
                preferences.putObject("auth_key", loginResponse.getResponse().getAuthKey());
//
                String auth_key = (String) preferences.getObject("auth_key", String.class  );

                final Response resp = (Response)preferences.getObject("Response", Response.class );
                String auth_key_1 = resp.getAuthKey();
                String sdgdf="fdsfs";
            }
        });
        viewModel.makeLoginWithPassword("admin", "admin", UUID.randomUUID().toString());
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

    @OnClick(R2.id.bottom)
    void onButtonClick() {

    }
}
