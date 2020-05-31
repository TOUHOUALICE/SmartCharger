package com.example.smartcharger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MenuActivity extends AppCompatActivity {
    public static MenuActivity instance;
    private ImageView appoint;  //预约按钮
    private ImageView scan;     //扫码按钮
    private ImageView my;       //个人信息按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        if (Launcher.instance != null) {
            Launcher.instance.finish();
        }
        if (AuthorizeDialog.instance != null) {
            AuthorizeDialog.instance.finish();
        }
        if (MainActivity.instance != null) {
            MainActivity.instance.finish();
        }
        setContentView(R.layout.activity_menu);

        appoint = (ImageView) findViewById(R.id.appoint);
        scan = (ImageView) findViewById(R.id.scan);
        my = (ImageView) findViewById(R.id.my_information);

        appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,appointment.class);
                startActivity(intent);
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(MenuActivity.this).setCaptureActivity(ScanQRCodeActivity.class).initiateScan();
            }
        });
        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,MyInfo.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d(getClass().getName(), "Cancelled");
                Toast.makeText(this, "扫描结果为空", Toast.LENGTH_LONG).show();
            } else {
                Log.d(getClass().getName(), "Scanned: " + result.getContents());
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
