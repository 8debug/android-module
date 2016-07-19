package gesoft.ghotfix;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.IOException;

/**
 * Created by yhr on 2016/7/18.
 * 采用阿里AndFix进行热修复
 */

public class GHotFix {

    private final static String TAG_ERROR = GHotFix.class.getName();
    private final static String TAG_DEBUG = GHotFix.class.getName();

    private final static String MSG_ERROR = "GHotFix is error";
    private final static String MSG_DEBUG = "";

    private static PatchManager patchMgr;

    /**
     * 初始化
     * @param context
     */
    public static void setApplication(Context context){
        try {
            String appversion= context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            patchMgr = new PatchManager(context);
            patchMgr.init(appversion);//current version
            patchMgr.loadPatch();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG_ERROR, MSG_ERROR, e);
        }
    }

    /**
     * 加载补丁
     * @param path
     */
    public static void addPatch( String path ){
        try {
            patchMgr.addPatch( path );
        } catch (IOException e) {
            Log.e(TAG_ERROR, MSG_ERROR, e);
        }
    }

}
