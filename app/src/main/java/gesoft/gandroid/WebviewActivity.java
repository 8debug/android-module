package gesoft.gandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import static gesoft.gandroid.R.id.webview;

@SuppressLint("SetJavaScriptEnabled")
public class WebviewActivity extends Activity {

    final static String URL = "http://www.baidu.com";

    @Bind(webview)
    WebView mWebView;

    ProgressDialog mPro;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mContext = this;
        mPro = new ProgressDialog(mContext);
        ButterKnife.bind(this);

    }

    public void loadPdf( View view ){
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.requestFocus();
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.loadUrl("http://192.168.1.100:8888/web/viewer.html");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }

        });

        /*mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                System.out.println("=========>开始下载 url =" + url);
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });*/
    }

    public void loadWeb( View view ){
        mPro.setMessage("正在加载...");
        mPro.show();

        WebSettings webSettings = mWebView.getSettings();
        //支持js，webview与js交互
        webSettings.setJavaScriptEnabled(true);
        //支持html5的缓存localStorage
        webSettings.setDomStorageEnabled(true);

        mWebView.addJavascriptInterface(new MyJavaScript(), "yhr");

        mWebView.setWebChromeClient( new WebChromeClient() );

        mWebView.setWebViewClient( new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mPro.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mPro.hide();
                super.onPageFinished(view, url);
            }
        } );

        mWebView.loadUrl(URL);
        //android调用JavaScript方法
        mWebView.loadUrl("javascript:alert();");
    }



    class MyJavaScript{

        //js调用android中say方法，yhr.say();
        @JavascriptInterface
        public void say(){
            Toast.makeText(mContext, "HelloWorld", Toast.LENGTH_SHORT).show();
        }

    }
}
