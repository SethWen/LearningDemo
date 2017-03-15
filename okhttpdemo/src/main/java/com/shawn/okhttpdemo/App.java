package com.shawn.okhttpdemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * author: Shawn
 * time  : 2016/12/15 11:41
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
