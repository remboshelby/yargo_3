package yargo.inc.common.network.api;

import yargo.inc.common.network.models.login.LoginResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import yargo.inc.common.network.models.login.User;
import yargo.inc.common.network.models.profile_editor_model.ProfileEditResponse;

public interface LoginApiService {
    @GET("/login")
    Observable<LoginResponse> makeLoginWithPass(@Query("LoginForm[username]") String login,
                                                @Query("LoginForm[password]") String password,
                                                @Query("app_id") String app_id);

    @GET("/login")
    Observable<LoginResponse> makeLogingWithToken(@Query("LoginForm[auth_key]") String auth_key,
                                                  @Query("app_id") String app_id);
    @GET("/user/update")
    Observable<ProfileEditResponse> editProfile(@Query("LoginForm[auth_key]") String auth_key,
                                                @Query("app_id") String app_id,
                                                @Query("User[username]") String username,
                                                @Query("User[surname]") String surname,
                                                @Query("User[sex]") int sex,
                                                @Query("User[id_city]") int id_city,
                                                @Query("User[birthday]") String birthday );
}
