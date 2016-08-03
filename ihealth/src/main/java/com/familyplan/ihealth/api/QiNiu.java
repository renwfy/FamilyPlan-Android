package com.familyplan.ihealth.api;

import com.familyplan.ihealth.Constants;
import com.familyplan.ihealth.model.QiniuInfo;
import com.familyplan.ihealth.net.NSCallback;
import com.familyplan.ihealth.net.NSHttpClent;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LSD on 16/3/3.
 */
public class QiNiu extends NSHttpClent {
    public interface UpLoadListener {
        void onFail(String error);

        void onSuccess(String url);

        void onProgress(double percent);
    }

    private static boolean isCancelled = false;
    static UploadManager uploadManager = new UploadManager();

    public static void doUpload(String key, String token, String filePath, final UpLoadListener callback) {
        doUpload(key, token, new File(filePath), callback);
    }

    public static void doUpload(String key, String token, File data, final UpLoadListener callback) {
        isCancelled = false;
        uploadManager.put(data, key, token, new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (callback != null)
                            if (info.statusCode != 200) {
                                callback.onFail("上传失败");
                            } else {
                                callback.onSuccess(Constants.QINIU_HOST + key);
                            }
                    }
                },
                new UploadOptions(null, null, false, new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {
                        callback.onProgress(percent);
                    }
                },
                        new UpCancellationSignal() {
                            public boolean isCancelled() {
                                return isCancelled;
                            }
                        }));

    }

    public static void doUpload(String key, String token, byte[] data, final UpLoadListener callback) {
        isCancelled = false;
        uploadManager.put(data, key, token, new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {

                        if (callback != null)
                            if (info.statusCode != 200) {
                                callback.onFail("上传失败");
                            } else {
                                callback.onSuccess(Constants.QINIU_HOST + key);
                            }
                    }
                },
                new UploadOptions(null, null, false, new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {
                        callback.onProgress(percent);
                    }
                },
                        new UpCancellationSignal() {
                            public boolean isCancelled() {
                                return isCancelled;
                            }
                        }));

    }

    public static void doCancelUpload() {
        isCancelled = true;
    }


    /***
     * 获取七牛上传token
     *
     * @param key
     * @param callback
     */
    public static void getToken(String key, NSCallback<QiniuInfo> callback) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", key);
        get("/qiniu_uptoken", params, callback);
    }
}
