package com.inc.evil.login.di;

import com.inc.evil.common.network.repository.LoginRepository;
import com.inc.evil.login.LoginViewModel;
import com.inc.evil.login.fragments.LoginFragment;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    @Provides
    @LoginScope
    public LoginViewModel provideLoginViewModel(LoginFragment host, final LoginRepository loginRepository){
        return ViewModelProviders.of(host, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new LoginViewModel(loginRepository);
            }
        }).get(LoginViewModel.class);
    }
}
