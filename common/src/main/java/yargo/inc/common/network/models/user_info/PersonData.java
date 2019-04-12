package yargo.inc.common.network.models.user_info;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Field;

public class PersonData {
    @SerializedName("SignupForm[surname]")
    private String surname;
    @SerializedName("SignupForm[username]")
    private String name;
    @SerializedName("SignupForm[email]")
    private String email;
    @SerializedName("SignupForm[id_city]")
    private String cityId;
    @SerializedName("SignupForm[phone]")
    private String telephonNumber;

    private String isTelephoneConfirmed;
    @SerializedName("SignupForm[birthday]")
    private String birthday;
    @SerializedName("SignupForm[password]")
    private String password;

    public PersonData(String surname, String name, String email, String cityId, String telephonNumber, String isTelephoneConfirmed, String birthday, String password) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.cityId = cityId;
        this.telephonNumber = telephonNumber;
        this.isTelephoneConfirmed = isTelephoneConfirmed;
        this.birthday = birthday;
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTelephonNumber() {
        return telephonNumber;
    }

    public void setTelephonNumber(String telephonNumber) {
        this.telephonNumber = telephonNumber;
    }

    public String getIsTelephoneConfirmed() {
        return isTelephoneConfirmed;
    }

    public void setIsTelephoneConfirmed(String isTelephoneConfirmed) {
        this.isTelephoneConfirmed = isTelephoneConfirmed;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}