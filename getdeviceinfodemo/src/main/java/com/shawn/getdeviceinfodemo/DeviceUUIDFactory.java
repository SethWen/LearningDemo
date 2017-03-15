package com.shawn.getdeviceinfodemo;

import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author 作者 : TonyFung
 * @version 创建时间：2014年9月2日 上午9:29:32
 * @description 描述:生成设备唯一的ID
 */
public class DeviceUUIDFactory {


    public static String getMobileId(Context context) {
        String mobileId = null;
        if (null == mobileId) {
            synchronized (DeviceUUIDFactory.class) {
                if (null == mobileId) {
                    String savedMobileId = null;
                    if (null != savedMobileId) {
                        mobileId = savedMobileId;
                    } else {
                        final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
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

}
 