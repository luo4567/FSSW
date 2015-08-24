package main.gis.money.waterinfo.entity;

/**
 * Created by Administrator on 2015/8/24.
 */
public class StationHistory {

    public StationHistory(String checkTime,float checkValue){
        this.checkTime=checkTime;
        this.checkValue=checkValue;
    }

    private String checkTime;

    private float checkValue;

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public float getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(float checkValue) {
        this.checkValue = checkValue;
    }
}
