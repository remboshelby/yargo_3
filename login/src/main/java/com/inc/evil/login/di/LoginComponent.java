package com.inc.evil.login.di;

import android.media.session.PlaybackState;
import android.os.Build;

import com.inc.evil.common.di.CommonComponent;
import com.inc.evil.login.fragments.LoginFragment;
import com.inc.evil.login.fragments.order_list.OrderList;
import com.inc.evil.login.utils_view.СustomEnterPasswordView;

import dagger.BindsInstance;
import dagger.Component;

@LoginScope
@Component(dependencies = CommonComponent.class, modules = {LoginModule.class,
        OrdersModule.class})
public interface LoginComponent {

    void inject(LoginFragment loginFragment);

    void inject(СustomEnterPasswordView сustomEnterPasswordView);

    void inject(OrderList orderList);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder root(LoginFragment loginFragment);

//        @BindsInstance
//        Builder root(OrderList orderList);

        Builder commonComponent(CommonComponent commonComponent);

        LoginComponent build();
    }
}
