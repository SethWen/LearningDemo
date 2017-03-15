package com.djsz.glidedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * author: Shawn
 * time  : 2017/3/15 09:04
 * desc  :
 */
public class GlideActivity extends AppCompatActivity {

    private static final String TAG = "GlideActivity";

    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });

    }

    private void showImage() {
        Glide.with(this)
                .load("http://img-arch.pconline.com.cn/images/upload/upc/tx/wallpaper/1207/05/c0/12233333_1341470829710.jpg")
                .centerCrop()
                .error(R.drawable.b3)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.1f)
                .override(600, 300)
//                .bitmapTransform()    // 自定义各种形状
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(iv1);


//        Glide.with(this)
//                .load(R.drawable.b3)
//                .fitCenter()
//                .crossFade(5000)
//                .into(iv2);
//
//        Glide.with(this)
//                .load(R.drawable.b4)
//                .thumbnail(0.1f)
//                .crossFade(8000)
//                .into(iv3);
//
//        Glide.with(this)
//                .load(R.drawable.b1)
//                .asGif()
//                .into(iv4);
    }


}
