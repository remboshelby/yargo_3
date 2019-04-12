package yargo.inc.common.network.repository;

import yargo.inc.common.network.api.LoginApiService;
import yargo.inc.common.network.models.login.LoginResponse;

import io.reactivex.Observable;

public class LoginRepository {
    private LoginApiService loginApiService;

    public LoginRepository(LoginApiService loginApiService) {
        this.loginApiService = loginApiService;
    }

    public Observable <LoginResponse> makeLoginWithPass(String login, String password, String app_id){
        return loginApiService.makeLoginWithPass(login, password, app_id);
    }
    public Observable <LoginResponse> makeLoginWithToken(String auth_key, String app_id){
        return loginApiService.makeLogingWithToken(auth_key, app_id);
    }
}
