package yargo.inc.common.network.models.order_detail;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("com.robohorse.robopojogenerator")
public class OrderDetailResponse {

	@SerializedName("response")
	private OrderDetailResponse response;

	@SerializedName("count")
	private int count;

	@SerializedName("orders")
	private List<OrdersItem> orders;

	@SerializedName("auth_key")
	private String authKey;

	@SerializedName("type")
	private String type;

	@SerializedName("order_watches")
	private OrderWatches orderWatches;

	public void setResponse(OrderDetailResponse response){
		this.response = response;
	}

	public OrderDetailResponse getResponse(){
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

	public void setOrderWatches(OrderWatches orderWatches){
		this.orderWatches = orderWatches;
	}

	public OrderWatches getOrderWatches(){
		return orderWatches;
	}

	@Override
 	public String toString(){
		return 
			"OrderDetailResponse{" +
			"response = '" + response + '\'' +
			",count = '" + count + '\'' + 
			",orders = '" + orders + '\'' + 
			",auth_key = '" + authKey + '\'' + 
			",type = '" + type + '\'' + 
			",order_watches = '" + orderWatches + '\'' + 
			"}";
		}
}