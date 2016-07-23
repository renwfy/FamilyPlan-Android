package com.lib.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpClientMr {
    String Tag = "OkHttpClientMr";
    private static OkHttpClientMr mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private GetDelegate mGetDelegate = new GetDelegate();
    private PostDelegate mPostDelegate = new PostDelegate();

    private OkHttpClientMr() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static OkHttpClientMr getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientMr.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientMr();
                }
            }
        }
        return mInstance;
    }

    public OkHttpClient getClient() {
        return mOkHttpClient;
    }

    public GetDelegate getGetDelegate() {
        return mGetDelegate;
    }

    public PostDelegate getPostDelegate() {
        return mPostDelegate;
    }

    /**
     * Get
     */
    public static void getAsyn(String url, Map<String, String> header, Object tag, NetCallback callback) {
        getInstance().getGetDelegate().getAsyn(url, header, tag, callback);
    }

    /**
     * POST
     */
    public static void postAsyn(String url, Map<String, String> params, Map<String, String> header, Object tag, final NetCallback callback) {
        getInstance().getPostDelegate().postAsyn(url, params, header, tag, callback);
    }

    //JsonPost
    public static void postJsonAsyn(String url, Map<String, String> params, Map<String, String> header, Object tag, final NetCallback callback) {
        getInstance().getPostDelegate().postJsonAsyn(url, params, header, tag, callback);
    }

    public static String getStringReqParam(Map<String, String> mapparams) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<?> iterator = mapparams.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            stringBuffer.append(key + "=" + mapparams.get(key) + "&");
        }
        String stringReqParam = stringBuffer.toString();
        return stringReqParam.substring(0, stringReqParam.length() - 1);
    }


    private void deliveryResult(NetCallback callback, final Request request) {
        if (callback == null) callback = DEFAULT_RESULT_CALLBACK;
        final NetCallback resCallBack = callback;
        //UI thread
        resCallBack.onBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e != null) {
                    e.printStackTrace();
                    Log.e(Tag, "网络请求故障");
                }
                sendFailedStringCallback("网络请求故障", -2, resCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //headers
                Map<String, String> headers = new HashMap<>();
                Set<String> keys = response.headers().names();
                Iterator<String> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = response.header(key, "");
                    headers.put(key, value);
                }

                //body
                String body = "";
                try {
                    body = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sendSuccessResultCallback(headers, body, resCallBack);
            }
        });
    }

    private void sendFailedStringCallback(final String error, final int code, final NetCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(error, code);
                callback.onAfter();
            }
        });
    }

    private void sendSuccessResultCallback(final Map<String, String> headers, final String response, final NetCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onHeader(headers);
                callback.onResponse(response);
                callback.onAfter();
            }
        });
    }

    public static void cancelTag(Object tag) {
        getInstance()._cancelTag(tag);
    }

    private void _cancelTag(Object tag) {
    }

    public static OkHttpClient getClinet() {
        return getInstance().client();
    }

    public OkHttpClient client() {
        return mOkHttpClient;
    }

    private final NetCallback DEFAULT_RESULT_CALLBACK = new NetCallback() {
        @Override
        protected void onFailure(String error, int code) {

        }

        @Override
        protected void onResponse(String response) {

        }
    };


    /***
     * PostDelegate
     */
    public class PostDelegate {
        private final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream; charset=utf-8");
        private final MediaType MEDIA_TYPE_STRING = MediaType.parse("text/plain; charset=utf-8");
        private final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

        /**
         * 直接将bodyStr以写入请求体
         */
        public void postAsyn(String url, String bodyStr, final NetCallback callback, Object tag) {
            postAsynWithMediaType(url, bodyStr, MEDIA_TYPE_STRING, callback, tag);
        }

        /***
         * params Json形式
         */
        public void postAsyn(String url, Map<String, String> params, final NetCallback callback, Object tag) {
            String jsonStr = new Gson().toJson(params);
            postAsynWithMediaType(url, jsonStr, MEDIA_TYPE_JSON, callback, tag);
        }

        /***
         * params Json形式&header
         */
        public void postJsonAsyn(String url, Map<String, String> params, Map<String, String> header, Object tag, final NetCallback callback) {
            String jsonStr = new Gson().toJson(params);
            postAsynWithMediaType(url, jsonStr, MEDIA_TYPE_JSON, header, tag, callback);
        }

        /**
         * 直接将bodyBytes以写入请求体
         */
        public void postAsyn(String url, byte[] bodyBytes, final NetCallback callback, Object tag) {
            postAsynWithMediaType(url, bodyBytes, MEDIA_TYPE_STREAM, callback, tag);
        }

        /**
         * 直接将bodyFile以写入请求体
         */
        public void postAsyn(String url, File bodyFile, final NetCallback callback, Object tag) {
            postAsynWithMediaType(url, bodyFile, MEDIA_TYPE_STREAM, callback, tag);
        }

        /**
         * 直接将bodyStr以写入请求体
         */
        public void postAsynWithMediaType(String url, String bodyStr, MediaType type, final NetCallback callback, Object tag) {
            RequestBody body = RequestBody.create(type, bodyStr);
            Request request = buildPostRequest(url, body, tag);
            deliveryResult(callback, request);
        }

        /**
         * 直接将bodyStr以写入请求体 加 Header
         */
        public void postAsynWithMediaType(String url, String bodyStr, MediaType type, Map<String, String> header, Object tag, final NetCallback callback) {
            RequestBody body = RequestBody.create(type, bodyStr);
            Request request = buildPostRequest(url, body, header, tag);
            deliveryResult(callback, request);
        }

        /**
         * 直接将bodyBytes以写入请求体
         */
        public void postAsynWithMediaType(String url, byte[] bodyBytes, MediaType type, final NetCallback callback, Object tag) {
            RequestBody body = RequestBody.create(type, bodyBytes);
            Request request = buildPostRequest(url, body, tag);
            deliveryResult(callback, request);
        }

        /**
         * 直接将bodyFile以写入请求体
         */
        public void postAsynWithMediaType(String url, File bodyFile, MediaType type, final NetCallback callback, Object tag) {
            RequestBody body = RequestBody.create(type, bodyFile);
            Request request = buildPostRequest(url, body, tag);
            deliveryResult(callback, request);
        }

        /**
         * post构造Request的方法
         *
         * @param url
         * @param body
         * @return
         */
        private Request buildPostRequest(String url, RequestBody body, Object tag) {
            Request.Builder builder = new Request.Builder()
                    .url(url)
                    .post(body);
            if (tag != null) {
                builder.tag(tag);
            }
            Request request = builder.build();
            return request;
        }

        /***
         * post构造Request的方法 添加header
         *
         * @param url
         * @param body
         * @param header
         * @param tag
         * @return
         */
        private Request buildPostRequest(String url, RequestBody body, Map<String, String> header, Object tag) {
            Request.Builder builder = new Request.Builder()
                    .url(url)
                    .post(body);
            if (header != null) {
                builder.headers(Headers.of(header));
            }
            if (tag != null) {
                builder.tag(tag);
            }
            Request request = builder.build();
            return request;
        }


        /**
         * 异步的post请求
         */
        public void postAsyn(String url, Map<String, String> params, Map<String, String> header, Object tag, final NetCallback callback) {
            Request request = buildPostFormRequest(url, params, header, tag);
            deliveryResult(callback, request);
        }

        private Request buildPostFormRequest(String url, Map<String, String> params, Map<String, String> header, Object tag) {
            if (params == null) {
                params = new HashMap<>();
            }
            FormBody.Builder formBuildr = new FormBody.Builder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                formBuildr.add(param.getKey(), param.getValue());
            }
            RequestBody requestBody = formBuildr.build();
            Request.Builder reqBuilder = new Request.Builder();
            reqBuilder.url(url).post(requestBody);
            if (header != null) {
                reqBuilder.headers(Headers.of(header));
            }
            if (tag != null) {
                reqBuilder.tag(tag);
            }
            return reqBuilder.build();
        }
    }

    /****
     * GetDelegate
     */
    public class GetDelegate {
        private Request buildGetRequest(String url, Map<String, String> header, Object tag) {
            Request.Builder builder = new Request.Builder()
                    .url(url);
            if (header != null) {
                builder.headers(Headers.of(header));
            }
            if (tag != null) {
                builder.tag(tag);
            }
            return builder.build();
        }

        public void getAsyn(String url, Map<String, String> header, Object tag, final NetCallback callback) {
            final Request request = buildGetRequest(url, header, tag);
            getAsyn(request, callback);
        }

        public void getAsyn(Request request, NetCallback callback) {
            deliveryResult(callback, request);
        }
    }
}