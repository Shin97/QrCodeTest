package com.example.partnerpc.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webview;
    private ProgressDialog progressBar;
    private FloatingActionButton fab;
    private int QrRequestCode = 1;
    private boolean QrBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        progressBar = ProgressDialog.show(MainActivity.this, getResources().getString(R.string.loading), "請稍後");
        QrBrowser = false;

        fab = (FloatingActionButton)findViewById(R.id.CamFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.open_scanner), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, CamViewActivity.class);
                startActivityForResult(intent, QrRequestCode);
            }
        });
        fab.hide();
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
                    fab.show();
                }
            }
        });
        webview.loadUrl(getResources().getString(R.string.URL));
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(webview.canGoBack()) {
                webview.goBack();
            } else if (QrBrowser) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("結束應用程式")
                        .setMessage("確定要結束嗎?")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
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

    Intent intent = getIntent();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == QrRequestCode){
            String result = data.getExtras().getString("code");
            Toast.makeText(MainActivity.this,"開啟網址:" + result, Toast.LENGTH_LONG).show();
            QrBrowser = true;

            WebView QRWebView = new WebView(this);
            QRWebView.getSettings().setJavaScriptEnabled(true);
            QRWebView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            QRWebView.loadUrl(result);
            setContentView(QRWebView);
        }
    }
}