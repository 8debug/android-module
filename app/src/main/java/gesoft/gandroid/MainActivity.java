package gesoft.gandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import gesoft.gandroid.download.service.DownloadService;
import gesoft.gphotoview.GPhotoView;

public class MainActivity extends AppCompatActivity {

    ProgressDialog mPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPro = new ProgressDialog(this);


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

}
