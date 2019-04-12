package yargo.inc.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;

import yargo.inc.R;
import yargo.inc.app.di.DaggerApplicationComponent;
import yargo.inc.common.di.CommonApplication;
import yargo.inc.common.di.CommonComponent;
import yargo.inc.app.di.ApplicationComponent;

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
