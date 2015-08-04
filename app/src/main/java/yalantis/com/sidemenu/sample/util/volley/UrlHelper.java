package yalantis.com.sidemenu.sample.util.volley;

import java.util.Map;

/**
 * ������������URL������
 * Created by devil on 15/4/1.
 */
public class UrlHelper {

    public final static String HOST = "https://cnodejs.org";
    public final static String API = "/api/v1";
    public final static String TOPICS = HOST + API + "/topics";
    public final static String TOPIC = HOST + API + "/topic";
    public final static String USER = HOST + API + "/user";
    public final static String ACCESS_TOKEN = HOST + API + "/accesstoken";
    public final static String REPLY_SUFFIX = "/replies";

    /**
     * �����б�url
     * @param params ����
     */
    public static String getTopicsUrl(Map<String, Object> params){
        return resolve(TOPICS, params);
    }

    /**
     * ��Ȩ��֤url
     */
    public static String getOauthUrl(){
        return ACCESS_TOKEN;
    }

    /**
     * ��������url
     * @param id ����id
     */
    public static String getTopicUrl(String id){
        return UrlHelper.resolve(TOPIC, id);
    }

    /**
     * �ظ�url
     * @param id ����id
     */
    public static String getReplyUrl(String id){
        return resolve(getTopicUrl(id), REPLY_SUFFIX);
    }

    //ƴ��url·��
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

    //ƴ�Ӳ���
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
