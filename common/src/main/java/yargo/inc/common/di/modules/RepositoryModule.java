package yargo.inc.common.di.modules;

import yargo.inc.common.database.UserOrdersDao;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.database.VacantOrdersDao;
import yargo.inc.common.network.api.LoginApiService;
import yargo.inc.common.network.api.OrderActionApiService;
import yargo.inc.common.network.api.OrderApiService;
import yargo.inc.common.network.api.RegistrApiService;
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
    OrdersRepository ordersRepository(OrderApiService orderApiService, VacantOrdersDao vacantOrdersDao, UserOrdersDao userOrdersDao, CommonSharedPreferences commonSharedPreferences){
        return new OrdersRepository(orderApiService,vacantOrdersDao,userOrdersDao,commonSharedPreferences);
    }
    @Provides
    RegistrRepository registrRepository(RegistrApiService registrApiService){
        return new RegistrRepository(registrApiService);
    }
    @Provides
    OrderActionRepository orderActionRepository(OrderActionApiService orderActionApiService, CommonSharedPreferences commonSharedPreferences){
        return new OrderActionRepository(orderActionApiService, commonSharedPreferences);
    }
}
