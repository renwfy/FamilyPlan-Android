package com.familyplan.ihealth.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.view.titlebar.TitleBar;

import butterknife.ButterKnife;

/**
 * Created by LSD on 16/5/30.
 */
public abstract class NativeFragment extends BaseFragment {
    TitleBar titleBar;
    LinearLayout container;

    @Override
    protected int getFrameLayout() {
        return R.layout.fragment_native;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initFrameLayout();
        titleBar.setBackDisEnable();
        int viewId = getContentView();
        if (viewId != 0) {
            View view = View.inflate(mContext, viewId, null);
            ButterKnife.bind(this, view);
            setView(view);
        }
    }

    public void setTitleBarDisable(){
        titleBar.setVisibility(View.GONE);
    }

    private void initFrameLayout() {
        container = (LinearLayout) rootView.findViewById(R.id.ll_container);
        titleBar = (TitleBar) rootView.findViewById(R.id.titlebar);
    }

    public abstract int getContentView();

    public void setTitle(String title) {
        titleBar.setTitle(title);
    }

    private void setView(View view) {
        ViewGroup.LayoutParams LAYOUT_PARAMS = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.removeAllViews();
        container.addView(view, LAYOUT_PARAMS);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
