package com.familyplan.ihealth.fragment;

import android.support.v4.view.ViewPager;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.model.TabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by LSD on 16/7/23.
 */
public class FragmentRecipe extends NativeFragment {
    @BindView(R.id.tablayout)
    CommonTabLayout tablayout;
    @BindView(R.id.viewpage)
    ViewPager viewpage;

    private String[] mTitles = {"大众食谱", "营养师推荐"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.fragment_recipe;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("营养食谱");

        initView();
    }

    private void initView() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        tablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpage.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tablayout.setTabData(mTabEntities);
    }

}