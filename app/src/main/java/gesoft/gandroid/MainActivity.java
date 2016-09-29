package gesoft.gandroid;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gesoft.gandroid.download.service.DownloadService;
import gesoft.gapp.common.GAct;
import gesoft.gapp.common.GApp;
import gesoft.gapp.common.GFile;
import gesoft.gapp.common.GImage;
import gesoft.gapp.common.GPhone;
import gesoft.gapp.common.L;
import gesoft.gapp.common.T;
import gesoft.gcrashemail.bean.GEmail;
import gesoft.gcrashemail.crash.GCrashHandler;
import gesoft.gphotoview.GPhotoView;
import gesoft.gshare.GShareSDK;
import gesoft.push.GPushConstant;
import gesoft.push.GPushXG;

public class MainActivity extends AppCompatActivity {

    Context mContext;

    ProgressDialog mPro;

    MsgReceiver updateListViewReceiver;

    @Override
    public void onBackPressed() {
        GAct.exit(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        T.setContext(this);
        mPro = new ProgressDialog(this);
        mContext = this;
        gpush();
    }

    public void baseui(View view){
        startActivity(new Intent(this, BaseUIActivity.class) );
    }

    public void download(View view){
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(DownloadService.PARAM_URL, "http://218.61.254.30:8082/glcsgis_APPDownload/apk/UnicomZc.apk");
        startService(intent );
    }

    public void photopicker(View view){
        startActivity(new Intent(this, PhotoActivity.class) );
    }

    public void asyncTask(View view){
        startActivity(new Intent(this, AsyncTaskActivity.class));
    }

    public void retrofit(View view){
        startActivity( new Intent(this, RetrofitActivity.class));
    }

    public void fresco(View view){
        startActivity(new Intent(this, FrescoActivity.class));
    }

    public void photoview(View view){

        List<String> listUrl = new ArrayList<>();

        listUrl.add("http://ww1.sinaimg.cn/mw600/b4ece975tw1e33ep5rqfmj.jpg");
        listUrl.add("http://img.eeyy.com/uploadfile/2015/1215/20151215091108369.jpg");
        listUrl.add("http://tupian.enterdesk.com/2013/lxy/07/27/6/1.jpg");
        listUrl.add("http://dl.bizhi.sogou.com/images/2012/04/16/113574.jpg");

        GPhotoView.startImagePagerActivity(this, listUrl, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public void bdlocation(View view){
        startActivity(new Intent(this, BLocationActivity.class));
    }

    public void checkupd(View view){
        T.show(this, "正在开发中");
    }

    public void hotfix(View view){
        startActivity(new Intent(this, HotFixActivity.class));
        //GFIR.checkUpd();
    }

    public void recyclerView(View view){
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    public void phoneNumber(View view){
        T.show( this, GPhone.getPhoneNumber(this) );
    }

    public void phoneScreen( View view ){
        DisplayMetrics display = GPhone.getMetric(this);
        T.show("width="+display.widthPixels+", height="+display.heightPixels);
    }

    public void countTimer( View view ){
        startActivity(new Intent(this, CountTimerActivity.class));
    }

    public void webview( View view ){
        startActivity(new Intent(this, WebviewActivity.class));
    }

    public void sendEmail( View view ){

        //搜集崩溃信息
        GEmail email = new GEmail();
        email.setUserName("y.h.r@163.com");
        email.setUserPwd("13050920586");
        email.setToAddress("y.h.r@163.com");
        email.setNick( GApp.getAppName(mContext)+"_"+GApp.getVersionName(mContext) );
        email.setSubject( GPhone.getPhoneName()+", "+GPhone.getPhoneNumber(mContext) );
        GCrashHandler crashHandler = GCrashHandler.getInstance();
        crashHandler.setGEMail(email);
        crashHandler.init(mContext);

        Intent intent = null;
        intent.putExtra("asd",  true);
    }

    public void customview( View view ){
        startActivity(new Intent(this, CustomViewActivity.class));
    }

    public void share( View view ){
        File file = GFile.createFile(this, GApp.getAppName(this)+".jpg");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        GImage.storeImage(bitmap, file.getAbsolutePath());
        GShareSDK.share(this,
                "格微软件",
                "http://www.ge-soft.com/",
                "这是一条内容",
                file.getAbsolutePath(),
                "https://wx.qq.com/",
                "这是一条评论",
                "分享此内容的网站名称",
                "http://www.163.com");
    }

    public void sql( View view ){
        startActivity(new Intent(this, SQLiteActivity.class));
    }

    //推送
    void gpush(){

        GPushXG.registerPush(getApplicationContext(), new GPushXG.Reg() {
            @Override
            public void onSuccess(Object token) {
                L.d(token);
                Toast.makeText(mContext, token.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // 0.注册数据更新监听器
        updateListViewReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GPushConstant.XG_NOTIFACTION_SHOW_RECEIVER);
        registerReceiver(updateListViewReceiver, intentFilter);
    }

    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("标题");
            builder.setMessage("内容");
            builder.create().show();
        }
    }

}
