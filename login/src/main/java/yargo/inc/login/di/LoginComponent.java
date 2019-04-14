package yargo.inc.login.di;

import yargo.inc.common.di.CommonComponent;
import yargo.inc.login.fragments.LoginFragment;
import yargo.inc.login.fragments.registration.RegistrationFragment;
import yargo.inc.login.fragments.registration.registration_pages.RegistrConfirmMobile;
import yargo.inc.login.fragments.registration.registration_pages.RegistrEnd;
import yargo.inc.login.fragments.registration.registration_pages.RegistrMobilePhone;
import yargo.inc.login.fragments.registration.registration_pages.RegistrPersonalData;
import yargo.inc.login.utils_view.СustomEnterPasswordView;

import dagger.BindsInstance;
import dagger.Component;

@LoginScope
@Component(dependencies = CommonComponent.class, modules = {LoginModule.class})
public interface LoginComponent {

    void inject(LoginFragment loginFragment);

    //Registr Parent Fragment
    void inject(RegistrationFragment registrationFragment);
    //Registr Child fragment
    void inject(RegistrConfirmMobile registrConfirmMobile);
    void inject(RegistrEnd registrEnd);
    void inject(RegistrMobilePhone registrMobilePhone);
    void inject(RegistrPersonalData registrPersonalData);

    void inject(СustomEnterPasswordView сustomEnterPasswordView);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder root(LoginFragment loginFragment);

        Builder commonComponent(CommonComponent commonComponent);

        LoginComponent build();
    }
}
