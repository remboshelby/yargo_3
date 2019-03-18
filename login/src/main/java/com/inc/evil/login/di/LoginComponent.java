package com.inc.evil.login.di;

import android.os.Build;

import com.inc.evil.common.di.CommonComponent;
import com.inc.evil.login.fragments.LoginFragment;

import dagger.BindsInstance;
import dagger.Component;

@LoginScope
@Component(dependencies = CommonComponent.class, modules = {LoginModule.class})
public interface LoginComponent {

    void inject(LoginFragment loginFragment);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder root(LoginFragment loginFragment);

        Builder commonComponent(CommonComponent commonComponent);

        LoginComponent build();
    }
}
