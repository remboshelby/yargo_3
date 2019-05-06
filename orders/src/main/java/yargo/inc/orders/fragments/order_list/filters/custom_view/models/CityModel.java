package yargo.inc.orders.fragments.order_list.filters.custom_view.models;

public class CityModel {
    private int id;
    private String cityName;
    private boolean isChecked;

    public CityModel(int id, String cityName, boolean isChecked) {
        this.id = id;
        this.cityName = cityName;
        this.isChecked = isChecked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
