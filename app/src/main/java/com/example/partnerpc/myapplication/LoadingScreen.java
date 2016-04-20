package com.example.partnerpc.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

public class LoadingScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Timer timer = new Timer(true);
        timer.schedule(new timerTask(), 2000, 1000);
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
