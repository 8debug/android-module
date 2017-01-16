package com.ybase.webview;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by yhr on 2017/1/6.
 *
 */

public class BaseWebViewClient extends WebViewClient {

    ProgressDialog mPro;

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        mPro = new ProgressDialog(view.getContext());
        mPro.setMessage("正在加载网页...");
        mPro.show();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mPro.hide();
    }

}
