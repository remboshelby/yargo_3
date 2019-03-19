package com.inc.evil.common.di.modules;

import android.app.Application;

import com.inc.evil.common.dto.CommonSharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferenceModule {
    @Provides
    @Singleton
    CommonSharedPreferences provideCommonSharedPreferences(Application application){
        return new CommonSharedPreferences(application);
    }
}
