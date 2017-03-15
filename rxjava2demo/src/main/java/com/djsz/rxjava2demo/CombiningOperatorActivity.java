package com.djsz.rxjava2demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * author: Shawn
 * time  : 2017/3/5 23:25
 */

public class CombiningOperatorActivity extends AppCompatActivity {

    private static final String TAG = "CombiningOperatorActivi";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combining_operator);

        findViewById(R.id.btn_zip).setOnClickListener(v -> testZipOperator());
        findViewById(R.id.btn_merge).setOnClickListener(v -> testMergeOperator());

    }

    private void testMergeOperator() {

    }

    /**
     * Zip 操作符会将两个 Observable 中发射的事件链严格按照顺序组合成新的事件。应场景，比如说一个界面需要拉两个接口数据共同配合
     * 显示，那么就可以使用 Zip
     */
    private void testZipOperator() {
        Observable
                .zip(
                        Observable.create((ObservableOnSubscribe<Integer>) e -> {
                            e.onNext(1);
                            e.onNext(2);
                            e.onNext(3);
                            e.onNext(4);
                            e.onComplete();
                        }).subscribeOn(Schedulers.io()),    // 参1

                        Observable.create((ObservableOnSubscribe<String>) e -> {
                            e.onNext("A");
                            e.onNext("B");
                            e.onNext("C");
                            e.onComplete();
                        }).subscribeOn(Schedulers.io()),    // 参2

                        (integer, s) -> integer + s         // 参3
                )
                .doOnNext(s -> Log.e(TAG, "doOnNext: " + s))
                .doOnComplete(() -> Log.e(TAG, "doOnComplete: "))
                .subscribe();
    }
}
