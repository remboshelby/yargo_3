package yargo.inc.orders.fragments.order_list.instructions.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class OffertsModel{

	@SerializedName("subHeaders")
	private List<SubHeadersItem> subHeaders;

	@SerializedName("header_title")
	private String headerTitle;

	@SerializedName("name")
	private String name;

	@SerializedName("header")
	private String header;

	public void setSubHeaders(List<SubHeadersItem> subHeaders){
		this.subHeaders = subHeaders;
	}

	public List<SubHeadersItem> getSubHeaders(){
		return subHeaders;
	}

	public void setHeaderTitle(String headerTitle){
		this.headerTitle = headerTitle;
	}

	public String getHeaderTitle(){
		return headerTitle;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setHeader(String header){
		this.header = header;
	}

	public String getHeader(){
		return header;
	}

	@Override
 	public String toString(){
		return 
			"OffertsModel{" + 
			"subHeaders = '" + subHeaders + '\'' + 
			",header_title = '" + headerTitle + '\'' + 
			",name = '" + name + '\'' + 
			",header = '" + header + '\'' + 
			"}";
		}
}