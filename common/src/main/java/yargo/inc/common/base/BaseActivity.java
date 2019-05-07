package yargo.inc.common.base;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class BaseActivity extends AppCompatActivity {

    public void pushFragment(BaseFragment fragment, boolean shoudlAddToBackStack) {
        if (!getSupportFragmentManager().popBackStackImmediate(fragment.getClass().getSimpleName(), 0)) {
            if (shoudlAddToBackStack)
                getSupportFragmentManager().beginTransaction()
                        .replace(containerResId(), fragment, fragment.getClass().getSimpleName())
                        .addToBackStack(fragment.getClass().getSimpleName())
                        .commit();
            else getSupportFragmentManager().beginTransaction()
                    .replace(containerResId(), fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }


    protected int containerResId() {
        return 0;
    } ;

    public void removeFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

    public void removePreviousFragment(String fragmentName) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (!fragment.getClass().getSimpleName().equals(fragmentName)) {
                removeFragment((BaseFragment) fragment);
                break;
            }
        }
    }
    public void clear(){
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i< backStackEntryCount; i++ ){
            getSupportFragmentManager().popBackStack();
        }
    }

}
