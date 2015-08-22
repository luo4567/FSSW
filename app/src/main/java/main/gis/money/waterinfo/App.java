package main.gis.money.waterinfo;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import main.gis.money.waterinfo.util.LogUtil;

/**
 * Created by Administrator on 2015/8/9.
 */
public class App extends Application{

    /**
     * 程序运行模式（调试、发布）
     */
    public final static LogUtil.LaunchMode LAUNCH_MODE = LogUtil.LaunchMode.DEBUG;

    /**
     * 程序标签
     */
    public final static String TAG = "WaterInfo";

    /**
     * 访问令牌，用来判断是否登录
     * 用户名
     * 头像地址
     */
    public String access_token;

    // 应用程序上下文
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
    }

    /**
     * 静态方法返回程序上下文
     * @return
     */
    public static synchronized App getContext() {
        return instance;
    }
}
