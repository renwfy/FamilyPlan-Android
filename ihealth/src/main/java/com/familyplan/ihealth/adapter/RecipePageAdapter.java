package com.familyplan.ihealth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.familyplan.ihealth.fragment.FragmentRecipeList;

/**
 * Created by LSD on 16/6/2.
 */
public class RecipePageAdapter extends FragmentPagerAdapter {
    public RecipePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (0 == position) {
            return FragmentRecipeList.getInstance(1);
        }
        if (1 == position) {
            return FragmentRecipeList.getInstance(2);
        }
        return null;
    }


    @Override
    public int getCount() {
        return 2;
    }
}
