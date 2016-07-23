package com.familyplan.ihealth.webapp;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebView;

import com.lib.utils.AppLog;

public class WebviewUtils {

    public static final String TAG = "WebviewUtils";

    public static boolean shouldOverrideUrlLoading(ZMWebView view, String url) {
        if (TextUtils.isEmpty(url)) {
            return true;
        }
        try {
            if (url.contains("#share")) {    // ‘#share’ 调用分享
                view.showShare();
            } else {   // 默认在当前页打开
                // 域名白名单 过滤
                if (isWhiteDomain(view.getContext(), url)) {
                    loadUrl(view.getWebView(), url);
                }
            }
        } catch (Exception e) {
            AppLog.e(TAG, e.getMessage());
            url = appendParams(url);
            loadUrl(view.getWebView(), url);
        }
        return true;
    }

    /***
     * 是否是白名单
     *
     * @param context
     * @param url
     * @return
     */
    public static boolean isWhiteDomain(Context context, String url) {
        return true;
//        String domain = url;
//        int start = domain.indexOf("//");
//        if (start <= 0) {
//            start = 0;
//        } else {
//            start += 2;
//        }
//        domain = domain.substring(start);
//        int end = domain.indexOf("/");
//        if (end <= 0) {
//            end = domain.length();
//        }
//        domain = domain.substring(0, end);
//        List<String> white = Session.INSTANCE.getSystemInfo().getWhiteDomain();
//        if (white == null) { // 默认
//            white = Arrays.asList(context.getResources().getStringArray(R.array.local_whitedomain));
//        }
//        List<String> white = new ArrayList<>();
//        boolean isWhiteDomain = false;
//        for (String w : white) {
//            if (domain.contains(w)) {
//                isWhiteDomain = true;
//                break;
//            }
//        }
//        return isWhiteDomain;
    }

    public static void loadUrl(WebView web, String url) {
        if (TextUtils.isEmpty(url)) {
            AppLog.w(TAG, "url is empty");
            return;
        }
        url = appendParams(url);
        AppLog.w(TAG, "url  = " + url);
        web.loadUrl(url);
    }

    /**
     * url 附加通用参数，包括token,_u,_v_o。
     * 去除#部分。
     *
     * @param url
     * @return
     */
    public static String appendParams(String url) {
        return url;
//        if (TextUtils.isEmpty(url)) {
//            return "";
//        }
//        // 去除#开头的部分
//        if (url.contains("#")) {
//            url = url.substring(0, url.indexOf("#"));
//        }
//        if (!url.contains("?")) {
//            url += "?";
//        } else {
//            url += "&";
//        }
//        return url + "token=" + IApplication.getInstance().getToken() + "&_u=" + IApplication.getInstance().getUserId()
//                + "&_o=" + Constants.OS_TYPE + "&_v=" + VersionManager.getAppVersionName(IApplication.getInstance());
    }
}
