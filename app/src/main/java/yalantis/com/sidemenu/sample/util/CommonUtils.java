package yalantis.com.sidemenu.sample.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import yalantis.com.sidemenu.sample.App;

/**
 * Created by devil on 14-7-22.
 * ͨ�÷���
 */
public class CommonUtils {

    private final static Context APP_CONTEXT = App.getContext();

    private CommonUtils() {
        throw new AssertionError();
    }

    /**
     * ��ȡ����Ϣ
     *
     * @return ����Ϣ
     */
    public static PackageInfo getVersionInfo() {
        try {
            PackageManager pm = APP_CONTEXT.getPackageManager();
            return pm.getPackageInfo(APP_CONTEXT.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ��ǰϵͳ�汾���ڵ�������İ汾��
     *
     * @param versionCode
     * @return
     */
    public static boolean gtVersion(int versionCode) {
        return Build.VERSION.SDK_INT >= versionCode;
    }

    /**
     * ��������Ƿ����
     *
     * @return true���ã� false������
     */
    public static boolean isNetWorkAvailable() {
        boolean result;
        ConnectivityManager cm = (ConnectivityManager) APP_CONTEXT.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /**
     * �ֻ���
     *
     * @param duration ʱ��ms
     */
    public static void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) APP_CONTEXT.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }

    /**
     * ͨ������, ��ö���Classʱ�����ĸ���ķ��Ͳ���������. ���޷��ҵ�, ����Object.class.
     *
     * @param clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be
     * determined
     */
    @SuppressWarnings("unchecked")
    public static Class<Object> getSuperClassGenricType(final Class clazz, final int index) {

        //���ر�ʾ�� Class ����ʾ��ʵ�壨�ࡢ�ӿڡ��������ͻ� void����ֱ�ӳ���� Type��
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        //���ر�ʾ������ʵ�����Ͳ����� Type ��������顣
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * �����ȡ��
     *
     * @param className
     * @return class
     */
    public static Class<?> getClassByString(String className) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cls;
    }

    /**
     * ��ȡ��Ļ���
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * ��ȡ��Ļ�߶�
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    /**
     * �����ֻ��ֱ��ʴ�dpת��px
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        final float scale = APP_CONTEXT.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * ��sp��px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = APP_CONTEXT.getResources().getDisplayMetrics().density;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * �������ֿ��
     *
     * @param paint
     * @param str
     * @return
     */
    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    /**
     * �������뷨����
     *
     * @param context
     */
    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            final View view = ((Activity) context).getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * ��ʾ���뷨����
     *
     * @param view
     */
    public static void showKeyboard(EditText view) {
        InputMethodManager imm = (InputMethodManager) APP_CONTEXT.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * ����item�߶���������ListView�߶�
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
    }

    /**
     * ����CheckBox����sdk�汾��ɵĲ���(С��16paddingδ����button�Ŀ��)
     * @param checkBox �ؼ�
     * @param pLeft ��߾�
     * @param pRight �ұ��
     * @param resid ͼƬ��Դid
     */
    public static void setCheckBox(CheckBox checkBox, int pLeft, int pRight, int resid) {
        int width = BitmapFactory.decodeResource(APP_CONTEXT.getResources(), resid).getWidth();
        checkBox.setButtonDrawable(resid);
        int paddingLeft = checkBox.getPaddingLeft() + Build.VERSION.SDK_INT <= 16 ? width : 0;
        checkBox.setPadding(paddingLeft + dip2px(pLeft), checkBox.getPaddingTop(), checkBox.getPaddingRight() +
                dip2px(pRight), checkBox.getPaddingBottom());
    }

    /**
     * ������Ϣ
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean saveStringToLocal(String key, String value) {
        SharedPreferences sharedPreferences = APP_CONTEXT.getSharedPreferences(key, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//��ȡ�༭��
        editor.putString(key, value);
        return editor.commit();//�ύ�޸�
    }

    /**
     * ������Ϣ
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean saveBooleanToLocal(String key, boolean value) {
        SharedPreferences sharedPreferences = APP_CONTEXT.getSharedPreferences(key, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//��ȡ�༭��
        editor.putBoolean(key, value);
        return editor.commit();//�ύ�޸�
    }

    /**
     * ������Ϣ
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean saveIntToLocal(String key, int value) {
        SharedPreferences sharedPreferences = APP_CONTEXT.getSharedPreferences(key, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//��ȡ�༭��
        editor.putInt(key, value);
        return editor.commit();//�ύ�޸�
    }

    /**
     * ������Ϣ
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean saveFloatToLocal(String key, float value) {
        SharedPreferences sharedPreferences = APP_CONTEXT.getSharedPreferences(key, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//��ȡ�༭��
        editor.putFloat(key, value);
        return editor.commit();//�ύ�޸�
    }

    /**
     * ��ȡ��Ϣ
     *
     * @param key
     * @return
     */
    public static String getStringFromLocal(String key) {
        SharedPreferences sharedPreferences = APP_CONTEXT.getSharedPreferences(key, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    /**
     * ��ȡ��Ϣ
     *
     * @param key
     * @return
     */
    public static boolean getBooleanFromLocal(String key, boolean defValue) {
        SharedPreferences sharedPreferences = APP_CONTEXT.getSharedPreferences(key, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static boolean getBooleanFromLocal(String key) {
        return getBooleanFromLocal(key, false);
    }

    /**
     * ��ȡ��Ϣ
     *
     * @param key
     * @return
     */
    public static int getIntFromLocal(String key) {
        SharedPreferences sharedPreferences = APP_CONTEXT.getSharedPreferences(key, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * ��ȡ��Ϣ
     *
     * @param key
     * @return
     */
    public static float getFloatFromLocal(String key) {
        SharedPreferences sharedPreferences = APP_CONTEXT.getSharedPreferences(key, Activity.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, 0);
    }

    /**
     * ��ʾ��ʾ
     *
     * @param message
     */
    public static void showToast(String message) {
        Toast.makeText(APP_CONTEXT, message, Toast.LENGTH_LONG).show();
    }

    public static void showToast(int resId) {
        Toast.makeText(APP_CONTEXT, resId, Toast.LENGTH_LONG).show();
    }

    /**
     * ������λ������ǰ��0������λ
     *
     * @param number
     * @return
     */
    public static String getDoubleDigit(int number) {
        StringBuilder builder = new StringBuilder();
        if (number >= 0 && number < 10) {
            builder.append("0");
        }
        builder.append(number).toString();
        return builder.toString();
    }

    /**
     * ��ʽ������
     *
     * @param millis �Ժ���Ϊ��λ
     * @param flags  ���ڸ�ʽ��־
     * @return
     */
    public static String formatDateTime(long millis, int flags) {
        return DateUtils.formatDateTime(APP_CONTEXT, millis, flags);
    }

    /**
     * ��ʽ����ǰʱ��
     *
     * @param flags
     * @return
     */
    public static String formatCurrentTime(int flags) {
        return formatDateTime(System.currentTimeMillis(), flags);
    }

    /**
     * ��ʽ������Ϊ����ʱ��
     *
     * @param date
     * @return
     */
    public static String formatMDHM(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM��dd�� HH:mm");
        if (date == null) return "";
        return dateFormat.format(date);
    }

    /**
     * ��ȡʱ���ʽ
     *
     * @param format
     * @param date
     * @return
     */
    public static String getTimeFormat(String format, Date date) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    public static String getTimeFormat(String format, long timestamp) {
        return getTimeFormat(format, new Date(timestamp * 1000));
    }

    /**
     * ������������֮���������
     *
     * @param beginDate ��ʼ����
     * @param endDate   ��������
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date beginDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            beginDate = sdf.parse(sdf.format(beginDate));
            endDate = sdf.parse(sdf.format(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * ���ַ�����ȡ����
     *
     * @param timeStr
     * @param format
     * @return
     */
    public static Date getDateFromString(String timeStr, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ���ݵ�ǰʱ�䷵��ͨ��ʱ��ֵ
     *
     * @param date
     * @return
     */
    public static String commonTime(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int y1 = c.get(Calendar.YEAR);
        int d1 = c.get(Calendar.DAY_OF_YEAR);
        long t1 = c.getTimeInMillis();
        c.setTime(new Date());
        int y2 = c.get(Calendar.YEAR);
        int d2 = c.get(Calendar.DAY_OF_YEAR);
        long t2 = c.getTimeInMillis();
        int yearGap = y2 - y1;
        int dayGap = d2 - d1; // ������ʱ���������
        long timeGap = (t2 - t1) / 1000;//������ʱ���������
        String timeStr = "";
        if (yearGap == 0) {//����
            if (dayGap == 0) {// ���죬ֱ����ʾʱ��
                if (timeGap > 60 * 60 * 4){// 4Сʱ-24Сʱ
                    timeStr = getTimeFormat("HH:mm", date);
                } else if (timeGap > 60 * 60) {// 1Сʱ-24Сʱ
                    timeStr = timeGap / (60 * 60) + "Сʱǰ";
                } else if (timeGap > 60) {// 1����-59����
                    timeStr = timeGap / 60 + "����ǰ";
                } else {// 1����-59����
                    timeStr = "�ո�";
                }
            } else if (dayGap == 1) {// ����+ʱ��
                timeStr = "���� " + getTimeFormat("HH:mm", date);
            } else if (dayGap == 2) {// ǰ��+ʱ��
                timeStr = "ǰ�� " + getTimeFormat("HH:mm", date);
            } else {// ����3�죬��ʾ�������ռ�ʱ��
                timeStr = getTimeFormat("MM-dd HH:mm", date);
            }
        } else {//�ǵ�����ʵ�����������ռ�ʱ��
            timeStr = getTimeFormat("yyyy-MM-dd", date);
        }
        return timeStr;

    }

    /**
     * ���г��еı����
     *
     * @param context ������
     */
    public static void gotoAppMarket(Context context) {
//        String str = "market://search?q=pname:" + context.getPackageName();
        String str = "market://details?id=" + context.getPackageName();
        Intent marketIntent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(marketIntent, 0);
        switch (resInfo.size()){
            case 0:
                CommonUtils.showToast("��δ��װӦ���г����޷����֣�");
                break;
            case 1:
                //ֱ������Ψһ���г�
                context.startActivity(marketIntent);
                break;
            default:
                //�����г�ѡ�����
                context.startActivity(Intent.createChooser(marketIntent, "��ѡ��"));
                break;
        }
    }

    /**
     * ����ϵͳ����
     */
    public static void playSystemSound() {
        // Uri alert =
        // RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        // alert =
        // RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // alert =
        // RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(App.getContext(), notification);
        r.play();
    }

}
