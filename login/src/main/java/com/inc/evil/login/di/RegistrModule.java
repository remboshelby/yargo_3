package com.inc.evil.login.di;

import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.repository.RegistrRepository;
import com.inc.evil.login.fragments.registration.RegistrationFragment;
import com.inc.evil.login.fragments.registration.RegistrationViewModel;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.Provides;

@Module
public class RegistrModule {
    @Provides
    @RegistrScope
    public RegistrationViewModel provideRegistrationViewModel(RegistrationFragment host,
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
