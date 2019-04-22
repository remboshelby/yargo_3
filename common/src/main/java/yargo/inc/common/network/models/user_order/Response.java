package yargo.inc.common.network.models.user_order;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("com.robohorse.robopojogenerator")
public class Response{

	@SerializedName("response")
	private Response response;

	@SerializedName("count")
	private int count;

	@SerializedName("orders")
	public List<OrdersItem> orders;

	@SerializedName("auth_key")
	private String authKey;

	@SerializedName("type")
	private String type;

	public void setResponse(Response response){
		this.response = response;
	}

	public Response getResponse(){
		return response;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setOrders(List<OrdersItem> orders){
		this.orders = orders;
	}

	public List<OrdersItem> getOrders(){
		return orders;
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

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"response = '" + response + '\'' + 
			",count = '" + count + '\'' + 
			",orders = '" + orders + '\'' + 
			",auth_key = '" + authKey + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}