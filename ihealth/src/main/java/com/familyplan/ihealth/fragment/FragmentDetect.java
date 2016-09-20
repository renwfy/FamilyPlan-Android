package com.familyplan.ihealth.fragment;

import android.support.v4.view.ViewPager;
import com.familyplan.ihealth.R;
import com.familyplan.ihealth.adapter.DetectPageAdapter;
import com.familyplan.ihealth.adapter.RecipePageAdapter;
import com.familyplan.ihealth.model.TabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by LSD on 16/7/23.
 */
public class FragmentDetect extends NativeFragment {
    @BindView(R.id.dt_tablayout)
    CommonTabLayout dtTablayout;
    @BindView(R.id.dt_viewpage)
    ViewPager dtViewpage;

    private String[] mTitles = {"智能体检", "减肥日记"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.fragment_detect;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("智能体检");
        setTitleBarDisable();

        initView();
    }

    private void initView() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        dtTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                dtViewpage.setCurrentItem(position, false);
                resetTabStatus(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        dtTablayout.setTabData(mTabEntities);

        dtViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                dtTablayout.setCurrentTab(position);
                resetTabStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        dtViewpage.setAdapter(new DetectPageAdapter(mContext.getSupportFragmentManager()));

        //初始化
        resetTabStatus(0);
    }

    private void resetTabStatus(int position) {
        for (int i = 0; i < mTitles.length; i++) {
            dtTablayout.getTitleView(i).setTextSize(15);
        }
        dtTablayout.getTitleView(position).setTextSize(16);
    }

}
