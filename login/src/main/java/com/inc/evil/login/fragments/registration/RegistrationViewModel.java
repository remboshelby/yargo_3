package com.inc.evil.login.fragments.registration;

import com.inc.evil.common.base.BaseViewModel;
import com.inc.evil.common.dto.CommonSharedPreferences;
import com.inc.evil.common.network.models.user_info.PersonData;
import com.inc.evil.common.network.repository.RegistrRepository;

import androidx.lifecycle.MutableLiveData;

public class RegistrationViewModel extends BaseViewModel {
    private RegistrRepository registrRepository;
    private CommonSharedPreferences commonSharedPreferences;

    public RegistrationViewModel(RegistrRepository registrRepository, CommonSharedPreferences commonSharedPreferences) {
        this.registrRepository = registrRepository;
        this.commonSharedPreferences = commonSharedPreferences;
    }

    MutableLiveData<PersonData> personDataMutableLiveData = new MutableLiveData<>();
}
