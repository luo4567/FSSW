package main.gis.money.waterinfo.util.volley;

import java.util.Map;

/**
 * 网络数据请求URL辅助类
 * Created by devil on 15/4/1.
 */
public class UrlHelper {

    public final static String HOST = "http:192.168.1.15:8001";
    public final static String API = "/api/information";
    public final static String WATERSTATIONS = HOST + API + "/topics";
    public final static String RainSTATIONS = HOST + API + "/topics";
    public final static String FloodSTATIONS = HOST + API + "/topics";
    public final static String SoilSTATIONS = HOST + API + "/topics";

    /**
     * 水情站点列表url
     * @param params 参数
     */
    public static String getWaterUrl(Map<String, Object> params){
        return resolve(WATERSTATIONS, params);
    }

    /**
     * 雨情站点列表url
     * @param params 参数
     */
    public static String getRainUrl(Map<String, Object> params){
        return resolve(RainSTATIONS, params);
    }

    /**
     * 涝情站点列表url
     * @param params 参数
     */
    public static String getFloodUrl(Map<String, Object> params){
        return resolve(FloodSTATIONS, params);
    }

    /**
     * 熵情站点列表url
     * @param params 参数
     */
    public static String getSoilUrl(Map<String, Object> params){
        return resolve(SoilSTATIONS, params);
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
