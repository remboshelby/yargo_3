package com.inc.evil.yargo_3.app.di;

import android.app.Application;
import android.os.Build;

import com.inc.evil.common.di.ServerUrl;
import com.inc.evil.yargo_3.app.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component (modules = {ApplicationModule.class})
public interface ApplicationComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder serverUrl(@ServerUrl String serverUrl);

        ApplicationComponent build();
    }
}
