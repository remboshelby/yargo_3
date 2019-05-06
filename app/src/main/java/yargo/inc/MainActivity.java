package yargo.inc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.util.List;

import yargo.inc.common.base.BaseActivity;
import yargo.inc.login.fragments.LoginFragment;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.orders.fragments.order_list.filters.FiltersView;
import yargo.inc.orders.fragments.order_list.order_detailse.OrderDetailView;

import static yargo.inc.common.dto.CommonSharedPreferences.AUTH_KEY;
import static yargo.inc.common.dto.CommonSharedPreferences.SHARED_PREFERENCES;


public class MainActivity extends BaseActivity {

    private SharedPreferences preferences;

    @Override
    protected int containerResId() {
        return R.id.activity_container;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (preferences.getString(AUTH_KEY, null)==null){
            pushFragment(new LoginFragment(),false);
        }
        else {
            pushFragment(new OrderListsFragment(),false);
        }
    }
    @Override
    public void onBackPressed() {
        if ( getSupportFragmentManager().findFragmentByTag(LoginFragment.class.getSimpleName())!=null) {
            super.onBackPressed();
        }
        else if ( getSupportFragmentManager().findFragmentByTag(OrderListsFragment.class.getSimpleName())!=null){
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }

    }
    public void removeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

}
