package com.inc.evil.common.network.api;

import com.inc.evil.common.network.data.Login.LoginResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginApiService {
    @GET("/login")
    Observable<LoginResponse> makeLoginWithPass(@Query("LoginForm[username]") String login,
                                                @Query("LoginForm[password]") String password,
                                                @Query("app_id") String app_id);

    Observable<LoginResponse> makeLogingWithToken(@Query("LoginForm[auth_key]") String auth_key,
                                                  @Query("app_id") String app_id);
}
