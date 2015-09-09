package main.gis.money.waterinfo.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.Date;
import java.util.List;

import main.gis.money.waterinfo.App;
import main.gis.money.waterinfo.MainActivity;
import main.gis.money.waterinfo.R;
import main.gis.money.waterinfo.entity.StationInfo;
import main.gis.money.waterinfo.entity.Stations;
import main.gis.money.waterinfo.holder.IconTreeItemHolder;
import main.gis.money.waterinfo.holder.ProfileHolder;
import main.gis.money.waterinfo.holder.SocialViewHolder;
import main.gis.money.waterinfo.ui.Fragment.MapFragment;
import main.gis.money.waterinfo.util.volley.DateTypeAdapter;
import main.gis.money.waterinfo.util.volley.VolleyErrorHelper;
import main.gis.money.waterinfo.util.volley.VolleyHelper;
import money.gis.bmlibrary.BMap;

/**
 * Created by Administrator on 2015/8/17.
 */
public class TreeUtil {
    private AndroidTreeView treeView;
    private MainActivity context;
    private LinearLayout treeLayout;
    private BMap myBaiduMap;
    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_gcoding);

    public TreeUtil(MainActivity context, MapFragment mapFragment) {
        this.context = context;
        this.treeLayout = (LinearLayout)context.findViewById(R.id.treeView);;
        myBaiduMap = mapFragment.getMyBaiduMap();
    }

    private TreeNode createNode(String rootName, List<StationInfo> stationInfos) {
        TreeNode root = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_person, rootName)).setViewHolder(new ProfileHolder(context));
        for (StationInfo stationInfo : stationInfos) {
            TreeNode facebook = new TreeNode(new SocialViewHolder.SocialItem(R.string.ic_post_facebook, stationInfo.getStationName(), stationInfo.getCheckTime(), stationInfo.getCheckValue(), "米")).setViewHolder(new SocialViewHolder(context));
            myBaiduMap.addPoint(Double.parseDouble(stationInfo.getLongitude()), Double.parseDouble(stationInfo.getLatitude()), stationInfo.getStationCode(), stationInfo.getStationName(), false, false, bitmapDescriptor,121);
            root.addChild(facebook);
        }
        return root;
    }

    public void UpdateTreeView(List<Stations> stations){
        // 根据区域名称分类
        final TreeNode root = TreeNode.root();
        for (int i = 0; i < stations.size(); i++) {
            String cityName = stations.get(i).getCityName();
            List<StationInfo> stationInfos = stations.get(i).getStationInfos();
            TreeNode children = createNode(cityName, stationInfos);
            root.addChild(children);
        }

        treeView = new AndroidTreeView(context, root);
        treeView.setDefaultAnimation(true);
        treeView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
        treeLayout.addView(treeView.getView());
    }
}
