package com.familyplan.ihealth.fragment;

import android.support.v4.view.ViewPager;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.adapter.HomePageAdapter;
import com.familyplan.ihealth.api.Api;
import com.familyplan.ihealth.model.ArticleCategory;
import com.familyplan.ihealth.net.NSCallback;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by LSD on 16/7/23.
 */
public class FragmentHome extends NativeFragment {
    @BindView(R.id.hm_tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.hm_viewpage)
    ViewPager viewpage;

    @Override
    public int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("首页");
        setTitleBarDisable();

        initView();
        loadData();
    }

    private void initView() {
        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                resetTabStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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
    }

    public void loadData() {
        Api.article_category(new NSCallback<ArticleCategory>(mContext, ArticleCategory.class) {
            @Override
            public void onSuccess(List<ArticleCategory> t, int total) {
                viewpage.setAdapter(new HomePageAdapter(mContext.getSupportFragmentManager(), t));
                tablayout.setViewPager(viewpage);

                resetTabStatus(0);
            }
        });
    }

    private void resetTabStatus(int position) {
        for (int i = 0; i < viewpage.getChildCount(); i++) {
            tablayout.getTitleView(i).setTextSize(15);
        }
        tablayout.getTitleView(position).setTextSize(16);
    }
}
