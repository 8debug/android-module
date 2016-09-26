package gesoft.gbmap.service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import gesoft.gbmap.GBLocation;

/**
 * Created by yhr on 2016/9/13.
 * 定位服务，启动此服务即可定位
 */
public class GLocationService extends Service{

    private GLocationBinder mLocationBinder = new GLocationBinder();
    protected Context mContext;
    protected Activity mActivity;
    private GBLocation mGBLocation;

    @Override
    public IBinder onBind(Intent intent) {
        return mLocationBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        GBLocation.setApplication(mContext.getApplicationContext());
        mGBLocation = new GBLocation(mContext.getApplicationContext());
        mGBLocation.initLocation( false );
        //mGBLocation.setIGBLocation(mLocationBinder);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
