package yargo.inc;

import android.os.Bundle;

import yargo.inc.common.base.BaseActivity;
import yargo.inc.login.fragments.LoginFragment;


public class MainActivity extends BaseActivity {

    @Override
    protected int containerResId() {
        return R.id.activity_container;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pushFragment(new LoginFragment(),false);
    }


}
