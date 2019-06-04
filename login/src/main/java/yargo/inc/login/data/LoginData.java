package yargo.inc.login.data;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class LoginData implements Serializable {
    @Nullable
    private String authKey;
    @Nullable
    private String appId;
    private String email;
    private String password;

    public String getAuthKey() {
        return authKey == null ? "" : authKey;
    }

    public LoginData(String authKey, String appId, String email, String password) {
        this.authKey = authKey;
        this.email = email;
        this.appId = appId;
        this.password = password;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppId() {
        return appId;
    }

    public boolean isFieldNotEmpty() {
        if (email.isEmpty() || password.isEmpty()) {
            return false;
        } else return true;
    }
}
