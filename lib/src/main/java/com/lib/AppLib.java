package com.lib;

import android.app.Application;

import com.lib.utils.AppLog;
import com.lib.utils.FileUtil;

/**
 * Created by LSD on 15/9/23.
 */
public class AppLib {
    private static boolean debug;
    public static Application appContext;

    public  static void init(Application context){
        appContext = context;
    }

    /**
     * debug模式
     *
     * @param debug
     */
    public static void setDebug(boolean debug) {
        AppLib.debug = debug;
        AppLog.setLogSwitcher(debug);
    }

    public static boolean isDebug() {
        return debug;
    }

    /***
     * 初始化SD卡文件路径
     *
     * @param CACHE_BASE
     */
    public static void initFileUtil(String CACHE_BASE) {
        FileUtil.CACHE_BASE = CACHE_BASE;
    }
}
