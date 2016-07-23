package com.lib.net;

import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Response;

/**
 * Created by LSD on 16/7/23.
 */
public abstract class OkStringCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {
        //headers
        Map<String, String> headers = new HashMap<>();
        Set<String> keys = response.headers().names();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = response.header(key, "");
            headers.put(key, value);
        }
        onHeader(headers);
        //respons
        return response.body().string();
    }

    protected void onHeader(Map<String, String> headers) {
    }
}
