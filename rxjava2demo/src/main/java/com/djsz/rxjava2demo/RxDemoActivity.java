package com.djsz.rxjava2demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * author: Shawn
 * time  : 2017/3/4 14:31
 */

public class RxDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_demo);
        findViewById(R.id.btn_demo1).setOnClickListener(v -> startActivity(new Intent()));
    }
}
