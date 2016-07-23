package com.familyplan.ihealth.net;

import android.text.TextUtils;

import com.familyplan.ihealth.Constants;
import com.lib.utils.AppLog;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LSD on 15/10/18.
 */
public class NSHttpClent {
    private static String Tag = NSHttpClent.class.getSimpleName();
    private static String BASE_URL = "";

    public static void init(String baseUrl){
        BASE_URL = baseUrl;
    }

    public static NSRequest get(String url, Map<String, String> pamas, NSCallback callback) {
        pamas = addParams(pamas);
        url = buildUrl(url, pamas);
        NSRequest request = new DefaultRequest(NSRequest.RequestMethod.GET,BASE_URL + url, callback);
        request.setHeaders(setHeader(null, callback));
        return request.doRequest();
    }

    public static NSRequest post(String url, Map<String, String> pamas, NSCallback callback) {
        pamas = addParams(pamas);
        NSRequest request = new DefaultRequest(NSRequest.RequestMethod.POST,BASE_URL + url, callback);
        request.setParams(pamas);
        request.setHeaders(setHeader(null, callback));
        return request.doRequest();
    }

    public static Map<String, String> addParams(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put("_o", "" + Constants.OS_TYPE);    // 操作系统
        //params.put("_u", "" + IApplication.getInstance().getUserId());    // 用户id
        //params.put("_v", "" + VersionManager.getAppVersion(CNApplication.getInstance()));    // 版本号*/
        return params;
    }

    public static Map<String, String> setHeader(Map<String, String> header, NSCallback callback) {
        if (header == null) {
            header = new HashMap<>();
        }
        if (callback != null && callback instanceof NSCallback.NSTokenCallback) {
            String token = "123";//IApplication.getInstance().getToken();
            if (TextUtils.isEmpty(token)) {
                callback.notLogin();
                return header;
            }
            header.put("x-auth-token", token); // token
        }
        return header;
    }

    private static String buildUrl(String url, Map<String, String> params) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), "utf-8"));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), "utf-8"));
                encodedParams.append('&');
            }
            return url + "?" + encodedParams.toString();
        } catch (Exception e) {
            AppLog.e(Tag, e.getMessage());
        }
        return url;
    }
}
