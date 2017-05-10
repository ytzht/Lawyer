package com.onekeyask.lawyer.global;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by zht on 2017/04/07 10:31
 */

public class L {

    public static final String TAG = "=====";

    public static void d(String msg){
        if (BuildConfig.DEBUG) {
            if (TextUtils.isEmpty(msg)){
                Log.d(TAG, "log为空");
            }else {
                Log.d(TAG, msg);
            }
        }
    }

    public static void d(String TAG, String msg){
        if (BuildConfig.DEBUG) {
            if (TextUtils.isEmpty(msg)){
                Log.d(TAG, "log为空");
            }else {
                Log.d(L.TAG + TAG, msg);
            }

        }
    }

    public static void e(String msg){
        if (BuildConfig.DEBUG) {
            if (TextUtils.isEmpty(msg)){
                Log.d(TAG, "log为空");
            }else {
                Log.e(TAG, msg);
            }

        }
    }

    public static void e(String TAG, String msg){
        if (BuildConfig.DEBUG) {
            if (TextUtils.isEmpty(msg)){
                Log.d(TAG, "log为空");
            }else {
                Log.e(L.TAG + TAG, msg);
            }

        }
    }







}
