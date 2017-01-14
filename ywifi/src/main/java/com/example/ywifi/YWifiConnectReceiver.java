package com.example.ywifi;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.hscr.common.L;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;
import static android.net.ConnectivityManager.TYPE_WIFI;
import static com.hscr.demo.DemoConstant.CHANGE_RESULT_OK;
import static com.hscr.demo.DemoConstant.EXTRA_CHANGE_RESULT;
import static com.hscr.demo.DemoConstant.EXTRA_HSCRAUTO_FLAG;
import static com.hscr.demo.DemoConstant.EXTRA_TARGET_SSID;
import static com.hscr.demo.DemoConstant.EXTRA_WIFI_INFO;
import static com.hscr.demo.DemoConstant.WIFI_CHANGE_RECEIVER_ACTION;
import static com.hscr.demo.DemoConstant.WIFI_CHANGE_STATE_ACTION;

/**
 * Created by yhr on 2017/1/4.
 * 接收wifi切换并连接上的广播
 */

public class YWifiConnectReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent data) {

        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //List<Long> listTime = YWifiConstant.getListTime();

        if( WIFI_CHANGE_RECEIVER_ACTION.equals( data.getAction() ) ){
            YWifiConstant.setFlag( data.getIntExtra(EXTRA_HSCRAUTO_FLAG, 0) );
            YWifiConstant.setTargetSSID( data.getStringExtra(EXTRA_TARGET_SSID) );
            //目标网络就是当前网络
            if( CHANGE_RESULT_OK == data.getIntExtra(EXTRA_CHANGE_RESULT, 0) ){
                WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
                Intent intent = new Intent(WIFI_CHANGE_STATE_ACTION);
                intent.putExtra( EXTRA_HSCRAUTO_FLAG, YWifiConstant.getFlag() );
                intent.putExtra( EXTRA_WIFI_INFO, wifiInfo );
                context.sendBroadcast(intent);
            }
        }

        if(CONNECTIVITY_ACTION.equals(data.getAction())){

            NetworkInfo info = cm.getActiveNetworkInfo();
            WifiInfo wifiInfo = mWifiManager.getConnectionInfo();

            if ( info != null && info.getType()==TYPE_WIFI && info.getState() == NetworkInfo.State.CONNECTED ) {

                //网络切换正确
                if( !TextUtils.isEmpty( YWifiConstant.getTargetSSID() ) &&
                        wifiInfo.getSSID().equals("\""+ YWifiConstant.getTargetSSID() +"\"") &&
                        !wifiInfo.getSSID().equals( YWifiConstant.getConnectedSSID() )  ){
                    L.d(" wifi has changed "+wifiInfo.getSSID());
                    YWifiConstant.setConnectedSSID( wifiInfo.getSSID() );
                    Intent intent = new Intent(WIFI_CHANGE_STATE_ACTION);
                    intent.putExtra( EXTRA_HSCRAUTO_FLAG, YWifiConstant.getFlag() );
                    intent.putExtra( EXTRA_WIFI_INFO, wifiInfo );
                    context.sendBroadcast(intent);
                }
                //网络切换与期望的不符
                else if( TextUtils.isEmpty( YWifiConstant.getTargetSSID() ) ||
                        !wifiInfo.getSSID().equals("\""+ YWifiConstant.getTargetSSID() +"\"") ){

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("网络切换错误");
                    builder.setMessage("当前网络与要使用的网络不符，请检查网络设置");
                    builder.create().show();
                }

                /*listTime.add(System.currentTimeMillis());
                //避免接收到两次，用最后接收到广播的系统时间减去倒数第二个系统时间若大于1秒就执行
                if( listTime.size()>1 ){
                    int lastIndex = listTime.size()-1;
                    long lastTime = listTime.get( lastIndex );
                    long lastTimeprev = listTime.get( lastIndex-1 );
                    if( lastTime-lastTimeprev>1000 ){ }
                }*/
            }
        }
    }
}
