package com.inc.evil.login.fragments.order_list.paging_orders.data;

public class Order {
    private int id;
    private int idd;
    private String orderPrice;
    private boolean payType;
    private String orderAbout;
    private int orderType;
    private int id_orderStatus;

    public Order(int id,
                 int idd,
                 String orderPrice,
                 boolean payType,
                 String orderAbout,
                 int orderType,
                 int id_orderStatus,
                 String oderDatetime) {
        this.id = id;
        this.idd = idd;
        this.orderPrice = orderPrice;
        this.payType = payType;
        this.orderAbout = orderAbout;
        this.orderType = orderType;
        this.id_orderStatus = id_orderStatus;
        this.oderDatetime = oderDatetime;
    }

    private String oderDatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public boolean isPayType() {
        return payType;
    }

    public void setPayType(boolean payType) {
        this.payType = payType;
    }

    public String getOrderAbout() {
        return orderAbout;
    }

    public void setOrderAbout(String orderAbout) {
        this.orderAbout = orderAbout;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getId_orderStatus() {
        return id_orderStatus;
    }

    public void setId_orderStatus(int id_orderStatus) {
        this.id_orderStatus = id_orderStatus;
    }

    public String getOderDatetime() {
        return oderDatetime;
    }

    public void setOderDatetime(String oderDatetime) {
        this.oderDatetime = oderDatetime;
    }
}
