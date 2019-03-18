package com.inc.evil.common.di.modules;

import com.inc.evil.common.network.api.LoginApiService;
import com.inc.evil.common.network.repository.LoginRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    LoginRepository loginRepository(LoginApiService loginApiService){
        return new LoginRepository(loginApiService);
    }
}
