package com.example.partnerpc.myapplication;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    WebView webview;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        progressBar = ProgressDialog.show(MainActivity.this, "讀取中", "請稍後");

        webview = new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);
        setContentView(webview);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
        });
        webview.loadUrl("http://www.medfirst.com.tw/gift/index.php?forceDevice=mobile");
        //spinbar.setVisibility(View.GONE);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean close;
            if(webview.canGoBack()){
                close =false;
                webview.goBack();
            } else {
                close = true;
            }
            event.startTracking();
            return close;
        }
        return super.onKeyDown(keyCode, event);
    }

}