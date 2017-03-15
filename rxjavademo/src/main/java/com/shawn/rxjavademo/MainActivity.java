package com.shawn.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static rx.Observable.just;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_unregister)
    Button btnUnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFlatMapObservable();
            }
        });
    }

    /**
     * 初始化 Observable
     */
    private void initCreateObservable() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("哈哈");
                        subscriber.onNext("呵呵");
                        subscriber.onNext("嘿嘿");
                        subscriber.onCompleted();
                        //                        action1.call("哈哈");
                        //                        action1.call("嘿嘿");
                        //                        action1.call("呵呵");
                        Log.d(TAG, "call: " + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    /**
                     * 相较于在 Observer 多出来的方法，在 subscribe() 被调用时的线程调用。 可以用于做一些准备工作
                     */
                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.d(TAG, "onStart() called");
                        Log.d(TAG, "onStart: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext() called with: s = [" + s + "]");
                        Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                    }
                });

    }

    private void initJustObservable() {
        Observable
                .just("aaa", "bbb")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext() called with: s = [" + s + "]");
                        Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                    }
                });
    }

    private void initFromObservable() {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("dd");
        list.add("eee");
        Observable
                .from(new String[]{"2", "3"})
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext() called with: s = [" + s + "]");
                        Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                    }
                });
    }

    /**
     * Observable.map(FunX()), 把前几个参 转换成最后一个类， 并在 Subscriber.onNext() 中返回
     */
    private void initMapObservable() {
        just(R.string.app_name, R.string.haha)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        String string = getResources().getString(integer);
                        return string;
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext() called with: s = [" + s + "]");
                    }
                });

    }

    private void initFlatMapObservable() {
        Student s1 = new Student("李想", 22);
        Student s2 = new Student("爱丽", 33);
        just(s1, s2)
                .flatMap(new Func1<Student, Observable<String>>() {
                    @Override
                    public Observable<String> call(Student student) {
                        // 理解为 又递归调用了一次
                        return just(student.name);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "call() called with: s = [" + s + "]");
                    }
                });
    }

    class Student {
        private String name;
        private int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
