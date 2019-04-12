package yargo.inc.login.di;

import yargo.inc.common.di.CommonComponent;
import yargo.inc.login.fragments.LoginFragment;
import yargo.inc.login.fragments.registration.RegistrationFragment;
import yargo.inc.login.utils_view.СustomEnterPasswordView;

import dagger.BindsInstance;
import dagger.Component;

@LoginScope
@Component(dependencies = CommonComponent.class, modules = {LoginModule.class})
public interface LoginComponent {

    void inject(LoginFragment loginFragment);

    void inject(RegistrationFragment registrationFragment);

    void inject(СustomEnterPasswordView сustomEnterPasswordView);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder root(LoginFragment loginFragment);

        Builder commonComponent(CommonComponent commonComponent);

        LoginComponent build();
    }
}
