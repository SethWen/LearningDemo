package com.djsz.sqldemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "aaaaa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCreateDb = (Button) findViewById(R.id.btn_create);
        String s = "ll";
        String r = "yy";
        btnCreateDb.setText(String.format(getString(R.string.aaa), s, r));

//        btnCreateDb.setText(R.string.aaa);

        btnCreateDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySqliteHelper helper = new MySqliteHelper(MainActivity.this, "info.db", null, 3);
                SQLiteDatabase writableDatabase = helper.getWritableDatabase();
//                helper.onUpgrade(writableDatabase, 1, 2);
                Log.i(TAG, "onClick: version = " + writableDatabase.getVersion());
            }
        });

    }
}
