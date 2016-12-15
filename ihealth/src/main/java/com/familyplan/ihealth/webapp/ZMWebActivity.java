package com.familyplan.ihealth.webapp;

import android.content.Context;
import android.content.Intent;

import com.familyplan.ihealth.activity.CommonActivity;
import com.familyplan.ihealth.model.Extra;

public class ZMWebActivity extends CommonActivity {
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
        if(extra != null){
            title = extra.getTitle();
            url = extra.getUrl();
        }
        setTitle(title);

        webView = new ZMWebView(mActivity, titleBar);
        setView(webView.getRoot());
        titleBar.setWebClient(webView);
        webView.init(getUrl());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (viewCreated) {
            //webView.onResume();
        }
        viewCreated = true;
    }

    public String getUrl() {
        return url;
    }

    public static void displayActivity(Context activity, String title, String url) {
        Extra extra = new Extra();
        extra.setTitle(title);
        extra.setUrl(url);
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
