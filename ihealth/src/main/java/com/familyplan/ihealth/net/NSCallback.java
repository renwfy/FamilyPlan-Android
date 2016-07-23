package com.familyplan.ihealth.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.lib.net.OkStringCallback;
import com.lib.utils.AppLog;
import com.lib.utils.AppTips;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by LSD on 15/10/18.
 */
public abstract class NSCallback<T> extends OkStringCallback {
    private String Tag = NSCallback.class.getSimpleName();
    private Context context;
    private NSJsonParser nsJsonParser = new NSJsonParser();
    private boolean showProgressBar;
    private String msg;
    private Class<T> clazz;
    private OnShowProgressDialogListener onShowProgressDialogListener;
    private OnNotLoginListener onNotLoginListener;

    public NSCallback(Context context, Class<T> clazz) {
        this.context = context;
        this.clazz = clazz;
        showProgressBar = false;
        msg = "";
        if (context instanceof OnShowProgressDialogListener) {
            onShowProgressDialogListener = (OnShowProgressDialogListener) context;
        }
        if (context instanceof OnNotLoginListener) {
            onNotLoginListener = (OnNotLoginListener) context;
        }
    }

    public NSCallback(Context context, Class<T> clazz, boolean showProgressBar, String msg) {
        this.context = context;
        this.clazz = clazz;
        this.showProgressBar = showProgressBar;
        this.msg = msg;
        if (context instanceof OnShowProgressDialogListener) {
            onShowProgressDialogListener = (OnShowProgressDialogListener) context;
        }
        if (context instanceof OnNotLoginListener) {
            onNotLoginListener = (OnNotLoginListener) context;
        }
    }

    @Override
    public void onResponse(String response, int id) {
        AppLog.d(Tag, response);
        nsJsonParser.parse(response, clazz, this);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        if (e != null) {
            e.printStackTrace();
            Log.e(Tag, "网络请求故障");
        }
        onFail(-2, "网络请求故障");
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        if (showProgressBar) {
            String content = TextUtils.isEmpty(msg) ? "请稍后" : msg;
            if (onShowProgressDialogListener != null)
                onShowProgressDialogListener.showProgressDialog(content);
        }
    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        if (showProgressBar) {
            if (onShowProgressDialogListener != null)
                onShowProgressDialogListener.hideProgressDialog();
        }
    }

    public void notLogin() {
        if (onNotLoginListener != null)
            onNotLoginListener.notLogin();
    }

    public void onSuccess(T t) {
    }

    public void onSuccess(List<T> t, int total) {
    }

    public void onFail(int code, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            AppTips.showToast(context, msg);
        }
    }


    /***
     * 显示dialog
     */
    public static interface OnShowProgressDialogListener {
        public void showProgressDialog(String msg);

        public void hideProgressDialog();
    }

    /***
     * 未登录
     */
    public interface OnNotLoginListener {
        public void notLogin();
    }

    /***
     * 需要登录
     */
    public static class NSTokenCallback<T> extends NSCallback<T> {
        public NSTokenCallback(Context context, Class<T> clazz) {
            super(context, clazz);
        }

        public NSTokenCallback(Context context, Class<T> clazz, boolean showProgressBar, String msg) {
            super(context, clazz, showProgressBar, msg);
        }
    }
}
