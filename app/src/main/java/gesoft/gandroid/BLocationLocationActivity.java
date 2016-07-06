package gesoft.gandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import gesoft.gapp.common.T;
import gesoft.gbmap.GBLocation;
import gesoft.gbmap.activity.GBLocationActivity;

public class BLocationLocationActivity extends GBLocationActivity {

    @Override
    protected void onStartG(){

    }

    @Override
    protected void onStopG(){

    }

    @Override
    protected void onCreateG(Bundle savedInstanceState) {
        setContentView(R.layout.activity_blocation);
    }

    public void location(final View view){
        final Button btn = (Button)view;
        mGBLocation.setIGBLocation(new GBLocation.IGBLocation() {
            @Override
            public void onLocationStart() {
                btn.setText(getResources().getString(R.string.btn_bd_start));
            }

            @Override
            public void onLocationFinish(boolean isSuccess, GBLocation.GLBean bean) {
                mGBLocation.stop();
                btn.setText(getResources().getString(R.string.btn_bd_stop));
                if( isSuccess ){
                    TextView tv = (TextView)findViewById(R.id.tv_result);
                    tv.setText(bean.getDiscribe());
                }
                T.show(mContext, bean.getMsg()+" "+bean.getTime());
            }
        });

        mGBLocation.start();
    }
}
