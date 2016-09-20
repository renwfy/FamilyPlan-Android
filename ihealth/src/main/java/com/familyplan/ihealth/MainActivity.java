package com.familyplan.ihealth;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost;

import com.familyplan.ihealth.activity.AbstractActivity;
import com.familyplan.ihealth.fragment.FragmentDetect;
import com.familyplan.ihealth.fragment.FragmentHome;
import com.familyplan.ihealth.fragment.FragmentMy;
import com.familyplan.ihealth.fragment.FragmentRecipe;
import com.familyplan.ihealth.view.TabMenuView;

public class MainActivity extends AbstractActivity {
    FragmentTabHost mTabHost;
    //tab数据
    private TabMenuView[] tabs = new TabMenuView[4];
    private String mTextviewArray[] = {"首页", "定制减脂", "减脂食谱", "我"};
    private int mImageViewArray[] = {R.drawable.ic_tab_home, R.drawable.ic_tab_detect, R.drawable.ic_tab_recipe, R.drawable.ic_tab_my};
    private Class fragmentArray[] = {FragmentHome.class, FragmentDetect.class,  FragmentRecipe.class ,FragmentMy.class};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    void initView() {
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.frame_container);
        int count = tabs.length;
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white));
            //分隔线
            mTabHost.getTabWidget().setDividerDrawable(null);
        }
    }

    private View getTabItemView(int index) {
        tabs[index] = new TabMenuView(this);
        tabs[index].render(mTextviewArray[index], mImageViewArray[index]);
        return tabs[index].getView();
    }
}
