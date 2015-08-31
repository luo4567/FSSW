package main.gis.money.waterinfo.entity;

/**
 * Created by Administrator on 2015/8/31.
 */
public class Region {

    /**
     * Geometry :
     * GeometryType : 1
     * IsPublish : false
     * Color :
     * CityID :
     * CityName : 区域选择
     */

    private String Geometry;
    private int GeometryType;
    private boolean IsPublish;
    private String Color;
    private String CityID;
    private String CityName;

    public void setGeometry(String Geometry) {
        this.Geometry = Geometry;
    }

    public void setGeometryType(int GeometryType) {
        this.GeometryType = GeometryType;
    }

    public void setIsPublish(boolean IsPublish) {
        this.IsPublish = IsPublish;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public void setCityID(String CityID) {
        this.CityID = CityID;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getGeometry() {
        return Geometry;
    }

    public int getGeometryType() {
        return GeometryType;
    }

    public boolean getIsPublish() {
        return IsPublish;
    }

    public String getColor() {
        return Color;
    }

    public String getCityID() {
        return CityID;
    }

    public String getCityName() {
        return CityName;
    }
}
