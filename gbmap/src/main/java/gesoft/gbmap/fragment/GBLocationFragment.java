package gesoft.gbmap.fragment;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import gesoft.gbmap.GBLocation;
import gesoft.gbmap.L;

/**
 * by yhr 在fragment中封装百度定位
 * A simple {@link Fragment} subclass.
 */
public abstract class GBLocationFragment extends Fragment implements GBLocation.IGBLocation {

    protected Context mContext;
    protected Activity mActivity;
    private GBLocation mGBLocation;
    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;

    @Override
    public void onStart() {
        try {
            super.onStart();
            onStartG();
        } catch (Exception e) {
            L.e(e);
        }
    }

    protected abstract void onStartG();
    protected abstract void onStopG();
    protected abstract View onCreateViewG(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 实现IGBLocation接口
     * @param igbLocation
     */
    void setGBLocation(GBLocation.IGBLocation igbLocation){
        if( igbLocation!=null )mGBLocation.setIGBLocation(igbLocation);
        else{
            throw new IllegalArgumentException(" GBLocation.IGBLocation is not null ");
        }
    }

    /**
     * 开始定位
     */
    protected void startLocation(){
        mGBLocation.start();
    }

    /**
     * 结束定位
     */
    protected void stopLocation(){
        mGBLocation.stop();
    }

    @Override
    public void onDestroyView() {
        mGBLocation.destoryLocation();
        super.onDestroyView();
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

    public GBLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        mContext = mActivity;
        getPersimmions();
        GBLocation.setApplication(mContext);
        mGBLocation = new GBLocation();
        mGBLocation.initLocation();
        setGBLocation(this);
        return onCreateViewG( inflater, container, savedInstanceState);
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if(mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(mContext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
			/*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (mContext.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }

        }else{
            return true;
        }
    }

}