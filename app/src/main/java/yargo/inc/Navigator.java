package yargo.inc;

import yargo.inc.orders.fragments.order_list.OrderListsFragment;
import yargo.inc.common.base.BaseActivity;
import yargo.inc.common.di.ApplicationNavigator;
import yargo.inc.login.fragments.LoginFragment;

public class Navigator implements ApplicationNavigator {

    @Override
    public void openFragment(BaseActivity baseActivity, String fragmentName) {
        switch (fragmentName){
            case "Orders":
                baseActivity.pushFragment(new OrderListsFragment(), false);
                break;
            case "Login":
                baseActivity.pushFragment(new LoginFragment(), false);
                break;
        }
    }
}
