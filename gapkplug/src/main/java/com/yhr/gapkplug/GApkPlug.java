package com.yhr.gapkplug;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.apkplug.Bundle.InstallBundler;
import org.apkplug.Bundle.installCallback;
import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import java.io.File;

/**
 * Created by yhr on 2016-10-06 0006.
 *
 */

public class GApkPlug {

    /**
     * 安装apk插件
     * @param applicationContext
     */
    public static void installApk( final Context applicationContext ){
        try {
            FrameworkFactory frameworkFactory = FrameworkFactory.getInstance();
            FrameworkInstance frame = frameworkFactory.start(null, applicationContext);
            BundleContext context = frame.getSystemBundleContext();
            InstallBundler ib=new InstallBundler(context);
            ib.installForAssets("PrinterShare.apk", "1.0.0", null, new installCallback(){
                @Override
                public void callback(int arg0, Bundle arg1) {
                    Log.d("debug_ge", (arg0==installCallback.stutas5 || arg0==installCallback.stutas7)+"" );
                    if( arg0==installCallback.stutas5 || arg0==installCallback.stutas7 ){
                        Log.d("debug_ge", String.format("插件安装 %s ： %d",arg1.getName(),arg0));
                        return;
                    }
                    else{
                        Log.d("debug_ge", "插件安装失败 ：%d"+arg0 );
                    }
                }
            });
        } catch (Exception e) {
            Log.e("tag", "msg", e);
        }
    }

    /**
     * 打印pdf
     * @param context
     * @param filePDF
     */
    public static void printerPDF( Context context, File filePDF ){
        /*Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.dynamixsoftware.printershare","com.dynamixsoftware.printershare.ActivityPrintPDF");
        intent.setComponent(comp);
        intent.setAction( Intent.ACTION_VIEW );
        intent.setType( "application/pdf");
        intent.setData( Uri.fromFile(filePDF) );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);*/
    }

}
