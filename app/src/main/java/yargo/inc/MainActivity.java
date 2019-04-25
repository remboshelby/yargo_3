package yargo.inc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import yargo.inc.common.base.BaseActivity;
import yargo.inc.common.base.BaseFragment;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.repository.LoginRepository;
import yargo.inc.common.network.utils.NoConnectivityException;
import yargo.inc.login.fragments.LoginFragment;
import yargo.inc.orders.fragments.order_list.order_detailse.OrderDetailView;

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
    @Override
    public void onBackPressed() {

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(OrderDetailView.class.getSimpleName());
        if (fragmentByTag!=null) {
            removeFragment(fragmentByTag);
        }else {
            onBackPressed();
        }

    }
    public void removeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

}
