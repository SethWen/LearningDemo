package com.djsz.rxjava2demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

/**
 * author: Shawn
 * time  : 2017/3/4 15:54
 */

public class FilteringOperatorActivity extends AppCompatActivity {

    private static final String TAG = "FilteringOperatorActivi";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtering_operator);

        findViewById(R.id.btn_filter).setOnClickListener(v -> testFilterOperator());
        findViewById(R.id.btn_sample).setOnClickListener(v -> testSampleOperator());

    }

    /**
     * Sample 操作符
     */
    private void testSampleOperator() {
        Observable
                .range(1, 100000)
                .sample(1, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.i(TAG, "onNext: " + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });
    }

    /**
     * Filter 操作符 急需要传入一段过滤规则，该过滤规则封装在 Predicate 中
     */
    private void testFilterOperator() {
        Observable
                .range(1, 20)
                //                .filter(integer -> integer % 2 == 0)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        // 过滤规则，返回true表示留下，返回false表示剔除
                        return true;
                    }
                })
                .doOnNext(integer -> Log.i(TAG, "testFilterOperator: " + integer))
                .subscribe();
    }
}
