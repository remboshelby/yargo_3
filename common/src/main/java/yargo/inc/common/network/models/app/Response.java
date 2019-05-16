package yargo.inc.common.network.models.app;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Response{

	@SerializedName("type")
	private String type;

	@SerializedName("message")
	private String message;

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
			"Response{" + 
			"type = '" + type + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}