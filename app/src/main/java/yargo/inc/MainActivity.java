package yargo.inc;

import android.os.Bundle;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.base.BaseActivity;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.repository.LoginRepository;
import yargo.inc.common.network.utils.NoConnectivityException;
import yargo.inc.login.fragments.LoginFragment;

import static yargo.inc.app.App.getApplicationComponent;


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
