package yargo.inc.common.network.models.user_info;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class User{

	@SerializedName("birthday")
	private String birthday;

	@SerializedName("id_nationality")
	private Object idNationality;

	@SerializedName("sex")
	private int sex;

	@SerializedName("is_agreement_personal_data")
	private Object isAgreementPersonalData;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("is_phone_confirmed")
	private Object isPhoneConfirmed;

	@SerializedName("login")
	private Object login;

	@SerializedName("is_agreement_rules")
	private Object isAgreementRules;

	@SerializedName("is_email_confirmed")
	private Object isEmailConfirmed;

	@SerializedName("id_city")
	private int idCity;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("phone")
	private String phone;

	@SerializedName("surname")
	private String surname;

	@SerializedName("ID")
	private int iD;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public void setBirthday(String birthday){
		this.birthday = birthday;
	}

	public String getBirthday(){
		return birthday;
	}

	public void setIdNationality(Object idNationality){
		this.idNationality = idNationality;
	}

	public Object getIdNationality(){
		return idNationality;
	}

	public void setSex(int sex){
		this.sex = sex;
	}

	public int getSex(){
		return sex;
	}

	public void setIsAgreementPersonalData(Object isAgreementPersonalData){
		this.isAgreementPersonalData = isAgreementPersonalData;
	}

	public Object getIsAgreementPersonalData(){
		return isAgreementPersonalData;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setIsPhoneConfirmed(Object isPhoneConfirmed){
		this.isPhoneConfirmed = isPhoneConfirmed;
	}

	public Object getIsPhoneConfirmed(){
		return isPhoneConfirmed;
	}

	public void setLogin(Object login){
		this.login = login;
	}

	public Object getLogin(){
		return login;
	}

	public void setIsAgreementRules(Object isAgreementRules){
		this.isAgreementRules = isAgreementRules;
	}

	public Object getIsAgreementRules(){
		return isAgreementRules;
	}

	public void setIsEmailConfirmed(Object isEmailConfirmed){
		this.isEmailConfirmed = isEmailConfirmed;
	}

	public Object getIsEmailConfirmed(){
		return isEmailConfirmed;
	}

	public void setIdCity(int idCity){
		this.idCity = idCity;
	}

	public int getIdCity(){
		return idCity;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setSurname(String surname){
		this.surname = surname;
	}

	public String getSurname(){
		return surname;
	}

	public void setID(int iD){
		this.iD = iD;
	}

	public int getID(){
		return iD;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"birthday = '" + birthday + '\'' + 
			",id_nationality = '" + idNationality + '\'' + 
			",sex = '" + sex + '\'' + 
			",is_agreement_personal_data = '" + isAgreementPersonalData + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",is_phone_confirmed = '" + isPhoneConfirmed + '\'' + 
			",login = '" + login + '\'' + 
			",is_agreement_rules = '" + isAgreementRules + '\'' + 
			",is_email_confirmed = '" + isEmailConfirmed + '\'' + 
			",id_city = '" + idCity + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",phone = '" + phone + '\'' + 
			",surname = '" + surname + '\'' + 
			",iD = '" + iD + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}