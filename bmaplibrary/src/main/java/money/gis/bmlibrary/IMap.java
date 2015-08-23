package money.gis.bmlibrary;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by Administrator on 2015/8/2.
 */
public interface IMap {
    /**
     * 初始化地图
     * @param id 控件Id
     * @param lng
     * @param lat
     * @param zoom 默认放大级别
     * @return BMap
     */
    BaiduMap initMap(int id, double lng, double lat, int zoom);

    /**
     * 添加点
     * @param lng
     * @param lat
     * @param key
     * @param name
     * @param dragable 是否可拖动
     * @param isShow 是否显示
     * @param icon 图标
     */
    void addPoint(double lng, double lat, String key, String name, boolean dragable, boolean isShow, BitmapDescriptor icon,int stationType);

    /**
     * 添加线
     * @param geometry
     * @param name
     * @param key
     * @param color
     */
    void addPolyline(String geometry, String name, String key, int color, boolean visible);

    /**
     * 添加面
     * @param geometry
     * @param name
     * @param key
     * @param strokeColor
     * @param fillColor
     */
    void addPolygon(String geometry, String name, String key, int strokeColor, int fillColor, boolean visible);

    /**
     * 添加文字标签
     * @param point
     * @param key
     * @param name
     * @param visible
     */
    void addLabel(LatLng point, String key, String name, boolean visible);

    // 设置点图标
    void setImageUrl(String key, BitmapDescriptor icon);

    /**
     * 根据键值获取覆盖物
     * @param key
     * @return 覆盖物
     */
    OverlayOptions getOverLayByKey(String key);

    // 显示覆盖物最佳位置
    void setViewPort(String key);

}
