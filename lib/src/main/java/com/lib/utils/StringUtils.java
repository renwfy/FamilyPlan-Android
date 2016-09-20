package com.lib.utils;

import java.util.regex.Pattern;

/**
 * Created by bensonwang on 15-1-6.
 */
public class StringUtils {
    public static boolean isMobile(String phone) {
        Pattern p = Pattern
                .compile("^1\\d{10}$");
        return p.matcher(phone).matches();
    }
}
