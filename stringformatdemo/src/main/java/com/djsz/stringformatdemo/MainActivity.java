package com.djsz.stringformatdemo;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView tvBefore;
    private TextView tvAfter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvBefore = (TextView) findViewById(R.id.tv_before);
        tvAfter = (TextView) findViewById(R.id.tv_after);

        String raw = "hello";
        tvBefore.setText(raw);
//        tvAfter.setText(String.format(Locale.US, "AAA%1$sBBB%2$d", raw, 22));
        // 1. %
//        tvAfter.setText(String.format(Locale.US, "AAA%1$-10sBBB", raw));
//        tvAfter.setText(String.format(Locale.US, "$04d", 2));
//        tvAfter.setText(String.format(Locale.US, "$04d", 2));

        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            tvBefore.setText(getPackageName());
            packageInfo = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String keyHash = getHashKeyOfSign(packageInfo);
        Log.i(TAG, "onCreate -> HashKey: " + keyHash);
        tvAfter.setText(keyHash);

    }

    private static String getHashKeyOfSign(PackageInfo info) {
        try {
            Signature[] signatures = info.signatures;
            int length = signatures.length;
            byte i = 0;
            if (i < length) {
                Signature signature = signatures[i];
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), 0);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
