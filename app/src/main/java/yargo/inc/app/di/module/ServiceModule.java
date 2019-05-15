package yargo.inc.app.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import yargo.inc.services.FirebaseMessaging;

@Module
public class ServiceModule {
    @Provides
    @Singleton
    FirebaseMessaging provideFirebaseMessaging(){
        return new FirebaseMessaging();
    }
}
