package gesoft.gbmap.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gesoft.gbmap.GBLocation;
import gesoft.gbmap.L;
import gesoft.gbmap.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class GBLocationFragment extends Fragment {

    protected Context mContext;
    protected Activity mActivity;
    private GBLocation mGBLocation;
    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;


    public GBLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onStart() {
        try {
            super.onStart();
            onStartG();
        } catch (Exception e) {
            L.e(e);
        }
    }

    @Override
    public void onStop() {
        try {
            onStopG();
        } catch (Exception e) {
            L.e(e);
        }
        super.onStop();
    }

    protected abstract void onStartG();
    protected abstract void onStopG();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = getActivity();
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

}
