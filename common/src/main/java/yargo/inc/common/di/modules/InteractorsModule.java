package yargo.inc.common.di.modules;

import dagger.Module;
import dagger.Provides;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.interactors.CommissionInteractor;
import yargo.inc.common.interactors.DateInteractor;
import yargo.inc.common.interactors.ProfileEditorInteractor;
import yargo.inc.common.interactors.RegistrationInteractor;
import yargo.inc.common.interactors.UserOrderInteractor;
import yargo.inc.common.network.repository.LoginRepository;
import yargo.inc.common.network.repository.OrderActionRepository;
import yargo.inc.common.network.repository.OrdersRepository;
import yargo.inc.common.network.repository.RegistrRepository;

@Module
public class InteractorsModule {
    @Provides
    CommissionInteractor commissionInteractor(OrderActionRepository orderActionRepository, CommonSharedPreferences commonSharedPreferences){
        return new CommissionInteractor(orderActionRepository, commonSharedPreferences);
    }
    @Provides
    DateInteractor dateInteractor(){
        return new DateInteractor();
    }

    @Provides
    ProfileEditorInteractor profileEditorInteractor(CommonSharedPreferences commonSharedPreferences, LoginRepository loginRepository){return new ProfileEditorInteractor(commonSharedPreferences,loginRepository);}

    @Provides
    UserOrderInteractor userOrderInteractor(OrdersRepository ordersRepository, CommonSharedPreferences commonSharedPreferences){return new UserOrderInteractor(ordersRepository, commonSharedPreferences);}

    @Provides
    RegistrationInteractor registrationInteractor(RegistrRepository registrRepository, CommonSharedPreferences commonSharedPreferences){
        return new RegistrationInteractor(registrRepository, commonSharedPreferences);
    }
}
