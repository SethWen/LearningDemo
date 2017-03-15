package com.shawn.imgdisplaydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ImageView ivImageLoader;
    private ImageView ivPicasso;
    private ImageView ivGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivImageLoader = (ImageView) findViewById(R.id.iv_image_loader);
        ivPicasso = (ImageView) findViewById(R.id.iv_picasso);
        ivGlide = (ImageView) findViewById(R.id.iv_glide);


        ImageLoader.getInstance().displayImage(
                "https://i.yiyahanyu.com/user/2016/12/21/3e492129541204f2bf78fe4a4f9dfa34.jpg", ivImageLoader);

        Picasso.with(this).load(
                "https://i.yiyahanyu.com/user/2016/12/21/3e492129541204f2bf78fe4a4f9dfa34.jpg").into(ivPicasso);

        Glide.with(this).load(
                "https://i.yiyahanyu.com/user/2016/12/21/3e492129541204f2bf78fe4a4f9dfa34.jpg").into(ivGlide);

    }
}
