package com.inc.evil.yargo_3.app.di;

import android.app.Application;
import android.content.Context;

import com.inc.evil.common.di.CommonComponent;
import com.inc.evil.common.di.ServerUrl;
import com.inc.evil.common.di.modules.DataBaseModule;
import com.inc.evil.common.di.modules.NetworkModule;
import com.inc.evil.common.di.modules.RepositoryModule;
import com.inc.evil.common.di.modules.SharedPreferenceModule;
import com.inc.evil.yargo_3.app.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component (modules = {NetworkModule.class, RepositoryModule.class, SharedPreferenceModule.class,
        DataBaseModule.class, AppModule.class})
public interface ApplicationComponent extends CommonComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder context(Context context);

        @BindsInstance
        Builder serverUrl(@ServerUrl String serverUrl);

        ApplicationComponent build();
    }
}
