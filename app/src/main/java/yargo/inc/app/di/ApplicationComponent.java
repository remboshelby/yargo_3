package yargo.inc.app.di;

import android.app.Application;
import android.content.Context;

import yargo.inc.common.di.CommonComponent;
import yargo.inc.common.di.ServerUrl;
import yargo.inc.common.di.modules.DataBaseModule;
import yargo.inc.common.di.modules.NetworkModule;
import yargo.inc.common.di.modules.RepositoryModule;
import yargo.inc.common.di.modules.SharedPreferenceModule;
import yargo.inc.app.di.module.AppModule;

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
