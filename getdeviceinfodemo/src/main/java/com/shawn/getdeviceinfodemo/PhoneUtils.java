package com.shawn.getdeviceinfodemo;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import static com.shawn.getdeviceinfodemo.Utils.context;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : 手机相关工具类
 * </pre>
 */
public class PhoneUtils {

    private PhoneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取IMEI码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMIE码
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getDeviceId() : null;
    }

    /**
     * 获取IMSI码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMIE码
     */
    @SuppressLint("HardwareIds")
    public static String getIMSI() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSubscriberId() : null;
    }

    /**
     * 跳至拨号界面
     *
     * @param phoneNumber 电话号码
     */
    public static void dial(String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    }

    /**
     * 拨打电话
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}</p>
     *
     * @param phoneNumber 电话号码
     */
    public static void call(String phoneNumber) {
        context.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber)));
    }

    /**
     * 跳至发送短信界面
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    public static void sendSms(String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }

    /**
     * 发送短信
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.SEND_SMS"/>}</p>
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    public static void sendSmsSilent(String phoneNumber, String content) {
        if (TextUtils.isEmpty(content)) return;
        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
        SmsManager smsManager = SmsManager.getDefault();
        if (content.length() >= 70) {
            List<String> ms = smsManager.divideMessage(content);
            for (String str : ms) {
                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
            }
        } else {
            smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
        }
    }

    public static String getMobileId() {
        String mobileId = null;
        if (null == mobileId) {
            synchronized (DeviceUUIDFactory.class) {
                if (null == mobileId) {
                    String savedMobileId = null;
                    if (null != savedMobileId) {
                        mobileId = savedMobileId;
                    } else {
                        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings
                                .Secure.ANDROID_ID);
                        try {
                            if (!"9774d56d682e549c".equals(androidId) && (null != androidId)) {
                                mobileId = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();

                            } else {
                                String deviceId = ((TelephonyManager) context.getSystemService(Context
                                        .TELEPHONY_SERVICE)).getDeviceId();
                                mobileId = ((null != deviceId) ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) :
                                        UUID.randomUUID()).toString();
                            }
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        return mobileId.replaceAll("-", "");
    }

    /**
     * 获取手机品牌和型号
     *
     * @return
     */
    public static String getBrandAndModel() {
        String phoneInfo = null;
        try {
            phoneInfo = Build.BRAND + "-" + Build.MODEL;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phoneInfo == null ? "Unknown brand and model" : phoneInfo;
    }

    /**
     * 获取 Android 版本信息
     *
     * @return
     */
    public static String getAndroidVersion() {
        String androidVersion = null;
        try {
            androidVersion = Build.VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return androidVersion == null ? "Unknown android version" : androidVersion;
    }
}