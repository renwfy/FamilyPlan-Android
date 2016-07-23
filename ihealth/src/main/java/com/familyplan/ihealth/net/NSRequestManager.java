package com.familyplan.ihealth.net;

import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * Created by LSD on 15/10/19.
 */
public class NSRequestManager {
    private static NSRequestManager INSTANCE;
    private Context context;


    public static NSRequestManager init(Context context, String baseUrl) {
        INSTANCE = new NSRequestManager();
        INSTANCE.context = context;
        NSHttpClent.init(baseUrl);
        InitOkHttpClient();
        return INSTANCE;
    }

    public static void InitOkHttpClient() {
        //ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));
        //https
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);//证书的inputstream,本地证书的inputstream,本地证书的密码
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                //.cookieJar(cookieJar)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public static NSRequestManager getInstance() {
        if (INSTANCE == null) {
            throw new RuntimeException("Request 未初始化!");
        }
        return INSTANCE;
    }

    public Context getContext() {
        return context;
    }
}
