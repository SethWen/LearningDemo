package com.shawn.rxretrodemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * author: Shawn
 * time  : 2016/12/5 15:05
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }
}
