package com.yhr;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by yhr on 2016/11/1.
 * 调试通用方法
 */

public class GDebug {

    /**
     * Facebook荣誉出品，可在chrome中调试（需翻墙）
     * 连通手机后打开你的Chrome浏览器在地址栏输入chrome://inspect
     * @param context   ApplicationContext
     */
    public static void stetho(Context context){
        Stetho.initializeWithDefaults(context);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

}
