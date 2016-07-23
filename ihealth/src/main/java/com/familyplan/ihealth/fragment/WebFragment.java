package com.familyplan.ihealth.fragment;

import android.widget.LinearLayout;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.activity.AbstractActivity;
import com.familyplan.ihealth.view.titlebar.TitleBar;
import com.familyplan.ihealth.webapp.ZMWebView;

/**
 * Created by LSD on 16/3/8.
 */
public abstract class WebFragment extends BaseFragment {
    TitleBar titleBar;
    LinearLayout container;

    ZMWebView webView;
    boolean viewCreated;

    @Override
    protected int getFrameLayout() {
        return R.layout.fragment_web;
    }

    @Override
    protected void onViewCreated() {
        initFrameLayout();
        webView = new ZMWebView((AbstractActivity) mContext, titleBar);
        container.addView(webView.getRoot());
        titleBar.setWebClient(webView);
        webView.init(getUrl());
        titleBar.setBackDisEnable();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(viewCreated){
            webView.onResume();
        }
        viewCreated = true;
    }

    private void initFrameLayout(){
        container = (LinearLayout) rootView.findViewById(R.id.ll_container);
        titleBar = (TitleBar) rootView.findViewById(R.id.titlebar);
    }

    public void setTitle(String title) {
        titleBar.setTitle(title);
    }

    public abstract String getUrl();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
