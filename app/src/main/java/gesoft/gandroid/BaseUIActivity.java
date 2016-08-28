package gesoft.gandroid;

import android.app.Activity;
import android.os.Bundle;

public class BaseUIActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_ui);
    }
}
