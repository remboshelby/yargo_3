package yargo.inc.common.base;

import yargo.inc.common.dto.CommonSharedPreferences;
import yargo.inc.common.network.models.login.User;

import static yargo.inc.common.dto.CommonSharedPreferences.APP_ID;
import static yargo.inc.common.dto.CommonSharedPreferences.AUTH_KEY;
import static yargo.inc.common.dto.CommonSharedPreferences.FCM_KEY;
import static yargo.inc.common.dto.CommonSharedPreferences.USER_ABOUT_RESPONSE;

public abstract class BaseSharedPreference {
    protected CommonSharedPreferences commonSharedPreferences;

    public void setCommonSharedPreferences(CommonSharedPreferences commonSharedPreferences) {
        this.commonSharedPreferences = commonSharedPreferences;
    }

    public void setUserInfoAndFilteredCity(User user) {
        commonSharedPreferences.putObject(commonSharedPreferences.USER_ABOUT_RESPONSE, user);
        commonSharedPreferences.putObject(commonSharedPreferences.FILTERED_CITY, user.getIdCity());
    }
    public void setAuthToken(String authToken){
        commonSharedPreferences.putObject(CommonSharedPreferences.AUTH_KEY, authToken);
    }

    public User getUserInfo(){
        return (User) commonSharedPreferences.getObject(USER_ABOUT_RESPONSE, User.class);
    }
    public String getAuthToken(){
        return (String) commonSharedPreferences.getObject(AUTH_KEY, String.class);
    }
    public String getAppId(){
        return (String) commonSharedPreferences.getObject(APP_ID, String.class);
    }
    public String getFcmToken(){
        return (String) commonSharedPreferences.getObject(FCM_KEY, String.class);
    }

}
