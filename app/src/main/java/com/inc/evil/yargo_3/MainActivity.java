package com.inc.evil.yargo_3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.inc.evil.common.base.BaseActivity;
import com.inc.evil.login.fragments.LoginFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int containerResId() {
        return R.id.activity_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pushFragment(new LoginFragment());
    }
}
