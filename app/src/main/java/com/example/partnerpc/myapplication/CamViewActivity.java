package com.example.partnerpc.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;


public class CamViewActivity extends AppCompatActivity {

    private ScannerLiveView camera;
    private boolean flashStatus;

    private int QrRequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_view);

        camera = (ScannerLiveView) findViewById(R.id.camview);
        camera.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener()
        {
            @Override
            public void onScannerStarted(ScannerLiveView scanner)
            {
                //Toast.makeText(CamViewActivity.this,"Scanner Started",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner)
            {
                //Toast.makeText(CamViewActivity.this,"Scanner Stopped",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerError(Throwable err)
            {
                Toast.makeText(CamViewActivity.this,"Scanner Error: " + err.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeScanned(String data)
            {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("code",data);
                intent.putExtras(bundle);
                setResult(QrRequestCode, intent); //requestCode需跟A.class的一樣
                camera.stopScanner();
                CamViewActivity.this.finish();
            }
        });
        findViewById(R.id.btnFlash).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toggleFlash();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        decoder.setScanAreaPercent(0.5);
        camera.setDecoder(decoder);
        camera.startScanner();
    }

    @Override
    protected void onPause()
    {
        camera.stopScanner();
        super.onPause();
    }

    public void toggleFlash()
    {
        flashStatus = !flashStatus;
        camera.getCamera().getController().switchFlashlight(flashStatus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return true;
    }

}
