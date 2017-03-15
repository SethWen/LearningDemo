package com.shawn.lambdademo;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        doLambda1();
//        doLambda2();
        doLambda3();


        setInter(() -> Log.d(TAG, "onCreate: "));


    }

    private void doLambda3() {
        List<String> lans = Arrays.asList("Java", "Python", "C++", "PHP", "JavaScript");
    }

    private void filter(List<String> lans, Predicate condition) {
        for (String lan : lans) {
        }
    }

    @TargetApi(24)
    private void doLambda2() {
        List<String> lans = Arrays.asList("Java", "Python", "C++", "PHP", "JavaScript");
        // 1. 数组的 forEach lambda 调用
        lans.forEach(n -> Log.d(TAG, "doLambda2: " + n));
        // 2. Lambda 双冒号 调用 方法
        lans.forEach(this::printLog);
    }

    private void printLog(String str) {
        Log.d(TAG, "printLog: " + str);
    }

    /**
     * Lambda 表达式可以理解为，将方法体作为参数出入方法内，也即传入的是代码块
     */
    private void doLambda1() {
        // 1. 无参, 前边括号中为空, 如果只有一条语句，那么可以省略大括号，否则不可省略
        new Thread(() -> {
            Log.d(TAG, "doLambda1: ");
            Log.d(TAG, "doLambda1: ");
        }
        ).start();

        // 2. 有参，参数为内部类必需重写的方法的参数，参数类型可以省略
        ListView listView = new ListView(this);
        listView.setOnItemClickListener((parent, view, position, id) -> Log.d(TAG, "onItemClick: "));

        // 3. 只有一个参数，那么参数的括号也可以省略
        new TextView(this).setOnClickListener(v -> Log.d(TAG, "doLambda1: "));
        new TextView(this).setOnClickListener(v -> {
            Log.d(TAG, "doLambda1: ");
        }

        );
    }

    private void setInter(Inter inter) {

    }

    @FunctionalInterface
    @TargetApi(24)
    interface Inter {
        void hah();
        
//        default void hei() {
//            Log.d(TAG, "hei: ");
//        }
    }

}
