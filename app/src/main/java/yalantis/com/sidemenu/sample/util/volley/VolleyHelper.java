package yalantis.com.sidemenu.sample.util.volley;

import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import yalantis.com.sidemenu.sample.App;

/**
 * Volley��������
 * Created by devil on 15/4/2.
 */
public class VolleyHelper {

    private static final String TAG = "Volley";
    //�����������
    private static RequestQueue requestQueue;

    /**
     * ��ȡ�����������
     * @return �������
     */
    public static RequestQueue getRequestQueue(){
        if (requestQueue==null){
            synchronized (App.class){
                requestQueue = Volley.newRequestQueue(App.getContext());
            }
        }
        return requestQueue;
    }

    /**
     * ������󵽶���
     * @param request ����
     * @param tag ��ǩ
     * @param <T> ����
     */
    public static <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1, 1.0f));
        VolleyLog.d("�������%s������:%s", tag, request.getUrl());
        getRequestQueue().add(request);
    }

    public static <T> void addToRequestQueue(Request<T> request) {
        addToRequestQueue(request, null);
    }

    /**
     * ȡ�������ض���ǩ����������
     * @param tag ��ǩ
     */
    public static void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

}
