package com.familyplan.ihealth.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.view.titlebar.TitleBar;
import com.familyplan.ihealth.view.titlebar.TitleBarMenuItem;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LSD on 16/3/2.
 */
public abstract class CommonActivity extends AbstractActivity implements TitleBar.OnTitleBarClickListener{
    public TitleBar titleBar;
    public LinearLayout container;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getFrameLayout());

        initFrameLayout();
        int viewId = getContentView();
        if(0 != viewId){
            View view = View.inflate(mActivity,viewId,null);
            setView(view);
        }
        unbinder = ButterKnife.bind(this);
        onViewCreated();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onLeftClick() {
    }

    @Override
    public void onRightClick() {
    }

    protected int getFrameLayout(){
        return R.layout.activity_common;
    }
    protected abstract int getContentView();
    protected void onViewCreated(){}

    private void initFrameLayout(){
        container = (LinearLayout) findViewById(R.id.container);
        titleBar = (TitleBar) findViewById(R.id.titlebar);
        titleBar.setOnTitleBarClickListener(this);
    }
    public void setTitle(String title){
        titleBar.setTitle(title);
    }
    public void setTitleBarDisable(){
        titleBar.setVisibility(View.GONE);
    }
    public void setRightTxt(String rightTxt){
        TitleBarMenuItem item = new TitleBarMenuItem();
        item.setTitle(rightTxt);
        titleBar.setRightMenu(item);
    }
    public void setRightButton(String icon){
        TitleBarMenuItem item = new TitleBarMenuItem();
        item.setIcon(icon);
        titleBar.setRightMenu(item);
    }
    protected void setView(View view){
        ViewGroup.LayoutParams LAYOUT_PARAMS = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.removeAllViews();
        container.addView(view, LAYOUT_PARAMS);
    }
}
