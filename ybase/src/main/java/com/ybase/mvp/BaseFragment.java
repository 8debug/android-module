package com.ybase.mvp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by yhr on 2016/11/17.
 * 大部分Fragment都基础此类
 */
public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;
    protected Context mContext;
    protected ProgressDialog  mPro;

    /**
     * 获取默认Tag
     * @return
     */
    public String getDefaultTag(){
        return getClass().getName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPro = new ProgressDialog(mContext);
        setHasOptionsMenu(true);
    }

    protected void setTitle( CharSequence title ){
        getActivity().setTitle( title );
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof Activity ){
            mActivity = (Activity) context;
            mContext = context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        mPro.dismiss();
        super.onDestroy();
        onDestroyFragment();
    }

    public abstract void onDestroyFragment();

    @Override
    public void onDestroyView() {
        mPro.dismiss();
        super.onDestroyView();
        onDestroyViewFragment();
    }

    public abstract void onDestroyViewFragment();
}
