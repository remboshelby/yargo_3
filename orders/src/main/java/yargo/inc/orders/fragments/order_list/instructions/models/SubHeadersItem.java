package yargo.inc.orders.fragments.order_list.instructions.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class SubHeadersItem{

	@SerializedName("number")
	private String number;

	@SerializedName("level")
	private int level;

	@SerializedName("text")
	private String text;

	@SerializedName("subHeader")
	private String subHeader;

	public void setNumber(String number){
		this.number = number;
	}

	public String getNumber(){
		return number;
	}

	public void setLevel(int level){
		this.level = level;
	}

	public int getLevel(){
		return level;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setSubHeader(String subHeader){
		this.subHeader = subHeader;
	}

	public String getSubHeader(){
		return subHeader;
	}

	@Override
 	public String toString(){
		return 
			"SubHeadersItem{" + 
			"number = '" + number + '\'' + 
			",level = '" + level + '\'' + 
			",text = '" + text + '\'' + 
			",subHeader = '" + subHeader + '\'' + 
			"}";
		}
}