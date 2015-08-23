package main.gis.money.waterinfo.util.volley;

import java.util.Map;

/**
 * 网络数据请求URL辅助类
 * Created by devil on 15/4/1.
 */
public class UrlHelper {

    public final static String HOST = "http:192.168.1.15:8001";
    public final static String API = "/api/information";
    public final static String STATIONS = HOST + API + "/topics";
    public final static String STATIONSHISTORY = HOST + API + "/GetHisotry";

    /**
     * 水情站点列表url
     * @param params 参数
     */
    public static String getStationsUrl(Map<String, Object> params){
        return resolve(STATIONS, params);
    }

    /**
     * 获取站点历史信息
     * @param params
     * @return
     */
    public static String getStationHistoryUrl(Map<String, Object> params){
        return resolve(STATIONSHISTORY, params);
    }

    //拼接url路径
    public static String resolve(String host, String path){
        StringBuilder builder = new StringBuilder(host);
        if (path.startsWith("/")&&host.endsWith("/")){
            path = path.substring(1);
        } else if (!path.startsWith("/")&&!host.endsWith("/")){
            builder.append("/");
        }
        builder.append(path);
        return builder.toString();
    }

    //拼接参数
    public static String resolve(String host, Map<String, Object> params){
        StringBuilder builder = new StringBuilder(host);
        if (!params.isEmpty()){
            builder.append("?");
            for (String key:params.keySet()){
                if (!builder.toString().endsWith("?")){
                    builder.append("&");
                }
                builder.append(key);
                builder.append("=");
                builder.append(params.get(key));
            }
        }
        return builder.toString();
    }
}
