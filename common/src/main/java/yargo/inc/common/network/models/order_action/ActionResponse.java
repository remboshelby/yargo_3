package yargo.inc.common.network.models.order_action;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ActionResponse {

	@SerializedName("response")
	private ActionResponse response;

	@SerializedName("error_code")
	private String errorCode;

	@SerializedName("auth_key")
	private String authKey;

	@SerializedName("type")
	private String type;

	@SerializedName("message")
	private String message;

	public void setResponse(ActionResponse response){
		this.response = response;
	}

	public ActionResponse getResponse(){
		return response;
	}

	public void setErrorCode(String errorCode){
		this.errorCode = errorCode;
	}

	public String getErrorCode(){
		return errorCode;
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

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ActionResponse{" +
			"response = '" + response + '\'' + 
			",error_code = '" + errorCode + '\'' + 
			",auth_key = '" + authKey + '\'' + 
			",type = '" + type + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}