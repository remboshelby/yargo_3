package yargo.inc.common.di.modules;

import android.content.Context;

import yargo.inc.common.database.OrdersDao;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.api.ComissionApiService;
import yargo.inc.common.network.api.LoginApiService;
import yargo.inc.common.network.api.OrderActionApiService;
import yargo.inc.common.network.api.OrderApiService;
import yargo.inc.common.network.api.RegistrApiService;
import yargo.inc.common.network.repository.CommissionRepository;
import yargo.inc.common.network.repository.LoginRepository;
import yargo.inc.common.network.repository.OrderActionRepository;
import yargo.inc.common.network.repository.OrdersRepository;
import yargo.inc.common.network.repository.RegistrRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    LoginRepository loginRepository(LoginApiService loginApiService){
        return new LoginRepository(loginApiService);
    }
    @Provides
    OrdersRepository ordersRepository(OrderApiService orderApiService, OrdersDao ordersDao, CommonSharedPreferences commonSharedPreferences, Context context){
        return new OrdersRepository(orderApiService, ordersDao,commonSharedPreferences, context);
    }
    @Provides
    RegistrRepository registrRepository(RegistrApiService registrApiService){
        return new RegistrRepository(registrApiService);
    }
    @Provides
    OrderActionRepository orderActionRepository(OrderActionApiService orderActionApiService, CommonSharedPreferences commonSharedPreferences){
        return new OrderActionRepository(orderActionApiService, commonSharedPreferences);
    }
    @Provides
    CommissionRepository commissionRepository(OrderActionApiService orderActionApiService, ComissionApiService comissionApiService, CommonSharedPreferences commonSharedPreferences){
        return new CommissionRepository(commonSharedPreferences, orderActionApiService,comissionApiService);
    }
}
