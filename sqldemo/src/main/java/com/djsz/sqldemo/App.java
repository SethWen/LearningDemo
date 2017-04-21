package com.djsz.sqldemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * author: Shawn
 * time  : 2017/4/6 15:22
 * desc  :
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
