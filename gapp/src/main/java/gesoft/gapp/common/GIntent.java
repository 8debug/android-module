package gesoft.gapp.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;

/**
 * Created by yhg on 2016/6/20.
 * 常用的系统intent
 */
public class GIntent {

    /**
     * 获取拍照的Intent
     * @param ctx
     * @return
     */
    public static Intent getIntentCamera(Context ctx, File fileOut){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(ctx.getPackageManager()) == null){
            Toast.makeText(ctx, "没找到摄像头", Toast.LENGTH_SHORT).show();
        }else{
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileOut));
        }
        return cameraIntent;
    }

    /**
     * 获取打电话的Intent，需要拨号权限
     * @param context
     * @param phoneNumber 电话号码
     */
    public static Intent getIntentCall(Context context, String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNumber));
        return intent;
    }

}
