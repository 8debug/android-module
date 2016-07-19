package gesoft.gandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        String url = "http://10.70.23.32/demo/apk/apkpatch-1.0.3/output1/fix-8dc39a0c7844f130276374df7e305190.apatch";

        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(DownloadService.PARAM_URL, url);
        startService(intent);

        tv.setText("hello world");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
