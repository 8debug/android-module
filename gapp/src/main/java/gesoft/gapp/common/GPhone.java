package gesoft.gapp.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/8/12.
 */
public class GPhone {

    /**
     * 获取手机型号
     * @return
     */
    public static String getPhoneName(){
        return Build.MODEL;
    }

    /**
     * 获取手机的android版本
     * @return
     */
    public static String getAndroidVersion(){
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机IMEI码
     * @param context
     * @return
     */
    public static String getIMEI(Context context){
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTm.getDeviceId();
    }

    /**
     * 获取手机号码
     * @param context
     * @return
     */
    public static String getPhoneNumber(Context context){
        TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mgr.getLine1Number();
    }


    /**
     * 获取屏幕宽度, 屏幕高度, 屏幕密度, 屏幕密度DPI
     * @param context
     * @return DisplayMetrics
     */
    public static DisplayMetrics getMetric( Context context ){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics display = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(display);
        return display;
    }

}
