package com.inc.evil.common.database.userslistorders;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "user_orders")
public class ListOfUserOrders {
    @PrimaryKey
    private long idUsersOrder;

    public long getIdUsersOrder() {
        return idUsersOrder;
    }

    public void setIdUsersOrder(long idUsersOrder) {
        this.idUsersOrder = idUsersOrder;
    }


    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    @Nullable
    public String getMaterialExist() {
        return materialExist;
    }

    public void setMaterialExist(@Nullable String materialExist) {
        this.materialExist = materialExist;
    }

    @Nullable
    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(@Nullable String deadline) {
        this.deadline = deadline;
    }

    @Nullable
    public String getIdPaymentMethod() {
        return idPaymentMethod;
    }

    public void setIdPaymentMethod(@Nullable String idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }

    @Nullable
    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(@Nullable String idCity) {
        this.idCity = idCity;
    }

    @Nullable
    public String getComments() {
        return comments;
    }

    public void setComments(@Nullable String comments) {
        this.comments = comments;
    }

    @Nullable
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(@Nullable String idUser) {
        this.idUser = idUser;
    }

    @Nullable
    public String getCityName() {
        return cityName;
    }

    public void setCityName(@Nullable String cityName) {
        this.cityName = cityName;
    }

    @Nullable
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(@Nullable String countryName) {
        this.countryName = countryName;
    }

    @Nullable
    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(@Nullable String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    @Nullable
    public String getSpecializaitionName() {
        return specializaitionName;
    }

    public void setSpecializaitionName(@Nullable String specializaitionName) {
        this.specializaitionName = specializaitionName;
    }

    @Nullable
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(@Nullable String longitude) {
        this.longitude = longitude;
    }

    @Nullable
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(@Nullable String latitude) {
        this.latitude = latitude;
    }

    @Nullable
    public String getCountVisit() {
        return countVisit;
    }

    public void setCountVisit(@Nullable String countVisit) {
        this.countVisit = countVisit;
    }

    @Nullable
    public String getClient() {
        return client;
    }

    public void setClient(@Nullable String client) {
        this.client = client;
    }

    @Nullable
    public String getStartworking() {
        return startworking;
    }

    public void setStartworking(@Nullable String startworking) {
        this.startworking = startworking;
    }

    @Nullable
    public String getExactAddress() {
        return exactAddress;
    }

    public void setExactAddress(@Nullable String exactAddress) {
        this.exactAddress = exactAddress;
    }

    @Nullable
    private String address;
    @Nullable
    private String materialExist;
    @Nullable
    private String deadline;
    @Nullable
    private String idPaymentMethod;
    @Nullable
    private String idCity;
    @Nullable
    private String comments;
    @Nullable
    private String idUser;
    @Nullable
    private String cityName;
    @Nullable
    private String countryName;
    @Nullable
    private String orderStatusName;
    @Nullable
    private String specializaitionName;
    @Nullable
    private String longitude;
    @Nullable
    private String latitude;
    @Nullable
    private String countVisit;
    @Nullable
    private String client;
    @Nullable
    private String startworking;
    @Nullable
    private String exactAddress;
}