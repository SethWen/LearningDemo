package com.shawn.getdeviceinfodemo;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.init(this);

        /**
         * 获取时区 Id
         */
        String id = TimeZone.getDefault().getID();
        Log.e(TAG, "onCreate: timeZone=" + id);

        long time = Long.parseLong(String.valueOf(System.currentTimeMillis()).substring(0, 10));
        Log.e("时间:::", "" + time);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 1.
                 */
                String mobileId = PhoneUtils.getMobileId();
                Log.e(TAG, "onClick: mobileId=" + mobileId);
                String brandAndModel = PhoneUtils.getBrandAndModel();
                Log.e(TAG, "onClick: brandAndModel=" + brandAndModel);
                String androidVersion = PhoneUtils.getAndroidVersion();
                Log.e(TAG, "onClick: androidVersion=" + androidVersion);
                String packageVersion = PackageInfoUtil.getPackageVersion(MainActivity.this);
                Log.e(TAG, "onClick: packageVersion=" + packageVersion);

//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                long l = System.currentTimeMillis();
                Log.e(TAG, "onClick: mls = " + l);
                Date curDate = new Date(1483687960000L);
                //获取当前时间
                String curTime = formatter.format(curDate);
                Log.e(TAG, "onClick: curTime=" + curTime);
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                int phoneType = PhoneUtils.getPhoneType();

                /**
                 * 2. 手机品牌 + 手机型号 形如： xiaomi-401
                 * 3. Android 版本 Build.VERSION.RELEASE
                 * 4. 当前的请求时间
                 * 5. App 版本号
                 */
                String handSetInfo =
                        "手机品牌:" + Build.BRAND +
                                "手机型号:" + Build.MODEL +
                                ",SDK版本:" + Build.VERSION.SDK_INT +
                                ",系统版本:" + Build.VERSION.RELEASE;
                Log.e(TAG, "onClick::::" + handSetInfo);
            }
        });
    }
}
