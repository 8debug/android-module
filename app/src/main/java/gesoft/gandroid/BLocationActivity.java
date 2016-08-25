package gesoft.gandroid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gesoft.gapp.common.T;
import gesoft.gbmap.GBLocation;
import gesoft.gbmap.activity.GBLocationActivity;

public class BLocationActivity extends GBLocationActivity {


    @Bind(R.id.btn_location)
    Button btnLocation;

    @Override
    protected void onCreateG(Bundle savedInstanceState) {
        setContentView(R.layout.activity_blocation);
        setLocationDevice(true);
        ButterKnife.bind(this);

    }

    public void location(final View view) {
        T.show("开始GPS定位");
        startLocationGPS();
    }

    public void openFragment(View view) {
        FragmentManager mgr = getFragmentManager();
        FragmentTransaction tr = mgr.beginTransaction();
        tr.add(R.id.layout_content, new BLocationFragment(), BLocationFragment.TAG).commit();
    }

    @Override
    public void onLocationStart() {
        btnLocation.setText(getResources().getString(R.string.btn_bd_start));
    }

    @Override
    public void onLocationFinish(boolean isSuccess, GBLocation.GLBean bean) {
        stopLocation();
        btnLocation.setText(getResources().getString(R.string.btn_bd_start));
        if (isSuccess) {
            TextView tv = (TextView) findViewById(R.id.tv_result);
            tv.setText(bean.getDiscribe()+"\n"+bean.getMsg());
        }
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }*/

}
