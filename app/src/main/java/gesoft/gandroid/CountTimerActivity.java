package gesoft.gandroid;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CountTimerActivity extends Activity {

    @Bind(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_timer);
        ButterKnife.bind(this);

        //倒计时
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                btn.setClickable(false);
                btn.setText((l / 1000) + "秒后重发");
            }

            @Override
            public void onFinish() {
                btn.setClickable(true);
                btn.setText("倒计时完毕");
                btn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        }.start();

    }


}
