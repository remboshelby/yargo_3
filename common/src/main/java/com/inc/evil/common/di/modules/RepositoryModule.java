package com.inc.evil.common.di.modules;

import com.inc.evil.common.database.VacantOrdersDao;
import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.api.LoginApiService;
import com.inc.evil.common.network.api.OrderApiService;
import com.inc.evil.common.network.api.RegistrApiService;
import com.inc.evil.common.network.repository.LoginRepository;
import com.inc.evil.common.network.repository.OrdersRepository;
import com.inc.evil.common.network.repository.RegistrRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    LoginRepository loginRepository(LoginApiService loginApiService){
        return new LoginRepository(loginApiService);
    }
    @Provides
    OrdersRepository ordersRepository(OrderApiService orderApiService, VacantOrdersDao vacantOrdersDao, CommonSharedPreferences commonSharedPreferences){
        return new OrdersRepository(orderApiService,vacantOrdersDao,commonSharedPreferences);
    }
    @Provides
    RegistrRepository registrRepository(RegistrApiService registrApiService){
        return new RegistrRepository(registrApiService);
    }
}
