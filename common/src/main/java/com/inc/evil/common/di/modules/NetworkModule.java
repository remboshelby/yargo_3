package com.inc.evil.common.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inc.evil.common.di.ServerUrl;
import com.inc.evil.common.network.api.LoginApiService;

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
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }
    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, @ServerUrl String baseUrl, OkHttpClient okHttpClient){
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
}
