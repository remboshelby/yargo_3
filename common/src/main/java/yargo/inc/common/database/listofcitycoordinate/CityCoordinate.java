package yargo.inc.common.database.listofcitycoordinate;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "city_coordinate")
public class CityCoordinate {
    @PrimaryKey
    private long idCity;

    @Nullable
    private String cityName;

    @Nullable
    private String longitude;

    @Nullable
    private String latitude;

    @Nullable
    private String cityId;


    @Nullable
    private String defaultZoom;

    public long getIdCity() {
        return idCity;
    }

    public void setIdCity(long idCity) {
        this.idCity = idCity;
    }

    @Nullable
    public String getCityName() {
        return cityName;
    }

    public void setCityName(@Nullable String cityName) {
        this.cityName = cityName;
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
    public String getCityId() {
        return cityId;
    }

    public void setCityId(@Nullable String cityId) {
        this.cityId = cityId;
    }

    @Nullable
    public String getDefaultZoom() {
        return defaultZoom;
    }

    public void setDefaultZoom(@Nullable String defaultZoom) {
        this.defaultZoom = defaultZoom;
    }
}
