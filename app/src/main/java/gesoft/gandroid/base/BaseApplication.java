package gesoft.gandroid.base;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by Administrator on 2016/6/23.
 */
public class BaseApplication extends Application {

    protected static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
