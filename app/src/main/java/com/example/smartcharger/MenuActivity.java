package com.example.smartcharger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MenuActivity extends AppCompatActivity {
    public static MenuActivity instance;

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
    }
}
