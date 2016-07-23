package com.familyplan.ihealth;

/**
 * Created by LSD on 16/7/23.
 */
public class Constants {
    public static final String BASE_FOLDER = "/ihealth";

    public static boolean DEBUG;

    public static final int OS_TYPE = 2;

    public static String BASE_URL;

    public static String QINIU_HOST = "http://zmkm.qiniudn.com/";//七牛地址

    public static void initDebug(boolean isDebug) {
        DEBUG = isDebug;
        BASE_URL = DEBUG ? "localhost:3012" : "http://api.ctchaonao.com/rest";
    }
}
