package yargo.inc.common.network.repository;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.http.Query;
import yargo.inc.common.network.api.RegistrApiService;
import yargo.inc.common.network.models.user_info.PersonData;

import io.reactivex.Observable;
import yargo.inc.common.network.models.user_info.RegistData.RegistrResponse;
import yargo.inc.common.network.models.user_info.ResponseTest;

public class RegistrRepository {
    private RegistrApiService registrApiService;

    public RegistrRepository(RegistrApiService registrApiService) {
        this.registrApiService = registrApiService;
    }

    public Observable<ResponseTest> makeRegistr(HashMap<String, Object> personData) {
        return registrApiService.makeRegistNewUser(personData);
    }

    public Observable<ResponseTest> makeRegistrGet(String login,
                                                String sex,
                                                String email,
                                                String phone,
                                                String birthday,
                                                String password,
                                                String id_city) {
        return registrApiService.makeRegistNewUserGet(login,sex,
                email,
                phone,
                birthday,
                password,
                id_city);
    }
    //TODO запрос на регистрацию сотового
}
