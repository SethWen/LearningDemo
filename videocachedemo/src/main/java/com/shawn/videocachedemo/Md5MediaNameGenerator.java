package com.shawn.videocachedemo;

import android.util.Log;

import com.danikula.videocache.ProxyCacheUtils;
import com.danikula.videocache.file.FileNameGenerator;

/**
 * author: Shawn
 * time  : 2016/12/21 10:38
 */

public class Md5MediaNameGenerator implements FileNameGenerator {

    @Override
    public String generate(String url) {
        Log.e("aaaa", "generate: " + ProxyCacheUtils.computeMD5(url));
        return ProxyCacheUtils.computeMD5(url);
    }
}
