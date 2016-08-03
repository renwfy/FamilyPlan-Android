package com.familyplan.ihealth.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.familyplan.ihealth.R;
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
public class FragmentRecipe extends NativeFragment {
    @BindView(R.id.tablayout)
    CommonTabLayout tablayout;
    @BindView(R.id.viewpage)
    ViewPager viewpage;

    private String[] mTitles = {"大众食谱", "营养师推荐"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.fragment_recipe;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("营养食谱");
        setTitleBarDisable();

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
                resetTabStatus(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        tablayout.setTabData(mTabEntities);

        mFragments.add(FragmentRecipePublic.getInstance());
        mFragments.add(FragmentReciperMaster.getInstance());
        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tablayout.setCurrentTab(position);
                resetTabStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewpage.setAdapter(new RecipePageAdapter(mContext.getSupportFragmentManager(),mFragments));

        //初始化
        tablayout.setCurrentTab(0);
        resetTabStatus(0);
    }

    private void resetTabStatus(int position){
        for (int i = 0; i < mTitles.length; i++) {
            tablayout.getTitleView(i).setTextSize(17);
        }
        tablayout.getTitleView(position).setTextSize(19);
    }
}