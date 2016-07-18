package gesoft.gupd;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

/**
 * Created by yhr on 2016/7/4.
 */
public class GFIR {

    //fir.im网站用户的唯一token
    private static String FIR_TOKEN;
    private final static String TAG_ERROR = "error_ge";
    private final static String TAG_DEBUG = "debug_ge";

    private static ICheckUpdCallback mICheckUpdCallback;

    public interface ICheckUpdCallback{
        void onSuccess(String versionJson);
        void onFail(Exception exception);
        void onStart();
        void onFinish();
    }

    /**
     * 初始化
     * @param appliction
     */
    public static void init(Context appliction){
        try {
            ApplicationInfo appInfo = appliction.getPackageManager().getApplicationInfo(appliction.getPackageName(), PackageManager.GET_META_DATA);
            FIR_TOKEN = appInfo.metaData.getString("BUG_HD_SDK_GENERAL_KEY");
            FIR.init(appliction);
            Log.d(TAG_DEBUG, FIR_TOKEN);
        } catch (Exception e) {
            Log.e(TAG_ERROR, TAG_ERROR, e);
        }
    }

    /**
     * 检测更新的回调函数
     * @param iCheckUpdCallback
     */
    public static void checkUpd( ICheckUpdCallback iCheckUpdCallback  ){

        mICheckUpdCallback = iCheckUpdCallback;

        FIR.checkForUpdateInFIR( FIR_TOKEN , new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {
                mICheckUpdCallback.onSuccess(versionJson);
            }

            @Override
            public void onFail(Exception exception) {
                mICheckUpdCallback.onFail(exception);
            }

            @Override
            public void onStart() {
                mICheckUpdCallback.onStart();
            }

            @Override
            public void onFinish() {
                mICheckUpdCallback.onFinish();
            }
        });
    }

    /**
     * 一键检测更新
     */
    public static void checkUpd(){
        checkUpd(mICheckUpdCallback);
    }

}
