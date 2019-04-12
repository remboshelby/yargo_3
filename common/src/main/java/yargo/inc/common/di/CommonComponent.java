package yargo.inc.common.di;

import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.repository.LoginRepository;
import yargo.inc.common.network.repository.OrdersRepository;
import yargo.inc.common.network.repository.RegistrRepository;

public interface CommonComponent {
    LoginRepository loginRepository();
    OrdersRepository ordersRepository();
    CommonSharedPreferences commonSharedPreferences();
    ApplicationNavigator navigator();
    RegistrRepository registrRepository();
}