package com.inc.evil.common.di.modules;

import com.inc.evil.common.network.api.LoginApiService;
import com.inc.evil.common.network.api.OrderApiService;
import com.inc.evil.common.network.repository.LoginRepository;
import com.inc.evil.common.network.repository.OrdersRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    LoginRepository loginRepository(LoginApiService loginApiService){
        return new LoginRepository(loginApiService);
    }
    @Provides
    OrdersRepository ordersRepository(OrderApiService orderApiService){
        return new OrdersRepository(orderApiService);
    }
}
