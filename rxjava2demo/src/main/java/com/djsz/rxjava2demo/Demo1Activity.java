package com.djsz.rxjava2demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observable;

/**
 * author: Shawn
 * time  : 2017/3/4 14:35
 */

public class Demo1Activity extends AppCompatActivity {

    private static final String TAG = "Demo1Activity";

    private TextView tvDemo1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        tvDemo1 = (TextView) findViewById(R.id.tv_demo1);

        tvDemo1.setOnClickListener(v -> start());
    }

    /**
     * 开始倒计时
     */
    private void start() {
        Observable<Integer> observable = Observable.create(e -> {
            e.onNext(60);
        });

        observable.doOnNext(integer -> {
            Log.i(TAG, "start: " + integer);
        });
    }
}
