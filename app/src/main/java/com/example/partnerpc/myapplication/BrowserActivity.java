package com.example.partnerpc.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserActivity extends AppCompatActivity {

    private WebView webview;
    private ProgressDialog progressBar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        Intent intent = this.getIntent();
        String url = intent.getStringExtra("URL");

        progressBar = ProgressDialog.show(BrowserActivity.this, getString(R.string.loading), "請稍後");
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.BrowserCoordinatorLayout);

        webview = (WebView) findViewById(R.id.QRView);
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
                    Handler myHandler = new Handler();
                    myHandler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            progressBar.dismiss();
                        }
                    },2000);
                }
            }
        });
        Snackbar.make(coordinatorLayout, getString(R.string.open_url), Snackbar.LENGTH_LONG).show();
        webview.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(webview.canGoBack()) {
                webview.goBack();
            } else {
                Intent intent = new Intent(BrowserActivity.this,MainActivity.class);
                startActivity(intent);
                BrowserActivity.this.finish();
            }
            event.startTracking();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
