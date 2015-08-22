package main.gis.money.waterinfo.entity;

/**
 * Created by Administrator on 2015/8/18.
 */
public class StationInfo {

    /**
     * CheckValue : -0.39
     * AlarmType : 0
     * CityName : 珠海市
     * HistoryMaxValue :
     * CheckTime : 2015/8/20 21:00:00
     * AutoId : 770
     * HaveYuBao : false
     * IsOver : false
     * Latitude : 22.04339691
     * StationCode : STM0371
     * HaveAlarm : false
     * StationDescription : 三灶监测站建成于1964年11月,位于广东省珠海市金湾区三灶镇草堂湾社区,属于珠江三角洲水系
     * Longitude : 113.41279783
     * AlarmValue :
     * StationName : 三灶
     */
    private String CheckValue;
    private String AlarmType;
    private String CityName;
    private String HistoryMaxValue;
    private String CheckTime;
    private int AutoId;
    private boolean HaveYuBao;
    private boolean IsOver;
    private String Latitude;
    private String StationCode;
    private boolean HaveAlarm;
    private String StationDescription;
    private String Longitude;
    private String AlarmValue;
    private String StationName;

    public void setCheckValue(String CheckValue) {
        this.CheckValue = CheckValue;
    }

    public void setAlarmType(String AlarmType) {
        this.AlarmType = AlarmType;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public void setHistoryMaxValue(String HistoryMaxValue) {
        this.HistoryMaxValue = HistoryMaxValue;
    }

    public void setCheckTime(String CheckTime) {
        this.CheckTime = CheckTime;
    }

    public void setAutoId(int AutoId) {
        this.AutoId = AutoId;
    }

    public void setHaveYuBao(boolean HaveYuBao) {
        this.HaveYuBao = HaveYuBao;
    }

    public void setIsOver(boolean IsOver) {
        this.IsOver = IsOver;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public void setStationCode(String StationCode) {
        this.StationCode = StationCode;
    }

    public void setHaveAlarm(boolean HaveAlarm) {
        this.HaveAlarm = HaveAlarm;
    }

    public void setStationDescription(String StationDescription) {
        this.StationDescription = StationDescription;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public void setAlarmValue(String AlarmValue) {
        this.AlarmValue = AlarmValue;
    }

    public void setStationName(String StationName) {
        this.StationName = StationName;
    }

    public String getCheckValue() {
        return CheckValue;
    }

    public String getAlarmType() {
        return AlarmType;
    }

    public String getCityName() {
        return CityName;
    }

    public String getHistoryMaxValue() {
        return HistoryMaxValue;
    }

    public String getCheckTime() {
        return CheckTime;
    }

    public int getAutoId() {
        return AutoId;
    }

    public boolean isHaveYuBao() {
        return HaveYuBao;
    }

    public boolean isIsOver() {
        return IsOver;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getStationCode() {
        return StationCode;
    }

    public boolean isHaveAlarm() {
        return HaveAlarm;
    }

    public String getStationDescription() {
        return StationDescription;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getAlarmValue() {
        return AlarmValue;
    }

    public String getStationName() {
        return StationName;
    }
}
