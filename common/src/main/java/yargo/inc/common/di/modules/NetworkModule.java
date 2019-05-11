package yargo.inc.common.di.modules;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import yargo.inc.common.di.ServerUrl;
import yargo.inc.common.network.api.ComissionApiService;
import yargo.inc.common.network.api.LoginApiService;
import yargo.inc.common.network.api.OrderActionApiService;
import yargo.inc.common.network.api.OrderApiService;
import yargo.inc.common.network.api.RegistrApiService;
import yargo.inc.common.network.utils.ConnectivityInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().setLenient().create();
    }
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(new ConnectivityInterceptor(context))
                .build();
    }
    @Provides
    @Singleton
    Retrofit provideRetrofit(@ServerUrl String baseUrl, OkHttpClient okHttpClient){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    @Provides
    @Singleton
    LoginApiService provideLoginApiService(Retrofit retrofit){
        return retrofit.create(LoginApiService.class);
    }
    @Provides
    @Singleton
    OrderApiService provideOrderApiService(Retrofit retrofit){
        return retrofit.create(OrderApiService.class);
    }
    @Provides
    @Singleton
    RegistrApiService provideRegistrApiService(Retrofit retrofit){
        return retrofit.create(RegistrApiService.class);
    }
    @Provides
    @Singleton
    OrderActionApiService provideOrderActionApiService(Retrofit retrofit){
        return retrofit.create(OrderActionApiService.class);
    }
    @Provides
    @Singleton
    ComissionApiService comissionApiService(Retrofit retrofit){
        return retrofit.create(ComissionApiService.class);
    }

}
