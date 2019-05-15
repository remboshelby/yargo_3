package yargo.inc.orders.fragments.order_list.order_commission.entity;

public class PayEntity {
    private String payToken;
    private String payMethodName;
    private String ammount;
    private String description;
    private int orderId;

    public PayEntity(String payToken, String payMethodName, String ammount, String description, int orderId) {
        this.payToken = payToken;
        this.payMethodName = payMethodName;
        this.ammount = ammount;
        this.description = description;
        this.orderId = orderId;
    }

    public String getPayToken() {
        return payToken;
    }

    public void setPayToken(String payToken) {
        this.payToken = payToken;
    }

    public String getPayMethodName() {
        return payMethodName;
    }

    public void setPayMethodName(String payMethodName) {
        this.payMethodName = payMethodName;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
