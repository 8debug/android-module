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
    Button btn;

    @Override
    protected int getThemeId() {
        return R.style.AppTheme;
    }

    @Override
    protected void onCreateG(Bundle savedInstanceState) {
        setContentView(R.layout.activity_blocation);
    }

    public void location(final View view) {
        startLocation();
    }

    public void openFragment( View view ){
        FragmentManager mgr = getFragmentManager();
        FragmentTransaction tr = mgr.beginTransaction();
        tr.add(R.id.fragment, new BLocationFragment(), BLocationFragment.TAG).commit();
    }

    @Override
    public void onLocationStart() {
        btn.setText(getResources().getString(R.string.btn_bd_start));
    }

    @Override
    public void onLocationFinish(boolean isSuccess, GBLocation.GLBean bean) {
        stopLocation();
        btn.setText(getResources().getString(R.string.btn_bd_stop));
        if (isSuccess) {
            TextView tv = (TextView) findViewById(R.id.tv_result);
            tv.setText(bean.getDiscribe());
        }
        T.show(mContext, bean.getMsg() + " " + bean.getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
