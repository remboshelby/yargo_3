package yargo.inc.common.network.models.order;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Generated("com.robohorse.robopojogenerator")
@Entity(tableName = "vacant_orders")
public class OrdersItem{

	@Nullable
	@SerializedName("latitude")
	private String latitude;

	@Nullable
	@SerializedName("description")
	private String description;

	@Nullable
	@SerializedName("created_at")
	private String createdAt;

	@Nullable
	@SerializedName("material_exists")
	private int materialExists;

	@Nullable
	@SerializedName("orderStatusName")
	private String orderStatusName;

	@Nullable
	@SerializedName("updated_at")
	private String updatedAt;

	@Nullable
	@SerializedName("cityName")
	private String cityName;

	@Nullable
	@SerializedName("price")
	private int price;

	@Nullable
	@SerializedName("client")
	private String client;

	@NonNull
	@PrimaryKey
	@SerializedName("ID")
	private int iD;

	@Nullable
	@SerializedName("exact_address")
	private String exactAddress;

	@Nullable
	@SerializedName("deadline")
	private String deadline;

	@Nullable
	@SerializedName("longitude")
	private String longitude;

	@Nullable
	@SerializedName("address")
	private String address;

	@Nullable
	@SerializedName("startworking")
	private String startworking;

	@Nullable
	@SerializedName("comments")
	private String comments;

	@Nullable
	@SerializedName("specializationName")
	private String specializationName;

	@Nullable
	@SerializedName("id_specialization")
	private int idSpecialization;

	@Nullable
	@SerializedName("id_payment_method")
	private int idPaymentMethod;

	@Nullable
	@SerializedName("id_user")
	private String idUser;

	@Nullable
	@SerializedName("watched")
	private int watched;

	@Nullable
	@SerializedName("id_city")
	private int idCity;

	@Nullable
	@SerializedName("id_order_status")
	private int idOrderStatus;

	@Nullable
	@SerializedName("phone")
	private String phone;

	@Nullable
	@SerializedName("name")
	private String name;

	@Nullable
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

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
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
			"OrdersItem{" + 
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