package com.shawn.videocachedemo;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.danikula.videocache.HttpProxyCacheServer;

import java.io.File;

/**
 * author: Shawn
 * time  : 2016/12/13 13:59
 */

public class App extends Application {

    public HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        App app = (App) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
//                return new HttpProxyCacheServer(this);
        File cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        return new HttpProxyCacheServer.Builder(this)
                .cacheDirectory(cacheDir)
                .maxCacheSize(1024 * 1024 * 1024)
                .fileNameGenerator(new Md5MediaNameGenerator())
                .build();
    }
}
