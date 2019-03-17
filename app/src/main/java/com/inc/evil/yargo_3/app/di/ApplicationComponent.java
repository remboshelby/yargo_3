package com.inc.evil.yargo_3.app.di;

import android.app.Application;
import android.os.Build;

import com.inc.evil.common.di.CommonComponent;
import com.inc.evil.common.di.ServerUrl;
import com.inc.evil.common.di.modules.NetworkModule;
import com.inc.evil.common.di.modules.RepositoryModule;
import com.inc.evil.common.di.modules.SharedPreferenceModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component (modules = {NetworkModule.class, RepositoryModule.class, SharedPreferenceModule.class})
public interface ApplicationComponent extends CommonComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder serverUrl(@ServerUrl String serverUrl);

        ApplicationComponent build();
    }
}
