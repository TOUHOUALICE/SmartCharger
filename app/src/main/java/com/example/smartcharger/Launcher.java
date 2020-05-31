package com.example.smartcharger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Launcher extends AppCompatActivity {
    public static Launcher instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        // 隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.AppTheme);//恢复原有的样式
        setContentView(R.layout.activity_launcher);

        //获取本地缓存并判断操作
        SharedPreferences sp = getSharedPreferences("login", getApplicationContext().MODE_PRIVATE);
        sp.edit()
                .putString("phone","17638591897")
                .putBoolean("isAgree",true)
                .apply();
        String phone = sp.getString("phone", null);
        Boolean isAgree = sp.getBoolean("isAgree",false);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!isAgree){
            Intent intent = new Intent(Launcher.this, AuthorizeDialog.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
        else if (phone != null){
            MyApplication application = (MyApplication) getApplicationContext();
            application.setPhone(phone);
            application.setIsAgree(isAgree);
            Intent intent = new Intent(Launcher.this, MenuActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
        else{
            MyApplication application = (MyApplication) getApplicationContext();
            application.setIsAgree(isAgree);
            Intent intent = new Intent(Launcher.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    }
}
