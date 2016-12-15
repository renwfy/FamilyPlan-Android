package com.familyplan.ihealth.webapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.activity.AbstractActivity;
import com.familyplan.ihealth.model.ShareInfo;
import com.familyplan.ihealth.view.titlebar.TitleBar;
import com.familyplan.ihealth.view.titlebar.TitleBarMenuItem;
import com.google.gson.Gson;
import com.lib.utils.AppLog;
import com.lib.utils.AppTips;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ZMWebView {
    private static final String TAG = "ZMWebView";
    private AbstractActivity context;
    private View root;
    private TitleBar titleBar;

    private boolean isError = false;
    @BindView(R.id.framelayout)
    RelativeLayout frameLayout;
    @BindView(R.id.web_ptrframelayout)
    PtrClassicFrameLayout webPtrFrameLayout;
    @BindView(R.id.zm_webview)
    WebView webView;
    @BindView(R.id.progress_wheel)
    ProgressBar progressBar;
    @BindView(R.id.rl_error)
    RelativeLayout error_layout;
    @BindView(R.id.tv_error_text)
    TextView error_text;

    WebChromeClient chromeClient;
    View fillView;//全屏
    protected Handler handler = new Handler();

    ShareInfo shareInfo;

    /**
     * @param context
     * @param titleBar
     */
    public ZMWebView(AbstractActivity context, TitleBar titleBar) {
        this.context = context;
        this.titleBar = titleBar;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        root = layoutInflater.inflate(R.layout.view_web, null);
        ButterKnife.bind(this, root);
    }

    public void init(String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        //允许缓存
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAllowFileAccess(true);
        String cachepath = context.getCacheDir().getAbsolutePath() + "/ihealth";
        webSettings.setAppCachePath(cachepath);
        webSettings.setDatabasePath(cachepath);
        // 设置可以支持缩放
        webSettings.setSupportZoom(false);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(false);
        // 扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // 高版本支持https
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //禁用长按事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setWebChromeClient(chromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            //全屏播放
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                if (fillView != null) {
                    callback.onCustomViewHidden();
                    return;
                }
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                fillView = view;
                fillView.setBackgroundColor(Color.parseColor("#000000"));
                fillView.setLayoutParams(params);
                frameLayout.addView(fillView);
                titleBar.setVisibility(View.GONE);
            }

            @Override
            public void onHideCustomView() {
                if (fillView == null) {
                    return;
                }
                frameLayout.removeView(fillView);
                fillView = null;
                titleBar.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AppTips.showToast(message);
                result.cancel();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                AppTips.showToast(message);
                result.cancel();
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

        });
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                AppLog.d("TTT","shouldOverrideUrlLoading");
                return WebviewUtils.shouldOverrideUrlLoading(ZMWebView.this, url);
            }

            //当https错误时,接受所有网站的证书，忽略SSL错误，执行访问网页
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Ignore SSL certificate errors
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                error_layout.setVisibility(View.GONE);
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //setWebCanGoBack();
                progressBar.setVisibility(View.GONE);
                if (isError) {
                    //titleBar.setTitle("");
                    error_layout.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.VISIBLE);
                    //设置程序的标题为网页的标题
                    if (webView.getTitle() != null && !webView.getTitle().contains("ctchaonao")) {
                        //titleBar.setTitle(webView.getTitle());
                    }
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                AppLog.e(TAG, description);
                super.onReceivedError(view, errorCode, description, failingUrl);
                isError = true;
                //pageLoadError(description, errorCode + "", failingUrl);
            }

        });
        webView.addJavascriptInterface(new MyJsInterface(), "fplan");
        webPtrFrameLayout.setLastUpdateTimeRelateObject(this);
        webPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //onrefrash
                onRefresh();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webPtrFrameLayout.refreshComplete();
                    }
                },1200);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, webView, header);
            }
        });
        webPtrFrameLayout.disableWhenHorizontalMove(true);
        WebviewUtils.loadUrl(webView, url);
    }

    @OnClick(R.id.rl_error)
    void load() {
        isError = false;
        error_layout.setVisibility(View.INVISIBLE);
        webView.reload();
    }

    public void onResume() {
        onRefresh();
    }

    public void showShare() {
        if (shareInfo == null) {
            return;
        }
        //context.showShare(shareInfo, shareListener);
    }

    public boolean onBackPressed() {
        if (fillView != null) {
            chromeClient.onHideCustomView();
            return false;
        }
        return true;
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    public void setPullToRefreshDisEnable(){
        webPtrFrameLayout.setEnabled(false);
    }

    protected void setTitleBarMenu(TitleBarMenuItem item) {
        titleBar.setTitleMenuItem(item);
    }

    public View getRoot() {
        return root;
    }

    public WebView getWebView() {
        return webView;
    }

    public AbstractActivity getContext() {
        return context;
    }

    //调用页面js 函数，来刷新页面
    public void onRefresh() {
        webView.loadUrl("javascript:onRefresh()");
    }

    //webview是否能返回
    public void setWebCanGoBack() {
        if (context instanceof ZMWebActivity) {
            //独立的页面 - 支持finish
            return;
        }
        if (webView.canGoBack()) {
            titleBar.setBackEnable();
        } else {
            titleBar.setBackDisEnable();
        }
    }
    //友盟统计页面错误
    private void pageLoadError(String errormsg, String errorcode, String errorurl) {
    }

    public class MyJsInterface {
        /***
         * 通过js设置标题栏
         *
         * @param info
         */
        @JavascriptInterface
        public void setTitleBtn(String info) {
            AppLog.d(TAG, info);
            try {
                final TitleBarMenuItem item = new Gson().fromJson(info, TitleBarMenuItem.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setTitleBarMenu(item);
                    }
                });

            } catch (Exception e) {
                AppLog.d(TAG, e.getMessage());
            }
        }

        @JavascriptInterface
        public void setUpShareInfo(String info) {
            shareInfo = new Gson().fromJson(info, ShareInfo.class);
        }


    }

    /***
     * 清除缓存
     *
     * @param context
     */
    public static void cleanCache(Context context) {
        try {
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");

            new WebView(context).clearCache(true);
            new WebView(context).clearHistory();

            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();

            File appCacheDir = new File(context.getCacheDir().getAbsolutePath()+"/zmkm");
            File webviewCacheDir = new File(context.getCacheDir().getAbsolutePath()+"/webviewCache");

            if(webviewCacheDir.exists()){
                deleteFile(webviewCacheDir);
            }
            if(appCacheDir.exists()){
                deleteFile(appCacheDir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 删除
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }
}
