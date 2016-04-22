package com.example.partnerpc.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView webview;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        progressBar = ProgressDialog.show(MainActivity.this, getResources().getString(R.string.loading), "請稍後");

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Fab clicked", Toast.LENGTH_LONG).show();
            }
        });

        webview = (WebView) findViewById (R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
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
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(webview.canGoBack()){
                webview.goBack();
            } else {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("結束應用程式")
                        .setMessage("確定要結束嗎?")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {}
                                }).show();
            }
            event.startTracking();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}