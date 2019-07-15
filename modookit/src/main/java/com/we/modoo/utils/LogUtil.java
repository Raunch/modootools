package com.we.modoo.utils;

import android.util.Log;

import com.we.modoo.modoo.BuildConfig;


public final class LogUtil {

    private final static String LOG_TAG = "modoo";
    private final static String TAG_PREFIX = "[modoo] : ";
    private static boolean sEnable = BuildConfig.DEBUG;

    private LogUtil() {
    }

    public static void v(String tag, String message) {
        if (!sEnable && !Log.isLoggable(LOG_TAG, Log.VERBOSE)) {
            return;
        }
        Log.v(TAG_PREFIX + tag, message, null);
    }

    public static void d(String tag, String message) {
        if (!sEnable && !Log.isLoggable(LOG_TAG, Log.DEBUG)) {
            return;
        }
        Log.d(TAG_PREFIX + tag, message, null);
    }

    public static void i(String tag, String message) {
        if (!sEnable && !Log.isLoggable(LOG_TAG, Log.INFO)) {
            return;
        }
        Log.i(TAG_PREFIX + tag, message);
    }

    public static void w(String tag, String message) {
        if (!sEnable && !Log.isLoggable(LOG_TAG, Log.WARN)) {
            return;
        }
        Log.w(TAG_PREFIX + tag, message);
    }

    public static void e(String tag, String message) {
        e(tag, message, null);
    }

    public static void e(String tag, String message, Throwable tr) {
        if (!sEnable && !Log.isLoggable(LOG_TAG, Log.ERROR)) {
            return;
        }
        Log.e(TAG_PREFIX + tag, message, tr);
    }
}
