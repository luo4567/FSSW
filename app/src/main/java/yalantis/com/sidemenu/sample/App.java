package yalantis.com.sidemenu.sample;

import android.app.Application;

import yalantis.com.sidemenu.sample.util.CommonUtils;
import yalantis.com.sidemenu.sample.util.LogUtil;
import yalantis.com.sidemenu.sample.util.constant.Params;


/**
 * ȫ�ֳ�����
 * Created by devil on 14-7-24.
 */
public class App extends Application {

    /**
     * ��������ģʽ�����ԡ�������
     */
    public final static LogUtil.LaunchMode LAUNCH_MODE = LogUtil.LaunchMode.DEBUG;

    /**
     * �����ǩ
     */
    public final static String TAG = "CNode";

    /**
     * �������ƣ������ж��Ƿ��¼
     * �û���
     * ͷ���ַ
     */
    public String access_token;

    // Ӧ�ó���������
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //Fresco.initialize(instance);
        access_token = CommonUtils.getStringFromLocal(Params.ACCESS_TOKEN);
        //��ֹĬ��ͳ��
        //MobclickAgent.openActivityDurationTrack(false);
    }

    /**
     * ��̬�������س���������
     * @return
     */
    public static synchronized App getContext() {
        return instance;
    }

}
