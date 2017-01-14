package com.example.ywifi;

import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;
import static com.example.ywifi.YWifiConstant.CHANGE_RESULT_CONNECTING;
import static com.example.ywifi.YWifiConstant.CHANGE_RESULT_FAILED;
import static com.example.ywifi.YWifiConstant.CHANGE_RESULT_OK;
import static com.example.ywifi.YWifiConstant.EXTRA_CHANGE_RESULT;
import static com.example.ywifi.YWifiConstant.EXTRA_HSCRAUTO_FLAG;
import static com.example.ywifi.YWifiConstant.EXTRA_TARGET_SSID;
import static com.example.ywifi.YWifiConstant.FLAG_CHANGE_PROGRESS;
import static com.example.ywifi.YWifiConstant.WIFI_CHANGE_RECEIVER_ACTION;
import static com.example.ywifi.YWifiConstant.WIFI_CHANGE_STATE_ACTION;

/**
 * Created by yhr on 2017/1/1.
 *
 */

public class YWifi {

    private static Context mContext;
    private static WifiManager mWifiMgr;

    //DRS的wifi信息
    private static ScanResult mScanResultDRS;
    //外网的wifi信息
    private static ScanResult mScanResultWLAN;

    public static void setContext( Context context ){
        mContext = context;
        mWifiMgr = (WifiManager)mContext.getSystemService(WIFI_SERVICE);
    }

    public static void setScanResultDRS( ScanResult scanResult ){
        mScanResultDRS = scanResult;
        if( mScanResultWLAN !=null && mScanResultWLAN.SSID.equals(scanResult.SSID) ){
            mScanResultWLAN = null;
        }
    }

    public static void setScanResultWLAN( ScanResult scanResult ){
        mScanResultWLAN = scanResult;
        if( mScanResultDRS !=null && mScanResultDRS.SSID.equals(scanResult.SSID) ){
            mScanResultDRS = null;
        }
    }

    public static ScanResult getScanResultDRS(){
        return mScanResultDRS;
    }

    public static ScanResult getScanResultWLAN(){
        return mScanResultWLAN;
    }

    public final static int NETWORK_TYPE_DRS = 1;
    public final static int NETWORK_TYPE_WLAN = 0;

    /**
     * 获取当前已经连接的wifi热点ip
     * @return ipDRS
     */
    public static String getGateWayIp(){
        DhcpInfo dhcp = mWifiMgr.getDhcpInfo();
        String ipDRS = Formatter.formatIpAddress(dhcp.gateway);
        return ipDRS;
    }
    /**
     * 获取已连接wifi的SSID，SSID中带有""
     * @return
     */
    public static String getWifiSSID(){
        return mWifiMgr.getConnectionInfo().getSSID();
    }

    /**
     * 切换网络
     * @param networkType
     * @return
     */
    public static int connectWifi( int networkType, int flag ){
        ScanResult scanResult = networkType == NETWORK_TYPE_DRS ? mScanResultDRS: mScanResultWLAN;
        List<WifiConfiguration> listWifiConfig =  mWifiMgr.getConfiguredNetworks();
        WifiInfo wifiInfo = mWifiMgr.getConnectionInfo();
        String needConnectSSID = "\""+scanResult.SSID+"\"";
        int result = 0;

        //当前网络与要切换的网络为同一个网络
        if( wifiInfo.getSSID().equals(needConnectSSID) ){
            Log.d("ywifi", " des wifi is current wifi ");
            result = CHANGE_RESULT_OK;
        }
        //开始切换网络
        else{
            for ( WifiConfiguration config : listWifiConfig ) {
                if( config.SSID.equals( needConnectSSID ) ){
                    mWifiMgr.setWifiEnabled(true);
                    mWifiMgr.enableNetwork(config.networkId, true);
                    result = CHANGE_RESULT_CONNECTING;
                    YWifiConstant.init();
                }
            }
        }

        //发送"正在切换"状态的广播，用于显示progress
        if( result==CHANGE_RESULT_CONNECTING ){
            Intent intent = new Intent(WIFI_CHANGE_STATE_ACTION);
            intent.putExtra(EXTRA_CHANGE_RESULT, CHANGE_RESULT_CONNECTING);
            intent.putExtra(EXTRA_HSCRAUTO_FLAG, FLAG_CHANGE_PROGRESS);
            intent.putExtra(EXTRA_TARGET_SSID, scanResult.SSID);
            mContext.sendBroadcast(intent);
        }

        if( result==CHANGE_RESULT_CONNECTING || result==CHANGE_RESULT_OK ){
            Intent intent = new Intent(WIFI_CHANGE_RECEIVER_ACTION);
            intent.putExtra(EXTRA_CHANGE_RESULT, result);
            intent.putExtra(EXTRA_HSCRAUTO_FLAG, flag);
            intent.putExtra(EXTRA_TARGET_SSID, scanResult.SSID);
            mContext.sendBroadcast(intent);
        }else{
            result = CHANGE_RESULT_FAILED;
            Toast.makeText(mContext, "请先在系统设置中连接"+needConnectSSID, Toast.LENGTH_SHORT).show();
            Log.d("hevision","哦！切换失败");
        }

        return result;

    }

}
