package yargo.inc.common.network.models.user_info;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponseTest {

	@SerializedName("response")
	private ResponseTest response;

	@SerializedName("birthday")
	private int birthday;

	@SerializedName("id_city")
	private String idCity;

	@SerializedName("is_email_sended")
	private Object isEmailSended;

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

	public void setResponse(ResponseTest response){
		this.response = response;
	}

	public ResponseTest getResponse(){
		return response;
	}

	public void setBirthday(int birthday){
		this.birthday = birthday;
	}

	public int getBirthday(){
		return birthday;
	}

	public void setIdCity(String idCity){
		this.idCity = idCity;
	}

	public String getIdCity(){
		return idCity;
	}

	public void setIsEmailSended(Object isEmailSended){
		this.isEmailSended = isEmailSended;
	}

	public Object getIsEmailSended(){
		return isEmailSended;
	}

	public void setSurname(String surname){
		this.surname = surname;
	}

	public String getSurname(){
		return surname;
	}

	public void setSex(String sex){
		this.sex = sex;
	}

	public String getSex(){
		return sex;
	}

	public void setAuthKey(String authKey){
		this.authKey = authKey;
	}

	public String getAuthKey(){
		return authKey;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
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
			"ResponseTest{" +
			"response = '" + response + '\'' + 
			",birthday = '" + birthday + '\'' + 
			",id_city = '" + idCity + '\'' + 
			",is_email_sended = '" + isEmailSended + '\'' + 
			",surname = '" + surname + '\'' + 
			",sex = '" + sex + '\'' + 
			",auth_key = '" + authKey + '\'' + 
			",type = '" + type + '\'' + 
			",user = '" + user + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}