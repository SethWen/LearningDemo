package com.djsz.rxjava2demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableOperator;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * author: Shawn
 * time  : 2017/3/1 15:20
 */

public class TransformingOperatorActivity extends AppCompatActivity {
    private static final String TAG = "TransOperatorActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transforming_operator);

        findViewById(R.id.btn_map).setOnClickListener(v -> testMapOperator());
        findViewById(R.id.btn_flat_map).setOnClickListener(v -> testFlatMapOperator());
        findViewById(R.id.btn_concat_map).setOnClickListener(v -> testConcatMapOperator());
        findViewById(R.id.btn_compose).setOnClickListener(v -> testComposeOperator());
        findViewById(R.id.btn_lift).setOnClickListener(v -> testLiftOperator());

    }

    /**
     * Lift 操作符
     */
    private void testLiftOperator() {
        Observable
                .range(1, 20)
                .lift(new ObservableOperator<String, Integer>() {
                    @Override
                    public Observer<? super Integer> apply(Observer<? super String> observer) throws Exception {
                        return null;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        Log.i(TAG, "onNext: value = " + value);
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
     * Compose 操作符
     */
    private void testComposeOperator() {
        ObservableTransformer transformer = new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {

                return null;
            }
        };

        Observable
                .range(1,9)
                .flatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Integer integer) throws Exception {
                        return Observable.fromArray("");
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                });

        Observable
                .range(0, 10)
                .compose(new ObservableTransformer<Integer, String>() {
                    @Override
                    public ObservableSource<String> apply(Observable<Integer> upstream) {
                        // 将上游的 Observable 转换为一个 新的 Observable 输出 供下游处理
                        Observable<String> map = upstream.map(new Function<Integer, String>() {
                            @Override
                            public String apply(Integer integer) throws Exception {
                                return integer + "compose";
                            }
                        });
                        return map;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        Log.i(TAG, "onNext: value = " + value);
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
     * map 操作符使用。使用场景举例：如果是接口套接口，那么便可以使用 map 转换，其中的两个泛型是前后两个 model
     */
    private void testMapOperator() {
        Observable
                .create((ObservableOnSubscribe<Integer>) e -> {
                    e.onNext(1);
                    e.onNext(2);
                    e.onComplete();
                })
                .map(value -> "I am the result " + value)
                .doOnNext(s -> Log.e(TAG, "testMapOperator: " + s))
                .doOnComplete(() -> Log.e(TAG, "testMapOperator: "))
                .subscribe();
    }

    /**
     * 经 ConcatMap 转换过的事件流在流出时是有序的
     */
    private void testConcatMapOperator() {
        Observable
                .create(e -> {
                    e.onNext(1);
                    e.onNext(2);
                    e.onNext("ooo");
                })
                .concatMap(i -> {
                    //                    List<String> list = new ArrayList<>();
                    //                    for (int j = 0; j < 3; j++) {
                    //                        list.add("I am value " + i);
                    //                    }
                    //                    return Observable.fromIterable(list).delay(500, TimeUnit.MILLISECONDS);
                    return Observable.fromIterable(Arrays.asList("con-----" + i)).delay(500, TimeUnit.MILLISECONDS);
                })
                .doOnNext(s -> Log.e(TAG, "testConcatMapOperator: " + s))
                .subscribe();
    }

    /**
     * 经 FlatMap 转换过的事件流在流出时是无序的
     */
    private void testFlatMapOperator() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onNext(3);
                    }
                })
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add("I am value " + integer);
                        }
                        return Observable.fromIterable(list);
                    }
                })
                .doOnNext(str -> Log.e(TAG, "accept: " + str))
                .subscribe();
    }
}
