package yargo.inc.common.network.models.profile_editor_model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import yargo.inc.common.network.models.login.User;

@Generated("com.robohorse.robopojogenerator")
public class ProfileEditResponse {

	@SerializedName("response")
	private ProfileEditResponse response;

	@SerializedName("auth_key")
	private String authKey;

	@SerializedName("type")
	private String type;

	@SerializedName("user")
	private User user;

	public void setResponse(ProfileEditResponse response){
		this.response = response;
	}

	public ProfileEditResponse getResponse(){
		return response;
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

	@Override
 	public String toString(){
		return 
			"ProfileEditResponse{" +
			"response = '" + response + '\'' + 
			",auth_key = '" + authKey + '\'' + 
			",type = '" + type + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}