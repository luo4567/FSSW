package money.gis.bmplibary;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.Overlay;
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
    BaiduMap InitMap(int id, double lng, double lat, int zoom);

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
    void AddPoint(double lng, double lat, String key, String name, boolean dragable, boolean isShow, BitmapDescriptor icon);

    /**
     * 添加线
     * @param geometry
     * @param name
     * @param key
     * @param color
     */
    void AddPolyline(String geometry,String name,String key,int color,boolean visible);

    /**
     * 添加面
     * @param geometry
     * @param name
     * @param key
     * @param strokeColor
     * @param fillColor
     */
    void AddPolygon(String geometry, String name, String key,int strokeColor,int fillColor,boolean visible);

    /**
     * 添加文字标签
     * @param point
     * @param key
     * @param name
     * @param visible
     */
    void AddLabel(LatLng point,String key,String name,boolean visible);

    // 设置点图标
    void SetImageUrl();

    /**
     * 根据键值获取覆盖物
     * @param key
     * @return 覆盖物
     */
    OverlayOptions GetOverLayByKey(String key);

    // 显示覆盖物最佳位置
    void SetViewPort(String key);

}
