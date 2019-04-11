package com.inc.evil.yargo_3.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.inc.evil.common.di.CommonApplication;
import com.inc.evil.common.di.CommonComponent;
import com.inc.evil.yargo_3.R;
import com.inc.evil.yargo_3.app.di.ApplicationComponent;
import com.inc.evil.yargo_3.app.di.DaggerApplicationComponent;
import com.inc.evil.yargo_3.app.di.module.AppModule;

import io.fabric.sdk.android.Fabric;
import java.util.UUID;

public class App extends Application implements CommonApplication {
    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        makeComponent();
        isFirstStart();
    }

    private void isFirstStart() {
        SharedPreferences preferences;
        preferences = getSharedPreferences("shared_preferences", Context.MODE_PRIVATE);
        if (preferences.getString("app_id", "").isEmpty()){
            preferences.edit().putString("app_id", UUID.randomUUID().toString());
        }
    }

    private void makeComponent() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .application(this)
                .context(this)
                .serverUrl(getString(R.string.server_name))
                .build();
    }

    @Override
    public CommonComponent component() {
        return applicationComponent;
    }
}
