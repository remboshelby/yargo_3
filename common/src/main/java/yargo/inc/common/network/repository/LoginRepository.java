package yargo.inc.common.network.repository;

import yargo.inc.common.network.api.LoginApiService;
import yargo.inc.common.network.models.app.AppResponse;
import yargo.inc.common.network.models.login.LoginResponse;

import io.reactivex.Observable;
import yargo.inc.common.network.models.profile_editor_model.ProfileEditResponse;

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
    public Observable <ProfileEditResponse> editProfile(String auth_key, String app_id, String username,
                                                        String surname, int sex, int id_city, String birthday ){
        return loginApiService.editProfile(auth_key, app_id, username, surname, sex, id_city, birthday);
    }
    public Observable <AppResponse> pushAppData(String auth_key,
                                                String app_id, String fcm){
        return loginApiService.pushAppData(auth_key, app_id, fcm);
    }
}
