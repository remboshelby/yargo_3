package yargo.inc.common.network.models.app;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class AppResponse{

	@SerializedName("response")
	private Response response;

	public void setResponse(Response response){
		this.response = response;
	}

	public Response getResponse(){
		return response;
	}

	@Override
 	public String toString(){
		return 
			"AppResponse{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}