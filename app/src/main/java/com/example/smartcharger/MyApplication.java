package com.example.smartcharger;

import android.app.Application;

public class MyApplication extends Application {
    private String phone;
    private String name;
    private Boolean isAgree = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsAgree(){
        return isAgree;
    }
    public void setIsAgree(Boolean isAgree){
        this.isAgree = isAgree;
    }
}
