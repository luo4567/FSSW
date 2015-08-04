package yalantis.com.sidemenu.sample.util;

import android.util.Log;

import yalantis.com.sidemenu.sample.App;


/**
 * ���ߣ�ħ�� E-mail��iwhs@qq.com ����ʱ�䣺2014��5��21�� ����10:10:30 ��˵����log�����
 */

public class LogUtil {

    public enum LaunchMode {
        DEBUG, RELEASE
    }

    public static void e(Class<?> clazz, Object msg, Throwable tr) {
        if (App.LAUNCH_MODE == LaunchMode.DEBUG) {
            String tag = "";
            if (null != clazz) {
                tag = "������" + clazz.getSimpleName() + " ";
            }
            Log.e(App.TAG, tag + msg, tr);
        }
    }

    public static void e(Class<?> clazz, Object msg) {
        e(clazz, msg, null);
    }

    public static void d(Class<?> clazz, Object msg) {
        if (App.LAUNCH_MODE == LaunchMode.DEBUG) {
            String tag = "";
            if (null != clazz) {
                tag = "������" + clazz.getSimpleName() + " ";
            }
            Log.d(App.TAG, tag + msg);
        }
    }

    /**
     * System.out�������
     */
    public static void sysout(Class<?> clazz, Object msg){
        if (App.LAUNCH_MODE == LaunchMode.DEBUG){
            String tag = "";
            if (null != clazz) {
                tag = "������" + clazz.getSimpleName() + " ";
            }
            System.out.println(tag + msg);
        }
    }

    public static void sysout(Object msg){
        sysout(null, msg);
    }

    /**
     * ������ʾ
     */
    public final static void toast(Class<?> clazz, Object msg){
        if (App.LAUNCH_MODE == LaunchMode.DEBUG){
            String tag = "";
            if (null != clazz) {
                tag = "������" + clazz.getSimpleName() + " ";
            }
            CommonUtils.showToast(tag + msg);
        }
    }

    public static void toast(Object msg){
        toast(null, msg);
    }

}
