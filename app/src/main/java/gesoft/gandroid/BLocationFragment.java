package gesoft.gandroid;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gesoft.gbmap.GBLocation;
import gesoft.gbmap.fragment.GBLocationFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class BLocationFragment extends GBLocationFragment {

    public final static String TAG = BLocationFragment.class.getName();


    @Bind(R.id.btn)
    Button btn;
    @Bind(R.id.tv)
    TextView tv;


    @Override
    protected View onCreateViewG(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blocation, container, false);
        ButterKnife.bind(this, view);
        setLocationDevice(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocation();
            }
        });

        return view;
    }

    public BLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onLocationStart() {
        btn.setText("正在综合定位...");
    }

    @Override
    public void onLocationFinish(boolean isSuccess, GBLocation.GLBean bean) {
        stopLocation();
        tv.setText(bean.getLat()+", "+bean.getLng()+" address="+bean.getDiscribe()+"  "+bean.getMsg());
        btn.setText("开始定位");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
