package com.djsz.rxjava2demo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author: Shawn
 * time  : 2017/3/3 13:30
 */

public class CreatingOperatorActivity extends AppCompatActivity {

    private static final String TAG = "CreatingOperatorActivit";
    private Button btnRange;
    CompositeDisposable disList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_operator);

        disList = new CompositeDisposable();

        findViewById(R.id.btn_from).setOnClickListener(v -> testFromOperator());
        findViewById(R.id.btn_just).setOnClickListener(v -> testJustOperator());
        findViewById(R.id.btn_interval).setOnClickListener(v -> testIntervalOperator());
        findViewById(R.id.btn_defer).setOnClickListener(v -> testDeferOperator());
        findViewById(R.id.btn_timer).setOnClickListener(v -> testTimerOperator());

        btnRange = (Button) findViewById(R.id.btn_range);
        btnRange.setOnClickListener(v -> testRangeOperator());


    }

    /**
     * Timer 操作符 至起一个延时的功能
     */
    private void testTimerOperator() {
        Observable
                .timer(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.i(TAG, "onNext: " + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * Defer 操作符
     */
    private void testDeferOperator() {
        Observable
                .defer(new Callable<ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> call() throws Exception {
                        return Observable.range(0, 20);
                    }
                })
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Object value) {
                        Log.i(TAG, "onNext: " + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * Interval 操作符
     * 将接收事件间隔开
     */
    private void testIntervalOperator() {
        Observable
                .intervalRange(1, 20, 3, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                        disList.add(d);
                    }

                    @Override
                    public void onNext(Long value) {
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
     * Range 操作符
     */
    private void testRangeOperator() {
        //        Observable
        //                .range(1, 20)
        //                .filter(integer -> integer % 2 == 0)
        //                .doOnNext(integer -> Log.i(TAG, "testRangeOperator: " + integer))
        //                .subscribe();

        // 60s 倒计时的实现
        Observable
                .intervalRange(1, 60, 0, 1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(i -> {
                    Log.i(TAG, "testRangeOperator: " + i);
                    btnRange.setText(String.valueOf(60 - i));
                })
                .doOnComplete(() -> {
                    btnRange.setText("Complete");
                })
                .subscribe();
    }

    /**
     * Just 操作符
     */
    private void testJustOperator() {
        Observable
                .just(1, 3, 5)
                .doOnNext(integer -> Log.i(TAG, "testJustOperator: " + integer))
                .doOnComplete(() -> Log.i(TAG, "testJustOperator: "))
                .subscribe();
    }

    /**
     * From 操作符
     */
    private void testFromOperator() {
        List<Integer> list = Arrays.asList(1, 3, 5);

        Observable
                .fromIterable(list)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        for (int i = 0; i < 10; i++) {
                            Log.i(TAG, "onSubscribe: " + i);
                            SystemClock.sleep(2000);
                        }
                        //                        d.dispose();
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.i(TAG, "onNext: " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注意，当 Activivty 销毁时，RxJava 未处理的事件依然会接着处理，所以，要通过 Dispose 关闭所有事件的接收
        disList.clear();
    }
}
