package gesoft.gandroid.download.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.Date;

import gesoft.gapp.common.L;
import gesoft.gapp.common.T;
import gesoft.ghotfix.GHotFix;

public class DownloadService extends Service {
    private DownloadManager dm;
    private long enqueue;
    private BroadcastReceiver receiver;
    private Intent data;
    public final static String PARAM_URL = "param_url";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent;
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try{
                    /**下载完成后自动打开下载文件 start**/
                    if( false ){
                        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/"+Environment.DIRECTORY_DOWNLOADS+"/myApp.apk"));
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setDataAndType( uri, "application/vnd.android.package-archive");
                        startActivity(intent);
                        stopSelf();
                    }
                    /**下载完成后自动打开下载文件 end**/

                    /**通过本地库查找下载完成的文件路径 start**/
                    if( true ){
                        //获取下载完成的下载文件ID
                        //long downloadId = intent.getLongExtra( DownloadManager.EXTRA_DOWNLOAD_ID, 0 );
                        DownloadManager.Query query = new DownloadManager.Query();
                        query.setFilterById(enqueue);
                        Cursor cursor = dm.query(query);
                        if( cursor.moveToFirst() ){
                            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                            if( DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex) ){
                                T.show(context, "补丁下载完毕");
                                String strPath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                                GHotFix.addPatch( Uri.parse(strPath).getPath() );
                            }
                        }
                    }
                    /**通过本地库查找下载完成的文件路径 end**/
                }catch (Exception e){
                    L.e(e);
                }
            }
        };

        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        startDownload();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void startDownload() {
        //String url = "http://218.61.254.30:8082/glcsgis_APPDownload/apk/UnicomZc.apk";
        Uri uri = Uri.parse( data.getStringExtra(PARAM_URL) );
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request( uri );
        request.setAllowedNetworkTypes( DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                //移动网络情况下是否允许漫游。
                .setAllowedOverRoaming(false)
                .setTitle("更新") // 用于信息查看
                .setDescription("下载apk"); // 用于信息查看
        //利用此属性下载后使用响应程序打开下载文件
        //request.setMimeType("application/vnd.android.package-archive");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, new Date().getTime()+".apatch");
        enqueue = dm.enqueue(request);
    }
}