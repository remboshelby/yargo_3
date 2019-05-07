package yargo.inc.common.network.models.user_info.RegistData;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class RegistrResponse {

    @SerializedName("response")
    private RegistrResponse registrResponse;

    @SerializedName("birthday")
    private int birthday;

    @SerializedName("id_city")
    private String idCity;

    @SerializedName("is_email_sended")
    private int isEmailSended;

    @SerializedName("surname")
    private String surname;

    @SerializedName("sex")
    private String sex;

    @SerializedName("auth_key")
    private String authKey;

    @SerializedName("type")
    private String type;

    @SerializedName("user")
    private User user;

    @SerializedName("username")
    private String username;

    @SerializedName("message")
    private Message message;

    public void setRegistrResponse(RegistrResponse registrResponse) {
        this.registrResponse = registrResponse;
    }

    public RegistrResponse getRegistrResponse() {
        return registrResponse;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
    }

    public String getIdCity() {
        return idCity;
    }

    public void setIsEmailSended(int isEmailSended) {
        this.isEmailSended = isEmailSended;
    }

    public int getIsEmailSended() {
        return isEmailSended;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return
                "RegistrResponse{" +
                        "registrResponse = '" + registrResponse + '\'' +
                        ",birthday = '" + birthday + '\'' +
                        ",id_city = '" + idCity + '\'' +
                        ",is_email_sended = '" + isEmailSended + '\'' +
                        ",surname = '" + surname + '\'' +
                        ",sex = '" + sex + '\'' +
                        ",auth_key = '" + authKey + '\'' +
                        ",type = '" + type + '\'' +
                        ",user = '" + user + '\'' +
                        ",username = '" + username + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}