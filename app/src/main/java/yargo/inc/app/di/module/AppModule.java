package yargo.inc.app.di.module;

import android.app.Application;

import yargo.inc.app.App;
import yargo.inc.common.di.ApplicationNavigator;
import yargo.inc.Navigator;

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
