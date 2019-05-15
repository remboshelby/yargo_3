package yargo.inc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import yargo.inc.common.base.BaseActivity;
import yargo.inc.login.fragments.LoginFragment;
import yargo.inc.orders.fragments.order_list.OrderListsFragment;

import static yargo.inc.common.dto.CommonSharedPreferences.AUTH_KEY;
import static yargo.inc.common.dto.CommonSharedPreferences.SHARED_PREFERENCES;


public class MainActivity extends BaseActivity {

    private SharedPreferences preferences;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected int containerResId() {
        return R.id.activity_container;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);


//        disableFCM();
//        enableFCM();

        preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (preferences.getString(AUTH_KEY, "\"\"").equals("\"\"")){
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
    public void enableFCM(){
        // Enable FCM via enable Auto-init service which generate new token and receive in FCMService
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }

    public void disableFCM(){
        // Disable auto init
        FirebaseMessaging.getInstance().setAutoInitEnabled(false);
        new Thread(() -> {
            try {
                // Remove InstanceID initiate to unsubscribe all topic
                // TODO: May be a better way to use FirebaseMessaging.getInstance().unsubscribeFromTopic()
                FirebaseInstanceId.getInstance().deleteInstanceId();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


}
