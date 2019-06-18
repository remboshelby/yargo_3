package yargo.inc.common.interactors;

import java.util.HashMap;

import io.reactivex.Observable;
import yargo.inc.common.base.BaseSharedPreference;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.user_info.RegistData.RegistrResponse;
import yargo.inc.common.network.repository.RegistrRepository;

public class RegistrationInteractor extends BaseSharedPreference  {
    private RegistrRepository registrRepository;

    public RegistrationInteractor(RegistrRepository registrRepository, CommonSharedPreferences commonSharedPreferences) {
        this.registrRepository = registrRepository;
        setCommonSharedPreferences(commonSharedPreferences);
    }
    public Observable<RegistrResponse> makeRegistrGet(
            String username,
            String login,
            String sex,
            String email,
            String phone,
            String birthday,
            String password,
            String id_city){
        return registrRepository.makeRegistrGet(username,login, sex,
                 email, phone, birthday, password, id_city);
    }
}
