package com.inc.evil.common.di;

import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.repository.LoginRepository;
import com.inc.evil.common.network.repository.OrdersRepository;
import com.inc.evil.common.network.repository.RegistrRepository;

public interface CommonComponent {
    LoginRepository loginRepository();
    OrdersRepository ordersRepository();
    CommonSharedPreferences commonSharedPreferences();
    ApplicationNavigator navigator();
    RegistrRepository registrRepository();
}
