package com.example.partnerpc.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private WebView webview;
    private ProgressDialog progressBar;
    private FloatingActionMenu menu;
    private FloatingActionButton fab;
    private FloatingActionButton fab2;
    private int QrRequestCode = 1;
    private boolean QrBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
              progressBar = ProgressDialog.show(MainActivity.this, getResources().getString(R.string.loading), "請稍後");
                QrBrowser = false;
                     menu = (FloatingActionMenu) findViewById(R.id.menu);
                      fab = (FloatingActionButton)findViewById(R.id.CamFab);
                     fab2 = (FloatingActionButton)findViewById(R.id.menu_item2);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CamViewActivity.class);
                startActivityForResult(intent, QrRequestCode);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(coordinatorLayout, "無作用按鈕", Snackbar.LENGTH_LONG).show();
            }
        });

        menu.hideMenu(false);

        if(isConnected()){
            webview = (WebView) findViewById(R.id.webView);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                        menu.showMenu(true);
                    }
                }
            });
            webview.loadUrl(getResources().getString(R.string.URL));
        }else {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("無網路連線")
                    .setMessage("請檢察網路連線")
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(webview.canGoBack()) {
                webview.goBack();
            } else if (QrBrowser && !webview.canGoBack()) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == QrRequestCode){
            String result = data.getExtras().getString("code");
            QrBrowser = true;
            webview = new WebView(this);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            webview.loadUrl(result);
            setContentView(webview);
            Snackbar.make(webview,getString(R.string.open_url) + result,Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}