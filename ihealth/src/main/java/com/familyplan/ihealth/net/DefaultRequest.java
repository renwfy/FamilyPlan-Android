package com.familyplan.ihealth.net;

import com.lib.net.OkHttpClientMr;
import com.lib.utils.AppLog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Map;

/**
 * Created by LSD on 15/10/18.
 */
public class DefaultRequest implements NSRequest {
    String Tag = "DefaultRequest";
    String requestTag;
    RequestMethod method;
    String url;
    NSCallback callback;
    Map<String, String> params = null;
    Map<String, String> header = null;

    DefaultRequest(RequestMethod method, String url, NSCallback callback) {
        this.method = method;
        this.url = url;
        this.callback = callback;
    }

    @Override
    public NSRequest doRequest() {
        requestTag = "RTag:" + url;
        if (method == RequestMethod.GET) {
            AppLog.d(Tag, url);
            OkHttpUtils.get().url(url).headers(header).tag(requestTag).build().execute(callback);
        } else if (method == RequestMethod.POST) {
            AppLog.d(Tag, url + " , " + OkHttpClientMr.getStringReqParam(params));
            OkHttpUtils.post().url(url).headers(header).params(params).tag(requestTag).build().execute(callback);
        }
        return this;
    }

    @Override
    public void cancelRequest() {
        OkHttpUtils.getInstance().cancelTag(requestTag);
    }

    @Override
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public void setHeaders(Map<String, String> header) {
        this.header = header;
    }
}
