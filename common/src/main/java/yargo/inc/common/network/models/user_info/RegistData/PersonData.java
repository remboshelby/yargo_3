package yargo.inc.common.network.models.user_info.RegistData;

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

    public boolean isRulseConfirmed;

    private boolean isTelephoneConfirmed;
    @SerializedName("SignupForm[birthday]")
    private String birthday;
    @SerializedName("SignupForm[password]")
    private String password;
    @SerializedName("SignupForm[sex]")
    private String sex;


    public PersonData(String surname, String name, String email, String cityId, String telephonNumber, boolean isTelephoneConfirmed, boolean isRulseConfirmed, String birthday, String sex, String password) {
        this.surname = surname;  //PegistrPersonData
        this.name = name;
        this.email = email;
        this.cityId = cityId;
        this.birthday = birthday;
        this.sex = sex;

        this.telephonNumber = telephonNumber; //RegistrMobilePhone
        this.isTelephoneConfirmed = isTelephoneConfirmed; //RerigstConfirmMobile
        this.isRulseConfirmed = isRulseConfirmed;

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

    public boolean isTelephoneConfirmed() {
        return isTelephoneConfirmed;
    }

    public void setTelephoneConfirmed(boolean telephoneConfirmed) {
        isTelephoneConfirmed = telephoneConfirmed;
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


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isPersonEmpty(){
        return !surname.equals("") && !name.equals("") && !email.equals("")
                && !cityId.equals("") && !birthday.equals("");
    }

    public boolean isPhoneConfirm(){
        return !telephonNumber.equals("") && isRulseConfirmed;
    }
    public boolean isRulseConfirmed() {
        return isRulseConfirmed;
    }

    public void setRulseConfirmed(boolean rulseConfirmed) {
        isRulseConfirmed = rulseConfirmed;
    }
}
