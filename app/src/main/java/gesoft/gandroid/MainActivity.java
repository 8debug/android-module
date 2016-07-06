package gesoft.gandroid;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gesoft.gandroid.download.service.DownloadService;
import gesoft.gapp.common.L;
import gesoft.gphotoview.GPhotoView;
import gesoft.gupd.GFIR;
import gesoft.push.GPushConstant;
import gesoft.push.GPushXG;

public class MainActivity extends AppCompatActivity {

    Context mContext;

    ProgressDialog mPro;

    MsgReceiver updateListViewReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPro = new ProgressDialog(this);
        mContext = this;
        gpush();
    }

    public void download(View view){
        startService( new Intent(this, DownloadService.class));
    }

    public void retrofit(View view){
        startActivity( new Intent(this, RetrofitActivity.class));
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
        startActivity(new Intent(this, BLocationLocationActivity.class));
    }

    public void checkupd(View view){
        GFIR.checkUpd();
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
