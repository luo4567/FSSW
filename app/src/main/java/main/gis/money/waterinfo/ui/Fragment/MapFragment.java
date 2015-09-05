package main.gis.money.waterinfo.ui.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.search.core.VehicleInfo;
import com.zcw.togglebutton.ToggleButton;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import main.gis.money.waterinfo.R;
import main.gis.money.waterinfo.ui.activity.ShowInfoActivity;
import main.gis.money.waterinfo.util.volley.UrlHelper;
import money.gis.bmlibrary.BMap;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class MapFragment extends Fragment implements ScreenShotable {

    private ToggleButton toggleBtn;
    private TextView mapType;

    public BMap getMyBaiduMap() {
        return myBaiduMap;
    }

    private BaiduMap baiduMap;

    BMap myBaiduMap;
    private Bitmap bitmap;
    private View containerView;

    public static MapFragment newInstance() {
        MapFragment contentFragment = new MapFragment();
        return contentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        final View view = inflater.inflate(R.layout.fragment_map, null);

        NiceSpinner niceSpinner = (NiceSpinner) view.findViewById(R.id.region_choice);
        mapType= (TextView) view.findViewById(R.id.mapType);
        toggleBtn= (ToggleButton) view.findViewById(R.id.toggle_mapType);
        toggleBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on){
                    mapType.setText("卫星");
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }else{
                    mapType.setText("地图");
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }
            }
        });
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        niceSpinner.attachDataSource(dataset);
        myBaiduMap = new BMap(view);
        baiduMap = myBaiduMap.initMap(R.id.bmapView, 113.255552, 23.121212, 12);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_gcoding);
        myBaiduMap.addPoint(113.255552, 23.121212, "test", "test", false, true, bitmapDescriptor, 121);
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                String stationCode = bundle.getString("key");
                int stationType = bundle.getInt("stationType");
                // TODO: 2015/8/22 获取历史信息
                Map<String, Object> params = new HashMap<>();
                params.put("stationCode", stationCode);
                params.put("type", stationType);
                String url = UrlHelper.getStationHistoryUrl(params);
                Intent showInfo = new Intent(MapFragment.this.getActivity(), ShowInfoActivity.class);
                startActivity(showInfo);
                LineChartFragment lineChart = new LineChartFragment();
                return true;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void takeScreenShot() {
        // TODO: 2015/8/16 根据点击的类型获取数据
//		Thread thread = new Thread() {
//			@Override
//			public void run() {
//				Log.d("view",containerView.toString());
//				Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
//						containerView.getHeight(), Bitmap.Config.ARGB_8888);
//				Canvas canvas = new Canvas(bitmap);
//				containerView.draw(canvas);
//				MapFragment.this.bitmap = bitmap;
//			}
//		};
//
//		thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
