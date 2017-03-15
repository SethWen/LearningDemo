package com.djsz.rxjava2demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author: Shawn
 * time  : 2017/3/1 15:20
 */

public class BasicRxJavaActivity extends AppCompatActivity {
    private static final String TAG = "BasicRxJavaActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_rxjava);

        testBasicRxJava();
    }

    private void testBasicRxJava() {
        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.i(TAG, "subscribe: " + Thread.currentThread().getName());
                emitter.onNext("AAA");
                emitter.onNext("BBB");
                emitter.onNext("CCC");
                emitter.onComplete();
                //                emitter.onError(new Throwable("WTF"));
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe: ");
                d.dispose();
                CompositeDisposable cd = new CompositeDisposable();
                cd.add(d);
                cd.clear();
            }

            @Override
            public void onNext(String value) {
                Log.i(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        };

        Consumer<String> onNextConsumer = s -> Log.i(TAG, "accept: " + s + "-" + Thread.currentThread().getName());
        Consumer<Throwable> onErrorConsumer = throwable -> Log.e(TAG, "onCreate: ", throwable);

        //        stringObservable.subscribe(observer);
        //        stringObservable
        //                .subscribeOn(Schedulers.io())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .subscribe(onNextConsumer, onErrorConsumer);

        // 注意：如果子线程做网络请求，主线程更新UI，那么，需要在Activity.onDestroy()中调用d.dispose()方法,如果有多个
        // Disposable，那么使用CompositeDisposable.add(), 然后调用CompositeDisposable.clear()切断所有事件接收
        stringObservable
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.newThread())
                .doOnNext(onNextConsumer)
                .observeOn(Schedulers.io())
                .doOnNext(onNextConsumer)
                .subscribe();
//                .doOnError(onErrorConsumer);
    }
}
