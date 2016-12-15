package com.familyplan.ihealth.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.familyplan.ihealth.Constants;

/**
 * Created by LSD on 2016/10/9.
 */

public class FragmentHomeList extends WebFragment {
    int cat_id;
    String cat_tag;

    public static Fragment getInstance(int cat_id, String cat_tag) {
        FragmentHomeList homeListFragment = new FragmentHomeList();
        Bundle args = new Bundle();
        args.putInt("cat_id", cat_id);
        args.putString("cat_tag", cat_tag);
        homeListFragment.setArguments(args);
        return homeListFragment;
    }

    @Override
    protected void onViewCreated() {
        cat_id = getArguments().getInt("cat_id");
        cat_tag = getArguments().getString("cat_tag");
        super.onViewCreated();
        setTitleBarDisable();
    }

    @Override
    public void onResume() {
        //不调用onResume刷新
        canRefresh = false;
        super.onResume();
    }

    @Override
    public String getUrl() {
        return Constants.WEB_HOST+"/article/list/"+cat_tag;
    }
}
