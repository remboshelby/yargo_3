package yargo.inc.common.di.modules;

import dagger.Module;
import dagger.Provides;
import yargo.inc.common.interactors.CommissionInteractor;
import yargo.inc.common.interactors.DateInteractor;
import yargo.inc.common.network.repository.OrderActionRepository;

@Module
public class InteractorModule {
    @Provides
    CommissionInteractor commissionInteractor(OrderActionRepository orderActionRepository){
        return new CommissionInteractor(orderActionRepository);
    }
    @Provides
    DateInteractor dateInteractor(){
        return new DateInteractor();
    }
}
