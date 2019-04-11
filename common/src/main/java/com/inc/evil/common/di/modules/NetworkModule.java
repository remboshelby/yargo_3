package com.inc.evil.common.di.modules;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inc.evil.common.di.ServerUrl;
import com.inc.evil.common.network.api.LoginApiService;
import com.inc.evil.common.network.api.OrderApiService;
import com.inc.evil.common.network.api.RegistrApiService;
import com.inc.evil.common.network.utils.ConnectivityInterceptor;

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
        return new GsonBuilder().create();
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
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
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

}
