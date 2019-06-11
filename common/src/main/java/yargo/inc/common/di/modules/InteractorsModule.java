package yargo.inc.common.di.modules;

import dagger.Module;
import dagger.Provides;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.interactors.CommissionInteractor;
import yargo.inc.common.interactors.DateInteractor;
import yargo.inc.common.interactors.RegistrInteractor;
import yargo.inc.common.network.repository.OrderActionRepository;
import yargo.inc.common.network.repository.RegistrRepository;

@Module
public class InteractorsModule {
    @Provides
    CommissionInteractor commissionInteractor(OrderActionRepository orderActionRepository){
        return new CommissionInteractor(orderActionRepository);
    }
    @Provides
    DateInteractor dateInteractor(){
        return new DateInteractor();
    }

    @Provides
    RegistrInteractor registrInteractor(RegistrRepository registrRepository, CommonSharedPreferences commonSharedPreferences)
    {return new RegistrInteractor(registrRepository, commonSharedPreferences);}
}
