package com.example.ywifi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import static com.example.ywifi.YWifiConstant.EXTRA_HSCRAUTO_FLAG;
import static com.example.ywifi.YWifiConstant.EXTRA_TARGET_SSID;
import static com.example.ywifi.YWifiConstant.FLAG_CHANGE_PROGRESS;
import static com.example.ywifi.YWifiConstant.FLAG_DRS_EXPORT;
import static com.example.ywifi.YWifiConstant.FLAG_DRS_IMPORT;
import static com.example.ywifi.YWifiConstant.FLAG_INTENET_HOME;
import static com.example.ywifi.YWifiConstant.FLAG_INTENET_SERVER;
import static com.example.ywifi.YWifiConstant.WIFI_CHANGE_STATE_ACTION;


public class WifiDemoActivity extends Activity implements View.OnClickListener {

    Button btnImport;
    Button btnExport;
    Button btnServer;
    Button btnBack;
    Button btnWificonfig;
    Button btnCurrnetwork;

    WifiManager mWifiMgr;

    ProgressDialog mPro;
    
    String TAG = "ywifi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        
        YWifi.setContext(this);
        //FIXME 此处需要填入选择的scanresult
        //YWifi.setScanResultDRS(GWifi.getScanResultDRS());
        //YWifi.setScanResultWLAN(GWifi.getScanResultWLAN());
        mWifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        mPro = new ProgressDialog(this);
        
        btnImport = (Button) findViewById(R.id.btn_import);
        btnExport = (Button) findViewById(R.id.btn_export);
        btnBack = (Button) findViewById(R.id.btn_back);
        btnWificonfig = (Button) findViewById(R.id.btn_wificonfig);
        btnCurrnetwork = (Button) findViewById(R.id.btn_currnetwork);

        IntentFilter intentFilter = new IntentFilter(WIFI_CHANGE_STATE_ACTION);
        registerReceiver(mReceiver, intentFilter);
    }

    
    public void onClick(View view) {

        if( view.getId()==R.id.btn_currnetwork ){
            ConnectivityManager mConnectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo =  mConnectivityManager.getActiveNetworkInfo();
            Log.d(TAG, networkInfo.toString());
        }else if( view.getId()==R.id.btn_wificonfig ){
            List<WifiConfiguration> wifiConfig = mWifiMgr.getConfiguredNetworks();
            for (WifiConfiguration configuration : wifiConfig) {
                Log.d(TAG, "SSID="+configuration.SSID+ ", BSSID="+configuration.BSSID+", networkid="+configuration.networkId);
            }
        }else if( view.getId()==R.id.btn_import ){
            mPro.show();
            YWifi.connectWifi( YWifi.NETWORK_TYPE_DRS, FLAG_DRS_IMPORT);
        }else if( view.getId()==R.id.btn_export ){
            mPro.show();
            Log.d(TAG," download img from DRS..... ");
            YWifi.connectWifi( YWifi.NETWORK_TYPE_DRS, FLAG_DRS_EXPORT);
        }else if( view.getId()==R.id.btn_back ){
            Log.d(TAG," back home..... ");
            YWifi.connectWifi( YWifi.NETWORK_TYPE_WLAN, FLAG_INTENET_HOME);
        }
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent data) {
            if( data.getAction().equals(WIFI_CHANGE_STATE_ACTION) ){
                int flag = data.getIntExtra(EXTRA_HSCRAUTO_FLAG, 0);
                switch ( flag ){
                    case FLAG_CHANGE_PROGRESS:
                        mPro.setMessage("正在将网络切换到"+data.getStringExtra(EXTRA_TARGET_SSID));
                        mPro.show();
                        break;
                    case FLAG_DRS_IMPORT:
                        Log.d(TAG," import data to DRS ");
                        Log.d(TAG," ");
                        mPro.hide();
                        setTitle(YWifi.getWifiSSID());
                        break;
                    case FLAG_DRS_EXPORT:
                        Log.d(TAG," download finish, change wlan..... ");
                        YWifi.connectWifi( YWifi.NETWORK_TYPE_WLAN, FLAG_INTENET_SERVER);
                        mPro.hide();
                        setTitle(YWifi.getWifiSSID());
                        break;
                    case FLAG_INTENET_SERVER:
                        Log.d(TAG," send data to server...... ");
                        Log.d(TAG," send data finish ");
                        Log.d(TAG," ");
                        mPro.hide();
                        setTitle(YWifi.getWifiSSID());
                        break;
                    case FLAG_INTENET_HOME:
                        Log.d(TAG," it is home ");
                        Log.d(TAG," ");
                        mPro.hide();
                        setTitle(YWifi.getWifiSSID());
                        break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
