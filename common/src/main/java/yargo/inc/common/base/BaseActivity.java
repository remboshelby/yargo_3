package yargo.inc.common.base;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public abstract class BaseActivity extends AppCompatActivity {

    public void pushFragment(BaseFragment fragment, boolean shoudlAddToBackStack) {
        if (!getSupportFragmentManager().popBackStackImmediate(fragment.getClass().getSimpleName(), 0)) {
            if (shoudlAddToBackStack)
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack(fragment.getClass().getSimpleName())
                        .replace(containerResId(), fragment)
                        .commit();
            else getSupportFragmentManager().beginTransaction()
                    .replace(containerResId(), fragment)
                    .commit();
        }
    }

    protected int containerResId() {
        return 0;
    }

    ;

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
}
