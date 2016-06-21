package gesoft.gapp.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2016/6/5.
 */
public class GAct {        

    /**
     * 添加或显示fragment
     * @param mgr
     * @param fragment
     * @param frameId
     * @param tag   要添加（显示）的fragment的tag
     * @param arrayTag  其他不显示的fragments的tags
     */
    public static void addFragmentToActivity (@NonNull FragmentManager mgr,
                                              @NonNull Fragment fragment,
                                              int frameId,
                                              String tag,
                                              String...arrayTag) {
        FragmentTransaction tr = mgr.beginTransaction();
        Fragment fragmentCurr = mgr.findFragmentByTag(tag);

        for (String t : arrayTag) {
            Fragment f = mgr.findFragmentByTag(t);
            if( f!=null ){
                tr.hide(f);
            }
        }

        if( fragmentCurr == null ){
            tr.add(frameId, fragment, tag);
        }else{
            tr.show(fragmentCurr);
        }
        tr.commit();
    }



    /**
     * 拍照
     * @param act
     * @param requestCode
     * @return
     */
    public static File startActivityCamera(Activity act, int requestCode){
        File mTmpFile = null;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(act.getPackageManager()) != null){
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            mTmpFile = GFile.createFile(act.getApplicationContext());
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            act.startActivityForResult(cameraIntent, requestCode);
        }else{
            Toast.makeText(act.getApplicationContext(), "没找到摄像头", Toast.LENGTH_SHORT).show();
        }
        return mTmpFile;
    }

    /**
     * 打电话，需要拨号权限
     * @param context
     * @param phoneNumber 电话号码
     */
    public static void startActivityCall(Context context, String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNumber));
        context.startActivity(intent);
    }

}
