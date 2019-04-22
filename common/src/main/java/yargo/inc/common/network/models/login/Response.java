package yargo.inc.common.network.models.login;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Response {

    @SerializedName("birthday")
    private int birthday;

    @SerializedName("id_city")
    private int idCity;

    @SerializedName("surname")
    private String surname;

    @SerializedName("sex")
    private int sex;

    @SerializedName("fcm_token")
    private int fcmToken;

    @SerializedName("kassa")
    private int kassa;

    @SerializedName("auth_key")
    private String authKey;

    @SerializedName("type")
    private String type;

    @SerializedName("user")
    private User user;

    @SerializedName("version")
    private String version;

    @SerializedName("username")
    private String username;

    @SerializedName("message")
    private String message;

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return sex;
    }

    public void setFcmToken(int fcmToken) {
        this.fcmToken = fcmToken;
    }

    public int getFcmToken() {
        return fcmToken;
    }

    public void setKassa(int kassa) {
        this.kassa = kassa;
    }

    public int getKassa() {
        return kassa;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return
                "UserOrderResponse{" +
                        "birthday = '" + birthday + '\'' +
                        ",id_city = '" + idCity + '\'' +
                        ",surname = '" + surname + '\'' +
                        ",sex = '" + sex + '\'' +
                        ",fcm_token = '" + fcmToken + '\'' +
                        ",kassa = '" + kassa + '\'' +
                        ",auth_key = '" + authKey + '\'' +
                        ",type = '" + type + '\'' +
                        ",user = '" + user + '\'' +
                        ",version = '" + version + '\'' +
                        ",username = '" + username + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}