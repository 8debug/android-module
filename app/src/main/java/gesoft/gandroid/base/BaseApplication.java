package gesoft.gandroid.base;

import android.app.Application;
import android.content.Context;

import gesoft.gapp.base.GApplication;
import gesoft.gapp.common.GApp;
import gesoft.gapp.common.GPhone;
import gesoft.gcrashemail.crash.GCrashHandler;
import gesoft.ghotfix.GHotFix;
import gesoft.push.GPushXG;

/**
 * Created by yhr on 2016/6/23.
 */
public class BaseApplication extends Application {

    protected static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        //Stetho.initializeWithDefaults(this);
        mContext = getApplicationContext();

        /*//配置fresco打印崩溃信息
        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());

        //Image pipeline 负责完成加载图像，变成Android设备可呈现的形式所要做的每个事情。
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setRequestListeners(requestListeners)
            //开启向下采样，向下采样在大部分情况下比 resize 更快
                .setDownsampleEnabled(true).build();

        Fresco.initialize(this, config);*/
        GApplication.setContext(this);
        //配置fresco打印崩溃信息
        //FLog.setMinimumLoggingLevel(FLog.ERROR);

        //信鸽推送
        GPushXG.setApplication(mContext);

        //热修复
        GHotFix.setApplication(mContext);




        /*GCrash gCrash = GCrash.getInstance();
        gCrash.setEmailMessage(GApp.getAppName(mContext)+"_"+GApp.getVersionName(mContext)+", "+ GPhone.getPhoneName()+", "+GPhone.getPhoneNumber(mContext));
        gCrash.init(mContext);*/

        /*GFIR.init(this);
        GFIR.checkUpd(new GFIR.ICheckUpdCallback() {
            @Override
            public void onSuccess(String versionJson) {
                L.d(versionJson);
            }

            @Override
            public void onFail(Exception exception) {
                L.e(exception);
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
        });*/
    }

    public static Context getContext(){
        return mContext;
    }
}
