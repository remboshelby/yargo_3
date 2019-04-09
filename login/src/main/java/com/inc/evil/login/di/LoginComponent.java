package com.inc.evil.login.di;

import com.inc.evil.common.di.CommonComponent;
import com.inc.evil.login.fragments.LoginFragment;
import com.inc.evil.login.utils_view.СustomEnterPasswordView;

import dagger.BindsInstance;
import dagger.Component;

@LoginScope
@Component(dependencies = CommonComponent.class, modules = {LoginModule.class, RegistrModule.class})
public interface LoginComponent {

    void inject(LoginFragment loginFragment);

    void inject(СustomEnterPasswordView сustomEnterPasswordView);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder root(LoginFragment loginFragment);

        Builder commonComponent(CommonComponent commonComponent);

        LoginComponent build();
    }
}
