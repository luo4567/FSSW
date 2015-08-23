package money.gis.bmlibrary;

import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/2.
 */
public class BMap implements IMap {

    private List<OverlayOptions> overlayOptionses;
    private View activity;
    private BaiduMap baiduMap;
    private MapView mapView = null;

    public BMap(View view) {
        this.activity = view;
        overlayOptionses = new ArrayList<>();
    }

    @Override
    public BaiduMap initMap(int id, double lng, double lat, int zoom) {
/*        BaiduMapOptions mapOptions = new BaiduMapOptions();
        //设置是否显示缩放控件
        mapOptions.zoomControlsEnabled(false);
        //设置是否允许俯视手势
        mapOptions.overlookingGesturesEnabled(false);
        //设置是否允许旋转手势
        mapOptions.rotateGesturesEnabled(false);*/
        LatLng centerPoint = new LatLng(lat, lng);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(centerPoint)
                .zoom(zoom)
                .build();
        mapView = (MapView) activity.findViewById(id);
        mapView.showZoomControls(false);
        mapView.showScaleControl(true);
        baiduMap = mapView.getMap();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.setMapStatus(mMapStatusUpdate);
        return baiduMap;
    }

    @Override
    public void addPoint(double lng, double lat, String key, String name, boolean dragable, boolean isShow, BitmapDescriptor icon, int stationType) {
        OverlayOptions marker = getOverLayByKey(key);
        if (marker != null) {
            return;
        }
        LatLng point = new LatLng(lat, lng);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .title(name)
                .draggable(dragable)
                .visible(isShow)
                .extraInfo(SetBundle(key, name, 0, stationType))
                .icon(icon);
        baiduMap.addOverlay(option);
        addLabel(point, key, name, isShow);
        overlayOptionses.add(option);
    }

    @Override
    public void addPolyline(String geometry, String name, String key, int color, boolean visible) {
        OverlayOptions polyline = getOverLayByKey(key);
        if (polyline != null) {
            return;
        }
        List<LatLng> points = Convert(geometry);
        OverlayOptions polylineOption = new PolylineOptions()
                .points(points)
                .extraInfo(SetBundle(key, name, 1, 0))
                .color(color);
        baiduMap.addOverlay(polylineOption);
        addLabel(points.get(0), key, name, visible);
    }

    @Override
    public void addPolygon(String geometry, String name, String key, int strokeColor, int fillColor, boolean visible) {
        OverlayOptions polygon = getOverLayByKey(key);
        if (polygon != null) {
            return;
        }
        //构建用户绘制多边形的Option对象
        List<LatLng> points = Convert(geometry);
        OverlayOptions polygonOption = new PolygonOptions()
                .points(points)
                .extraInfo(SetBundle(key, name, 2, 0))
                .stroke(new Stroke(5, strokeColor))
                .fillColor(fillColor);
        //在地图上添加多边形Option，用于显示
        baiduMap.addOverlay(polygonOption);
        addLabel(points.get(0), key, name, visible);
    }

    @Override
    public void addLabel(LatLng point, String key, String name, boolean visible) {
        OverlayOptions textOption = new TextOptions()
                .extraInfo(SetBundle(key + "_label", name, -1, 0))
                .text(name)
                .position(point)
                .visible(visible);
        baiduMap.addOverlay(textOption);
    }

    @Override
    public void setImageUrl(String key, BitmapDescriptor icon) {
        MarkerOptions options = (MarkerOptions) this.getOverLayByKey(key);
        options.icon(icon);
    }

    @Override
    public OverlayOptions getOverLayByKey(String key) {
        //List<OverlayOptions> overlayOptionses = manager.getOverlayOptions();
        for (int i = 0; i < overlayOptionses.size(); i++) {
            try {
                OverlayOptions options = overlayOptionses.get(i);
                Bundle bundle = null;
                if (options instanceof MarkerOptions) {
                    bundle = ((MarkerOptions) options).getExtraInfo();
                } else if (options instanceof PolygonOptions) {
                    bundle = ((PolygonOptions) options).getExtraInfo();
                } else if (options instanceof PolylineOptions) {
                    bundle = ((PolygonOptions) options).getExtraInfo();
                }
                if (bundle.getString("key") == key) {
                    return options;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void setViewPort(String key) {

    }

    /**
     * 设置覆盖物的额外信息
     *
     * @param key
     * @param name
     * @param type
     * @return
     */
    private Bundle SetBundle(String key, String name, int type, int stationType) {
        Bundle info = new Bundle();
        info.putString("key", key);
        info.putString("name", name);
        info.putInt("type", type);
        info.putInt("stationType", stationType);
        return info;
    }

    /**
     * 将字符串坐标转换成数组坐标
     *
     * @param geometry
     * @return
     */
    private List<LatLng> Convert(String geometry) {
        List<LatLng> latLngs = new ArrayList<>();
        String[] geometryPoints = geometry.split(";");
        for (int i = 0; i < geometryPoints.length; i++) {
            String[] latlng = geometryPoints[i].split(",");
            latLngs.add(new LatLng(Double.parseDouble(latlng[0]), Double.parseDouble(latlng[1])));
        }
        return latLngs;
    }
}
