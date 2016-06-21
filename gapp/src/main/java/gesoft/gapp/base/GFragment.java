package gesoft.gapp.base;

/**
 * Created by yhr on 2016/5/13.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;

public class GFragment extends Fragment {


    protected Context mContext;
    protected ProgressDialog mPro;
    protected Activity mActivity;

    @Override
    public void onStart() {
        super.onStart();
    }

    protected void onStartG(){

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mContext = mActivity;
        mPro = new ProgressDialog(mActivity);
        onStartG();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onDestroyG();
    }

    protected void onDestroyG(){
        if( mPro!=null ){
            mPro.dismiss();
        }
    }
}
