package com.familyplan.ihealth.activity;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.familyplan.ihealth.R;

import butterknife.BindView;

/**
 * Created by LSD on 16/6/18.
 */
public class WebViewActivity extends CommonActivity {
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progress_wheel)
    ProgressBar progressWheel;

    @Override
    protected int getContentView() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();

        WebSettings webSettings = webview.getSettings();
        // 设置可以支持缩放
        webSettings.setSupportZoom(false);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(false);
        // 扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //内部打开
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressWheel.setVisibility(View.VISIBLE);
                view.setVisibility(View.INVISIBLE);
                setTitle(webview.getTitle());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressWheel.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
                setTitle(webview.getTitle());
            }
        });

        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            webview.loadUrl(url);
        }
    }
}
