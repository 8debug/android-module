package com.ybase.webview;

import android.app.ProgressDialog;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import static android.app.ProgressDialog.STYLE_HORIZONTAL;

/**
 * Created by yhr on 2017/1/6.
 *
 */

public class BaseWebChromeClient extends WebChromeClient {

    private ProgressDialog mPro;

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if( mPro==null ){
            mPro = new ProgressDialog(view.getContext());
            mPro.setProgressStyle( STYLE_HORIZONTAL );
        }

        if( !mPro.isShowing() ){
            mPro.show();
        }

        if( newProgress==100 ){
            mPro.hide();
        }

        mPro.setProgress(newProgress);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
        result.confirm();
        return true;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage message) {
        MessageLevel level = message.messageLevel();
        switch (level){
            case LOG:
                Log.i("info_linenumber"+message.lineNumber(), message.message());
                break;
            case TIP:
                break;
            case DEBUG:
                Log.d("debug_linenumber"+message.lineNumber(), message.message());
                break;
            case WARNING:
                Log.w("warning_linenumber"+message.lineNumber(), message.message());
                break;
            case ERROR:
                Log.e("error_linenumber"+message.lineNumber(), message.message());
                break;
        }
        return true;
    }
}
