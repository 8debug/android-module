package tdotapp.com.xingedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import gesoft.gandroid.gpush.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*GPushXG.registerReceiver(this, new XGPushBaseReceiver() {
            @Override
            public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {

            }

            @Override
            public void onUnregisterResult(Context context, int i) {

            }

            @Override
            public void onSetTagResult(Context context, int i, String s) {

            }

            @Override
            public void onDeleteTagResult(Context context, int i, String s) {

            }

            @Override
            public void onTextMessage(Context context, XGPushTextMessage message) {
                //透传消息
                String strJson = message.getCustomContent();
            }

            @Override
            public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {

            }

            @Override
            public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {

            }
        });*/

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// 必须要调用这句
    }
}
