package yalantis.com.sidemenu.sample.persistent;

/**
 * 区域列表
 * Created by Administrator on 2015/8/4.
 */
public class Region {

    /**
     * CityID :
     * GeometryType : null
     * CityName : 区域选择
     * Geometry : null
     * Color : null
     * IsPublish : false
     */
    private String CityID;
    private String GeometryType;
    private String CityName;
    private String Geometry;
    private String Color;
    private boolean IsPublish;

    public void setCityID(String CityID) {
        this.CityID = CityID;
    }

    public void setGeometryType(String GeometryType) {
        this.GeometryType = GeometryType;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public void setGeometry(String Geometry) {
        this.Geometry = Geometry;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public void setIsPublish(boolean IsPublish) {
        this.IsPublish = IsPublish;
    }

    public String getCityID() {
        return CityID;
    }

    public String getGeometryType() {
        return GeometryType;
    }

    public String getCityName() {
        return CityName;
    }

    public String getGeometry() {
        return Geometry;
    }

    public String getColor() {
        return Color;
    }

    public boolean isIsPublish() {
        return IsPublish;
    }
}
