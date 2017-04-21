package com.djsz.rxjava2demo.bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.djsz.rxjava2demo.R;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author: Shawn
 * time  : 2017/4/14 09:41
 * desc  :
 */
public class RxBus1Activity extends AppCompatActivity {

    private static final String TAG = "RxBus1Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus_1);

        TextView tv1 = (TextView) findViewById(R.id.tv1);

        findViewById(R.id.btn1).setOnClickListener(v -> startActivity(new Intent(this, RxBus2Activity.class)));

        RxBus2.getDefault().toObservable()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        EventManager.ChangeTextEvent event = (EventManager.ChangeTextEvent) value;
                        Log.i(TAG, "onNext: " + event.text);
                        tv1.setText(event.text);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
