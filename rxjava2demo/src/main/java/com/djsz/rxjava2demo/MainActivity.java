package com.djsz.rxjava2demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_basic).setOnClickListener(v -> startActivity(new Intent(this, BasicRxJavaActivity.class)));
        findViewById(R.id.btn_creating_operator).setOnClickListener(v -> startActivity(new Intent(this, CreatingOperatorActivity.class)));
        findViewById(R.id.btn_transforming_operator).setOnClickListener(v -> startActivity(new Intent(this, TransformingOperatorActivity.class)));
        findViewById(R.id.btn_filtering_operator).setOnClickListener(v -> startActivity(new Intent(this, FilteringOperatorActivity.class)));
        findViewById(R.id.btn_flow_back).setOnClickListener(v -> startActivity(new Intent(this, FlowBackActivity.class)));
        findViewById(R.id.btn_flow_back).setOnClickListener(v -> startActivity(new Intent(this, CombiningOperatorActivity.class)));
        findViewById(R.id.btn_flow_back).setOnClickListener(v -> startActivity(new Intent(this, AssistantOperatorActivity.class)));
    }

    private void newDir() {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "hahh");
        if (!dir.exists()) {
            dir.mkdir();
            Log.i(TAG, "newDir: ");
        }
    }
}
