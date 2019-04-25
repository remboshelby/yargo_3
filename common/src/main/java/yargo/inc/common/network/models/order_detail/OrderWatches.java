package yargo.inc.common.network.models.order_detail;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class OrderWatches{

	@SerializedName("id_order")
	private String idOrder;

	@SerializedName("watched")
	private int watched;

	public void setIdOrder(String idOrder){
		this.idOrder = idOrder;
	}

	public String getIdOrder(){
		return idOrder;
	}

	public void setWatched(int watched){
		this.watched = watched;
	}

	public int getWatched(){
		return watched;
	}

	@Override
 	public String toString(){
		return 
			"OrderWatches{" + 
			"id_order = '" + idOrder + '\'' + 
			",watched = '" + watched + '\'' + 
			"}";
		}
}