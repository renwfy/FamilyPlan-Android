package com.lib.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.lib.AppLib;

public class AppTips {

    public static long lastTime = 0;

    public static void showToast(String content) {
        showToast(AppLib.appContext,content);
    }

    public static void showToast(Context context, String content) {
        long curTime = System.currentTimeMillis();
        if (curTime - lastTime < 1000) {
            lastTime = curTime;
            return;
        }
        if (TextUtils.isEmpty(content)) {
            return;
        }
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
