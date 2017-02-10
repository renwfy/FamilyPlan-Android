package com.familyplan.ihealth;

/**
 * Created by LSD on 16/7/23.
 */
public class Constants {
    public static final String BASE_FOLDER = "/ihealth";

    public static boolean DEBUG;

    public static final int OS_TYPE = 2;

    public static String BASE_URL;
    public static String WEB_HOST;

    public static String QINIU_HOST = "http://oac4ul6pe.bkt.clouddn.com/";//七牛地址

    public static void initDebug(boolean isDebug) {
        DEBUG = isDebug;
        BASE_URL = DEBUG ? "http://10.0.2.2:3012/api" : "http://ifamilyplan.com:3012/api";
        WEB_HOST = DEBUG ? "http://10.0.2.2:3012/app" : "http://ifamilyplan.com:3012/app";
    }
}
