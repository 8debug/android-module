package com.ybase.webview;

import android.app.ProgressDialog;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by yhr on 2017/1/5.
 *
 */

public class BaseJsInterface {

    protected Context mContext;

    protected ProgressDialog mPro;

    public BaseJsInterface( Context context ){
        mContext = context;
    }

    @JavascriptInterface
    public void toast( String msg ) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void showProgress( String msg ) {
        if( mPro==null ){
            mPro = new ProgressDialog(mContext);
        }
        mPro.show();
    }

    @JavascriptInterface
    public void hideProgress() {
        if( mPro!=null ){
            mPro.hide();
        }
    }

}
