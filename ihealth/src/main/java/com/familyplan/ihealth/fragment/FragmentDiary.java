package com.familyplan.ihealth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.activity.RecipeDetailsActivity;
import com.familyplan.ihealth.adapter.RecipeListAdapter;
import com.familyplan.ihealth.api.Api;
import com.familyplan.ihealth.model.Recipe;
import com.familyplan.ihealth.net.NSCallback;
import com.familyplan.ihealth.view.AutoLoadListView;

import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by LSD on 16/7/29.
 */
public class FragmentDiary extends NativeFragment {

    public static Fragment getInstance(int type) {
        FragmentDiary recipeList = new FragmentDiary();
        Bundle args = new Bundle();
        args.putInt("type", type);
        recipeList.setArguments(args);
        return recipeList;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_diary;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitleBarDisable();

        initView();
        loadData();
    }

    public void initView() {
    }

    public void loadData() {
    }
}
