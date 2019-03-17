package com.inc.evil.yargo_3.app;

import android.app.Application;

import com.inc.evil.yargo_3.R;
import com.inc.evil.yargo_3.app.di.ApplicationComponent;
import com.inc.evil.yargo_3.app.di.DaggerApplicationComponent;

public class app extends Application {
    private static ApplicationComponent applicationComponent;

    public static ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        makeComponent();
    }

    private void makeComponent() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .application(this)
                .serverUrl(getString(R.string.server_name))
                .build();
    }
}
