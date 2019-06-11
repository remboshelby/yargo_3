package yargo.inc.common.interactors;

import io.reactivex.Observable;
import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.login.User;
import yargo.inc.common.network.models.user_info.RegistData.RegistrResponse;
import yargo.inc.common.network.repository.RegistrRepository;

public class RegistrInteractor {
    protected RegistrRepository registrRepository;
    protected CommonSharedPreferences commonSharedPreferences;

    public RegistrInteractor(RegistrRepository registrRepository, CommonSharedPreferences commonSharedPreferences) {
        this.registrRepository = registrRepository;
        this.commonSharedPreferences = commonSharedPreferences;
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
        return registrRepository.makeRegistrGet(username,
                login,
                sex,
                email,
                phone,
                birthday,
                password,
                id_city);
    }
    public void pushAuthToken(String authKey) {
        commonSharedPreferences.putObject(CommonSharedPreferences.AUTH_KEY, authKey);
    }

    public void pushUser(User user) {
        commonSharedPreferences.putObject(CommonSharedPreferences.USER_ABOUT_RESPONSE, user);
    }
}
