package gesoft.gandroid.base;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import gesoft.push.GPushXG;

/**
 * Created by yhr on 2016/6/23.
 */
public class BaseApplication extends Application {

    protected static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        mContext = getApplicationContext();
        //信鸽推送
        GPushXG.setApplication(mContext);
    }

    public static Context getContext(){
        return mContext;
    }
}
