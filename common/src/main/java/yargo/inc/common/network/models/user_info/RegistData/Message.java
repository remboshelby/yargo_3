package yargo.inc.common.network.models.user_info.RegistData;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Message{

	@SerializedName("email")
	private String email;

	@SerializedName("phone")
	private String phone;

	public void setEmail(String email, String phone){
		this.email = email;
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"Message{" + 
			"email = '" + email + '\'' + 
			",phone = '" + phone + '\'' +
			"}";
		}
}