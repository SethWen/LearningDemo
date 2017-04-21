package com.djsz.rxjava2demo.bus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.djsz.rxjava2demo.R;

/**
 * author: Shawn
 * time  : 2017/4/14 10:57
 * desc  :
 */
public class RxBus2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus_2);

        findViewById(R.id.btn2).setOnClickListener(v -> RxBus2.getDefault().send(new EventManager.ChangeTextEvent("我是李达康")));

    }
}
