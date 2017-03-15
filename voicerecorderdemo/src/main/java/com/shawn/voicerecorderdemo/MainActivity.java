package com.shawn.voicerecorderdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private RecorderLinearLayout rll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rll = (RecorderLinearLayout) findViewById(R.id.rll);

        rll.setOnRecordListener(new RecorderLinearLayout.OnRecordListener() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: ");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: ");
            }
        });
    }
}
