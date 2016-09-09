package gesoft.gandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gesoft.gandroid.download.service.DownloadService;

public class HotFixActivity extends AppCompatActivity {

    @Bind(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_fix);
        ButterKnife.bind(this);

        hotFixText();
    }

    void hotFixText(){
        tv.setText("修改文字生效");
    }

    public void startHotFix( View view ){
        String url = "http://10.70.23.32/demo/apkpatch/new-78c762dcf8a9610be99318bf8e071d9c.apatch";
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(DownloadService.PARAM_URL, url);
        startService(intent);

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, DownloadService.class));
        super.onDestroy();

    }
}
