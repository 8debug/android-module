package gesoft.gandroid.base;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import gesoft.gapp.common.L;
import gesoft.gupd.GFIR;
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
        GFIR.init(this);
        GFIR.checkUpd(new GFIR.ICheckUpdCallback() {
            @Override
            public void onSuccess(String versionJson) {
                L.d(versionJson);
            }

            @Override
            public void onFail(Exception exception) {
                L.d("更新失败");
            }

            @Override
            public void onStart() {
                L.d("正在检测更新");
            }

            @Override
            public void onFinish() {
                L.d("更新完成");
            }
        });
    }

    public static Context getContext(){
        return mContext;
    }
}
