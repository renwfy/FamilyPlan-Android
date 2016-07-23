package com.familyplan.ihealth.webapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;
import android.view.WindowManager;

import com.familyplan.ihealth.activity.CommonActivity;
import com.familyplan.ihealth.model.Extra;

public class ZMWebActivity extends CommonActivity {
    private boolean is_SCREEN_ORIENTATION_LANDSCAPE = false;
    private boolean viewCreated = false;
    private String title;
    private String url;
    public ZMWebView webView;

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected void onViewCreated() {
        Extra extra = (Extra) getIntent().getSerializableExtra("extra");
        if (extra != null) {
            title = extra.getTitle();
            url = extra.getUrl();
            is_SCREEN_ORIENTATION_LANDSCAPE = extra.isLandscape();

            if (TextUtils.isEmpty(title)) {
                title = "超脑";
            }
            setTitle(title);
            //返回按钮
            if (extra.isNewActivity()) {
                titleBar.setBackEnable();
            } else {
                titleBar.setBackDisEnable();
            }
            //去标题
            if (extra.isDisableTitleBar()) {
                setTitleBarDisable();
            }
            //全屏
            if (extra.isFullScreen()) {
                setTitleBarDisable();
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }

        webView = new ZMWebView(mActivity, titleBar);
        setView(webView.getRoot());
        titleBar.setWebClient(webView);
        webView.init(getUrl());
    }

    @Override
    protected void onResume() {
        //横屏
        if (is_SCREEN_ORIENTATION_LANDSCAPE && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }
        super.onResume();
        if (viewCreated) {
            webView.onResume();
        }
        viewCreated = true;
    }

    public String getUrl() {
        return url;
    }

    public static void displayAloneActivity(Context activity, String title, String url) {
        Extra extra = new Extra();
        extra.setTitle(title);
        extra.setUrl(url);
        displayView(activity, extra);
    }

    public static void displayLandscape(Context activity, String title, String url) {
        Extra extra = new Extra();
        extra.setTitle(title);
        extra.setUrl(url);
        extra.setIsLandscape(true);
        extra.setIsNewActivity(true);
        displayView(activity, extra);
    }

    public static void displayWithNewActivity(Context activity, String title, String url) {
        Extra extra = new Extra();
        extra.setTitle(title);
        extra.setUrl(url);
        extra.setIsNewActivity(true);
        displayView(activity, extra);
    }

    public static void displayNoTitleBar(Context activity, String title, String url) {
        Extra extra = new Extra();
        extra.setTitle(title);
        extra.setUrl(url);
        extra.setDisableTitleBar(true);
        displayView(activity, extra);
    }

    public static void displayFullScreen(Context activity, String title, String url) {
        Extra extra = new Extra();
        extra.setTitle(title);
        extra.setUrl(url);
        extra.setFullScreen(true);
        displayView(activity, extra);
    }

    /***
     * 显示网页
     *
     * @param activity
     * @param extra
     */
    private static void displayView(Context activity, Extra extra) {
        Intent intent = new Intent(activity, ZMWebActivity.class);
        intent.putExtra("extra", extra);
        activity.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (webView != null) {
            if (webView.onBackPressed())
                super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
