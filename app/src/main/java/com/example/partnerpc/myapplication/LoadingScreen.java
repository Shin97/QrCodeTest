package com.example.partnerpc.myapplication;

/**
 * Created by PartnerPC on 2016/4/19.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000; //開啟畫面時間(2秒)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        setContentView(R.layout.activity_loading);

        Timer timer = new Timer(true);
        timer.schedule(new timerTask(), 3000, 1000);
        /*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LoadingScreen.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);*/
    }

    public class timerTask extends TimerTask {
        public void run()
        {
            Intent it = new Intent();
            it.setClass(LoadingScreen.this, MainActivity.class);
            startActivity(it);
            this.cancel();
            LoadingScreen.this.finish();
        }
    }

}
