package com.inc.evil.common.di;

import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.repository.LoginRepository;

public interface CommonComponent {
    LoginRepository loginRepository();
    CommonSharedPreferences commonSharedPreferences();
}
