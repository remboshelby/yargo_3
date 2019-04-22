package yargo.inc.common.network.models.vacant_order;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Response{

	@SerializedName("count")
	private int count;

	@SerializedName("orders")
	private List<VacantOrderItem> orders;

	@SerializedName("auth_key")
	private String authKey;

	@SerializedName("type")
	private String type;

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setOrders(List<VacantOrderItem> orders){
		this.orders = orders;
	}

	public List<VacantOrderItem> getOrders(){
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
			"count = '" + count + '\'' + 
			",orders = '" + orders + '\'' + 
			",auth_key = '" + authKey + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}