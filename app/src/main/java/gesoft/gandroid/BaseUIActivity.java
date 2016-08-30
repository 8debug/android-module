package gesoft.gandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import gesoft.gapp.common.GView;

public class BaseUIActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.rg_demo1)
    RadioGroup rgDemo1;
    @Bind(R.id.rg_demo2)
    RadioGroup rgDemo2;
    @Bind(R.id.rbtn_1)
    RadioButton rbtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_ui);
        ButterKnife.bind(this);
        rgDemo1.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        GView.setEnabledRadioGroup( rgDemo2,checkedId==R.id.rbtn_1 );
        GView.setEnabledRadioGroup( rgDemo2,checkedId!=R.id.rbtn_1 );
    }
}
