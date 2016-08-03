package com.familyplan.ihealth.fragment;

import android.support.v4.app.Fragment;

import com.familyplan.ihealth.R;

/**
 * Created by LSD on 16/7/29.
 */
public class FragmentReciperMaster extends NativeFragment {
    public static Fragment getInstance() {
        return new FragmentReciperMaster();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_recipemaster;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitleBarDisable();
    }
}
