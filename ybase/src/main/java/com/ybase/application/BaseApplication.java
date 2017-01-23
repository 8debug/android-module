package com.ybase.application;

import android.app.Application;

/**
 * Created by yhr on 2017/1/14.
 *
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;

    public static BaseApplication getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
