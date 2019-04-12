package yargo.inc.login.di;

import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.repository.LoginRepository;
import yargo.inc.common.network.repository.RegistrRepository;
import yargo.inc.login.LoginViewModel;
import yargo.inc.login.fragments.LoginFragment;
import yargo.inc.login.fragments.registration.RegistrationViewModel;

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
    public LoginViewModel provideLoginViewModel(LoginFragment host,
                                                final LoginRepository loginRepository,
                                                CommonSharedPreferences commonSharedPreferences){
        return ViewModelProviders.of(host, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @Singleton
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new LoginViewModel(loginRepository,commonSharedPreferences);
            }
        }).get(LoginViewModel.class);
    }
    @Provides
    @LoginScope
    public RegistrationViewModel provideRegistrationViewModel(LoginFragment host,
                                                              final RegistrRepository registrRepository,
                                                              CommonSharedPreferences commonSharedPreferences){
        return ViewModelProviders.of(host, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @Singleton
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new RegistrationViewModel(registrRepository, commonSharedPreferences);
            }
        }).get(RegistrationViewModel.class);
    }
}
