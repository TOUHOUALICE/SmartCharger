package com.example.smartcharger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;
import com.mob.OperationCallback;


public class AuthorizeDialog extends AppCompatActivity {
    public static AuthorizeDialog instance;
    private boolean granted;   //是否同意协议
    private TextView button_accept;
    private TextView button_reject;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        // 隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(Launcher.instance != null){
            Launcher.instance.finish();
        }
        setContentView(R.layout.activity_authorize_dialog);

        button_accept = findViewById(R.id.authorize_dialog_accept);
        button_reject = findViewById(R.id.authorize_dialog_reject);

        button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                granted = true;
                SharedPreferences sp = getSharedPreferences("login", getApplicationContext().MODE_PRIVATE);
                sp.edit()
                        .putBoolean("isAgree", granted)
                        .apply();
                submitPrivacyGrantResult(granted);
                intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        button_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                granted = false;
                submitPrivacyGrantResult(granted);
                finish();
            }
        });
    }

    private void submitPrivacyGrantResult(boolean granted) {
        MobSDK.submitPolicyGrantResult(granted, new OperationCallback<Void>() {
            @Override
            public void onComplete(Void data) {
                Log.d("TAG", "隐私协议授权结果提交：成功");
                Toast.makeText(AuthorizeDialog.this, "隐私协议授权结果提交：成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("TAG", "隐私协议授权结果提交：失败");
            }
        });
    }
}
