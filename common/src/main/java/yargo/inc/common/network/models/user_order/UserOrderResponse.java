package yargo.inc.common.network.models.user_order;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("com.robohorse.robopojogenerator")
public class UserOrderResponse {

	@SerializedName("response")
	private UserOrderResponse response;

	@SerializedName("count")
	private int count;

	@SerializedName("orders")
	public List<UserOrdersItem> orders;

	@SerializedName("auth_key")
	private String authKey;

	@SerializedName("type")
	private String type;

	public void setResponse(UserOrderResponse response){
		this.response = response;
	}

	public UserOrderResponse getResponse(){
		return response;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setOrders(List<UserOrdersItem> orders){
		this.orders = orders;
	}

	public List<UserOrdersItem> getOrders(){
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
			"UserOrderResponse{" +
			"response = '" + response + '\'' + 
			",count = '" + count + '\'' + 
			",orders = '" + orders + '\'' + 
			",auth_key = '" + authKey + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}