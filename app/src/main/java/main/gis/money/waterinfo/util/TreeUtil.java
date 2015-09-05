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
    private Context context;
    private LinearLayout treeLayout;
    private BMap myBaiduMap;
    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_gcoding);

    public TreeUtil(Context context, LinearLayout treeLayout, MapFragment mapFragment) {
        this.context = context;
        this.treeLayout = treeLayout;
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

    /**
     * 获取远程数据
     */
    public void getDataFromServer(String url, String tab) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) {
                    // TODO: 2015/8/18 出错后操作
                    return;
                }
                TreeNode root = handleData(response);
                treeView = new AndroidTreeView(context, root);
                treeView.setDefaultAnimation(true);
                treeView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
                treeLayout.addView(treeView.getView());
                // TODO: 2015/8/18 保存到数据库
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyErrorHelper.getMessage(error, App.getContext());
            }
        });
        VolleyHelper.addToRequestQueue(request, tab);
    }

    /**
     * 处理获取到的数据
     */
    private TreeNode handleData(String response) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
        List<Stations> stations = gson.fromJson(response, new TypeToken<List<Stations>>() {
        }.getType());
        // 根据区域名称分类
        final TreeNode root = TreeNode.root();
        for (int i = 0; i < stations.size(); i++) {
            String cityName = stations.get(i).getCityName();
            List<StationInfo> stationInfos = stations.get(i).getStationInfos();
            TreeNode children = createNode(cityName, stationInfos);
            root.addChild(children);
        }
        return root;
    }
}
