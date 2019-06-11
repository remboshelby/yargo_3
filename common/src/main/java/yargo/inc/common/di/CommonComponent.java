package yargo.inc.common.di;

import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.interactors.CommissionInteractor;
import yargo.inc.common.interactors.DateInteractor;
import yargo.inc.common.interactors.RegistrInteractor;
import yargo.inc.common.network.repository.CommissionRepository;
import yargo.inc.common.network.repository.LoginRepository;
import yargo.inc.common.network.repository.OrderActionRepository;
import yargo.inc.common.network.repository.OrdersRepository;
import yargo.inc.common.network.repository.RegistrRepository;

public interface CommonComponent {
    LoginRepository loginRepository();
    OrdersRepository ordersRepository();
    CommonSharedPreferences commonSharedPreferences();
    ApplicationNavigator navigator();
    RegistrRepository registrRepository();
    OrderActionRepository orderActionRepository();
    CommissionRepository commissionRepository();

    //interactors
    CommissionInteractor commissionInteractor();
    DateInteractor dateInteractor();
    RegistrInteractor registrInteractor();
}
