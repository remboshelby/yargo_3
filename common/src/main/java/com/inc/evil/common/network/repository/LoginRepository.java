package com.inc.evil.common.network.repository;

import com.inc.evil.common.network.api.LoginApiService;
import com.inc.evil.common.network.data.Login.LoginResponse;

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
