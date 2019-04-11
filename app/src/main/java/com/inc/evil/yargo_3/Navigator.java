package com.inc.evil.yargo_3;

import com.example.orders.fragments.order_list.OrderListsFragment;
import com.inc.evil.common.base.BaseActivity;
import com.inc.evil.common.di.ApplicationNavigator;
import com.inc.evil.login.fragments.LoginFragment;

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
