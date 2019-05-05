package yargo.inc.orders.fragments.order_list.filters.custom_view.models;

public class SettingModel {
    private String topLabel;
    private String bottomLabel;
    private int picId;
    private int typeId;
    private int id;

    public SettingModel(String topLabel, String bottomLabel, int picId, int id) {
        this.topLabel = topLabel;
        this.bottomLabel = bottomLabel;
        this.picId = picId;
        this.id = id;
    }

    public String getTopLabel() {
        return topLabel;
    }

    public void setTopLabel(String topLabel) {
        this.topLabel = topLabel;
    }

    public String getBottomLabel() {
        return bottomLabel;
    }

    public void setBottomLabel(String bottomLabel) {
        this.bottomLabel = bottomLabel;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
