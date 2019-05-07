package yargo.inc.common.network.repository;

import java.util.HashMap;

import yargo.inc.common.network.api.RegistrApiService;

import io.reactivex.Observable;
import yargo.inc.common.network.models.user_info.RegistData.RegistrResponse;

public class RegistrRepository {
    private RegistrApiService registrApiService;

    public RegistrRepository(RegistrApiService registrApiService) {
        this.registrApiService = registrApiService;
    }

    public Observable<RegistrResponse> makeRegistr(HashMap<String, Object> personData) {
        return registrApiService.makeRegistNewUser(personData);
    }

    public Observable<RegistrResponse> makeRegistrGet(
            String username,
            String login,
            String sex,
            String email,
            String phone,
            String birthday,
            String password,
            String id_city) {
        return registrApiService.makeRegistNewUserGet(username,
                login,
                sex,
                email,
                phone,
                birthday,
                password,
                id_city);
    }
    //TODO запрос на регистрацию сотового
}
