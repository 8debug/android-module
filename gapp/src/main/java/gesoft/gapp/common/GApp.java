package gesoft.gapp.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * 跟App相关的辅助类
 *
 *
 *
 */
public class GApp
{

    private GApp()
    {
		/* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context)
    {
        try{
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context)
    {
        try
        {

            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /*public static String getAppName(Context context){
        Resources appR = context.getResources();
        CharSequence txt = appR.getText(appR.getIdentifier("app_name", "string", context.getPackageName()));
        return txt+"";
    }*/

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

}
