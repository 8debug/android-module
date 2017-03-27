package com.hevision.ypush.base;

/**
 * Created by yhr on 2017-03-09.
 *
 */

public interface YPushInterface {

    void onRegisterSuccess( String token );

    void onRegisterFailed( int errorCode, String msg );
}
