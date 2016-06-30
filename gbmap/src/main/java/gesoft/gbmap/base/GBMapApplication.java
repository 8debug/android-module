package gesoft.gbmap.base;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;

import gesoft.gbmap.L;
import gesoft.gbmap.service.LocationService;

/**
 * Created by yhr on 2016/6/30.
 * 封装地图功能
 */

public class GBMapApplication extends Application {

    public static LocationService locationService;
    public static Vibrator mVibrator;
    public static Context instance;

    public static void setApplicationContext(Context applicationContext){
        try {
            instance = applicationContext;
            locationService = new LocationService(applicationContext);
            mVibrator =(Vibrator)applicationContext.getSystemService(Service.VIBRATOR_SERVICE);
            SDKInitializer.initialize(applicationContext);
        } catch (Exception e) {
            L.e(e);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /***
         * 初始化定位sdk，建议在Application中创建
         */
        setApplicationContext(this);
    }
}
