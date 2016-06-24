package gesoft.gandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gesoft.gandroid.download.service.DownloadService;

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

}
