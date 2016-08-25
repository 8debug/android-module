package gesoft.gandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressLint("SetJavaScriptEnabled")
public class WebviewActivity extends Activity {

    final static String URL = "http://www.baidu.com";

    @Bind(R.id.webview)
    WebView webview;

    ProgressDialog mPro;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mContext = this;
        mPro = new ProgressDialog(mContext);
        ButterKnife.bind(this);

        initWeb();
    }


    void initWeb(){
        mPro.setMessage("正在加载...");
        mPro.show();

        WebSettings webSettings = webview.getSettings();
        //支持js，webview与js交互
        webSettings.setJavaScriptEnabled(true);
        //支持html5的缓存localStorage
        webSettings.setDomStorageEnabled(true);

        webview.addJavascriptInterface(new MyJavaScript(), "yhr");

        webview.setWebChromeClient( new WebChromeClient() );

        webview.setWebViewClient( new WebViewClient(){

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

        webview.loadUrl(URL);
        //android调用JavaScript方法
        webview.loadUrl("javascript:alert();");
    }



    class MyJavaScript{

        //js调用android中say方法，yhr.say();
        @JavascriptInterface
        public void say(){
            Toast.makeText(mContext, "HelloWorld", Toast.LENGTH_SHORT).show();
        }

    }
}
