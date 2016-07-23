package com.lib.net;

import java.util.Map;

import okhttp3.Request;

/**
 * Created by LSD on 15/11/8.
 */
public abstract class NetCallback {
    protected void onBefore(Request request) {
    }

    protected void onAfter() {
    }

    protected void onHeader(Map<String, String> headers) {
    }

    protected abstract void onFailure(final String error, final int code);

    protected abstract void onResponse(final String response);
}
