package yargo.inc.login.fragments.registration;

import yargo.inc.common.base.BaseViewModel;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.user_info.PersonData;
import yargo.inc.common.network.repository.RegistrRepository;

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
