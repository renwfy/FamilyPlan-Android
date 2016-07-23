package com.lib.utils;

import android.util.Log;

public abstract class AppLog {

    private static boolean isPrintLog = true;

    public static void d(String tag, String meg) {
        if ((tag != null) && (meg != null) && (isPrintLog))
            Log.d(tag, meg);
    }

    public static void e(String tag, String meg) {
        if ((tag != null) && (meg != null) && (isPrintLog))
            Log.e(tag, meg);
    }

    public static void i(String tag, String meg) {
        if ((tag != null) && (meg != null) && (isPrintLog))
            Log.i(tag, meg);
    }

    public static void v(String tag, String meg) {
        if ((tag != null) && (meg != null) && (isPrintLog))
            Log.v(tag, meg);
    }

    public static void w(String tag, String meg) {
        if ((tag != null) && (meg != null) && (isPrintLog))
            Log.w(tag, meg);
    }

    public static boolean getLogStatus() {
        return isPrintLog;
    }

    public static void setLogSwitcher(boolean paramBoolean) {
        isPrintLog = paramBoolean;
    }

}
