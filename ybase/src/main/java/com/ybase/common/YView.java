package com.ybase.common;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by yhr on 2017/1/16.
 *
 */

public class YView {

    static Toast toast;

    public static void showAlert( Context context, String title, String msg ){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(!TextUtils.isEmpty(title)){
            builder.setTitle(title);
        }
        builder.setMessage(msg);
        builder.setPositiveButton("确定", null);
        builder.show();
    }

    public static AlertDialog getAlert( Context context, String msg ){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton("确定", null);
        return builder.create();
    }

    public static void showAlert( Context context, String msg ){
        /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.show();*/
        showAlert(context, "", msg);
    }

    public static void showToast( Context context, String msg ){
        if( toast==null ){
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }

}
