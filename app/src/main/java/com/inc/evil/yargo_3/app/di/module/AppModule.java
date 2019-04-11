package com.inc.evil.yargo_3.app.di.module;

import com.inc.evil.common.di.ApplicationNavigator;
import com.inc.evil.yargo_3.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    @Singleton
    ApplicationNavigator provideNavigator(){
        return new Navigator();
    }
}
