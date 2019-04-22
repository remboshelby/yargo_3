package yargo.inc.common.network.models.user_order;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Generated("com.robohorse.robopojogenerator")
@Entity(tableName = "user_orders")
public class UserOrdersItem {

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("material_exists")
	private int materialExists;

	@SerializedName("orderStatusName")
	private String orderStatusName;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("cityName")
	private String cityName;

	@SerializedName("price")
	private int price;

	@SerializedName("client")
	private String client;

	@PrimaryKey
	@SerializedName("ID")
	private int iD;

	@SerializedName("exact_address")
	private String exactAddress;

	@SerializedName("deadline")
	private String deadline;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("address")
	private String address;

	@SerializedName("startworking")
	private String startworking;

	@SerializedName("comments")
	private String comments;

	@SerializedName("specializationName")
	private String specializationName;

	@SerializedName("id_specialization")
	private int idSpecialization;

	@SerializedName("id_payment_method")
	private int idPaymentMethod;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("watched")
	private int watched;

	@SerializedName("id_city")
	private int idCity;

	@SerializedName("id_order_status")
	private int idOrderStatus;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("countryName")
	private String countryName;

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setMaterialExists(int materialExists){
		this.materialExists = materialExists;
	}

	public int getMaterialExists(){
		return materialExists;
	}

	public void setOrderStatusName(String orderStatusName){
		this.orderStatusName = orderStatusName;
	}

	public String getOrderStatusName(){
		return orderStatusName;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setClient(String client){
		this.client = client;
	}

	public String getClient(){
		return client;
	}

	public void setID(int iD){
		this.iD = iD;
	}

	public int getID(){
		return iD;
	}

	public void setExactAddress(String exactAddress){
		this.exactAddress = exactAddress;
	}

	public String getExactAddress(){
		return exactAddress;
	}

	public void setDeadline(String deadline){
		this.deadline = deadline;
	}

	public String getDeadline(){
		return deadline;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setStartworking(String startworking){
		this.startworking = startworking;
	}

	public String getStartworking(){
		return startworking;
	}

	public void setComments(String comments){
		this.comments = comments;
	}

	public String getComments(){
		return comments;
	}

	public void setSpecializationName(String specializationName){
		this.specializationName = specializationName;
	}

	public String getSpecializationName(){
		return specializationName;
	}

	public void setIdSpecialization(int idSpecialization){
		this.idSpecialization = idSpecialization;
	}

	public int getIdSpecialization(){
		return idSpecialization;
	}

	public void setIdPaymentMethod(int idPaymentMethod){
		this.idPaymentMethod = idPaymentMethod;
	}

	public int getIdPaymentMethod(){
		return idPaymentMethod;
	}

	public void setIdUser(int idUser){
		this.idUser = idUser;
	}

	public int getIdUser(){
		return idUser;
	}

	public void setWatched(int watched){
		this.watched = watched;
	}

	public int getWatched(){
		return watched;
	}

	public void setIdCity(int idCity){
		this.idCity = idCity;
	}

	public int getIdCity(){
		return idCity;
	}

	public void setIdOrderStatus(int idOrderStatus){
		this.idOrderStatus = idOrderStatus;
	}

	public int getIdOrderStatus(){
		return idOrderStatus;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCountryName(String countryName){
		this.countryName = countryName;
	}

	public String getCountryName(){
		return countryName;
	}

	@Override
 	public String toString(){
		return 
			"UserOrdersItem{" +
			"latitude = '" + latitude + '\'' + 
			",description = '" + description + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",material_exists = '" + materialExists + '\'' + 
			",orderStatusName = '" + orderStatusName + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",cityName = '" + cityName + '\'' + 
			",price = '" + price + '\'' + 
			",client = '" + client + '\'' + 
			",iD = '" + iD + '\'' + 
			",exact_address = '" + exactAddress + '\'' + 
			",deadline = '" + deadline + '\'' + 
			",longitude = '" + longitude + '\'' + 
			",address = '" + address + '\'' + 
			",startworking = '" + startworking + '\'' + 
			",comments = '" + comments + '\'' + 
			",specializationName = '" + specializationName + '\'' + 
			",id_specialization = '" + idSpecialization + '\'' + 
			",id_payment_method = '" + idPaymentMethod + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",watched = '" + watched + '\'' + 
			",id_city = '" + idCity + '\'' + 
			",id_order_status = '" + idOrderStatus + '\'' + 
			",phone = '" + phone + '\'' + 
			",name = '" + name + '\'' + 
			",countryName = '" + countryName + '\'' + 
			"}";
		}
}