package com.djsz.glidedemo;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * author: Shawn
 * time  : 2017/3/15 10:38
 * desc  :
 */
public class MyGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        // 1. 获取默认的内存
//        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
//        int defaultMemoryCacheSize = calculator.getMemoryCacheSize(); // 获得默认内存缓存大小
//        int defaultBitmapPoolSize = calculator.getBitmapPoolSize(); // 获得bitmap缓存池大小
//        Log.i(TAG, "onCreate: defaultMemoryCacheSize = " + defaultMemoryCacheSize);

        // 2. 设置BitmapPool缓存大小
        int maxMemory = (int) Runtime.getRuntime().maxMemory();// 获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 8;// 设置图片内存缓存占用八分之一//设置内存缓存大小
        builder.setBitmapPool(new LruBitmapPool(500000));//设置BitmapPool缓存内存大小
        // 3. 设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));

        // 4. 设置Glide磁盘缓存大小
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "GlideCache", 10 << 20)); //10M
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "GlideCache", 10 << 20));
        // 5. 设置图片解码格式，默认是ARGB_565
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
