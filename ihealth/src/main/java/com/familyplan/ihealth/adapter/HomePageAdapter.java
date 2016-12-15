package com.familyplan.ihealth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.familyplan.ihealth.fragment.FragmentHomeList;
import com.familyplan.ihealth.fragment.FragmentHomeNvList;
import com.familyplan.ihealth.model.ArticleCategory;

import java.util.List;

/**
 * Created by LSD on 16/6/2.
 */
public class HomePageAdapter extends FragmentPagerAdapter {
    List<ArticleCategory> list;

    public HomePageAdapter(FragmentManager fm, List<ArticleCategory> list) {
        super(fm);
        this.list = list;
    }

    public void setData(List<ArticleCategory> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        ArticleCategory category = list.get(position);
        return FragmentHomeList.getInstance(category.getId(),category.getTag());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        ArticleCategory category = list.get(position);
        return category.getTitle();
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }
}
