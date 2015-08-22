package main.gis.money.waterinfo.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/8/22.
 */
public class ChartInfo {
    // 标题
    private String title;
    // 子标题
    private String subTitle;
    // x轴名称
    private String linexName;
    // y轴名称
    private String lineyName;
    // x轴数据
    private List<String> XData;
    // y轴数据
    private List<Float> YData;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getLinexName() {
        return linexName;
    }

    public void setLinexName(String linexName) {
        this.linexName = linexName;
    }

    public String getLineyName() {
        return lineyName;
    }

    public void setLineyName(String lineyName) {
        this.lineyName = lineyName;
    }

    public List<String> getXData() {
        return XData;
    }

    public void setXData(List<String> XData) {
        this.XData = XData;
    }

    public List<Float> getYData() {
        return YData;
    }

    public void setYData(List<Float> YData) {
        this.YData = YData;
    }
}
