package com.example.ywifi;

/**
 * Created by yhr on 2017/1/14.
 *
 */

public class YWifiConstant {

    //private static List<Long> mListTime = new ArrayList<>();

    //切换网络成功后要做的操作
    private static int mFlag = 0;

    public final static int INIT_FLAG = 0;

    public final static String INIT_SSID = "";

    //要切换的网络，以此作为判断切换后的网络是否符合要求
    private static String mTargetSSID;

    private static String mConnectedSSID;

    /*public static List<Long> getListTime(){
        return mListTime;
    }*/

    public static void setFlag( int flag ){
        mFlag = flag;
    }

    public static void setTargetSSID(String targetSSID ){
        mTargetSSID = targetSSID;
    }

    public static int getFlag( ){
        return mFlag;
    }

    public static String getTargetSSID( ){
        return mTargetSSID;
    }

    public static String getConnectedSSID(){
        return mConnectedSSID;
    }

    public static void setConnectedSSID( String ssid ){
        mConnectedSSID = ssid;
    }

    //根据此广播动作存储逻辑操作标识
    public final static String WIFI_CHANGE_RECEIVER_ACTION = "hescrauto.change.RECEIVER_ACTION";

    //切换wifi时的状态变化
    public final static String WIFI_CHANGE_STATE_ACTION = "WIFI_CHANGE_STATE_ACTION";

    public final static String EXTRA_TARGET_SSID = "EXTRA_WIFI_SSID";

    public final static String EXTRA_HSCRAUTO_FLAG = "EXTRA_HSCRAUTO_FLAG";

    public final static String EXTRA_WIFI_INFO = "EXTRA_WIFI_INFO";

    //要切换的目标网络是否为当前网络
    public final static String EXTRA_CHANGE_RESULT = "EXTRA_CHANGE_RESULT";

    //切换网络时的过程
    public final static int FLAG_CHANGE_PROGRESS = 5;

    //导入到DRS
    public final static int FLAG_DRS_IMPORT = 1;

    //从DRS导出
    public final static int FLAG_DRS_EXPORT = 2;

    //发送到服务器
    public final static int FLAG_INTENET_SERVER = 3;

    //返回首界面
    public final static int FLAG_INTENET_HOME = 4;

    //当前网络就是要切换的网络
    public final static int CHANGE_RESULT_OK = 1;
    //网络切换中
    public final static int CHANGE_RESULT_CONNECTING = 2;
    //网络切换失败
    public final static int CHANGE_RESULT_FAILED = 3;

    public static void init(){
        setTargetSSID(INIT_SSID);
        setFlag(INIT_FLAG);
        setConnectedSSID("");
        //mListTime.clear();
    }
}
