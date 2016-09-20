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
public class FragmentRecipeList extends NativeFragment {
    @BindView(R.id.rp_public_list_framelayout)
    PtrClassicFrameLayout listFramelayout;
    @BindView(R.id.rp_public_listview)
    AutoLoadListView listview;

    RecipeListAdapter adapter;
    int size = 10;
    int type;

    public static Fragment getInstance(int type) {
        FragmentRecipeList recipeList = new FragmentRecipeList();
        Bundle args = new Bundle();
        args.putInt("type", type);
        recipeList.setArguments(args);
        return recipeList;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_recipelist;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitleBarDisable();

        type = getArguments().getInt("type");

        initView();
        loadData();
    }

    public void initView() {
        listFramelayout.setLastUpdateTimeRelateObject(this);
        listFramelayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        listview.setAdapter(adapter = new RecipeListAdapter(mContext));
        listview.setOnLoadListener(new AutoLoadListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadData();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe recipe = (Recipe) adapter.getItem(position);
                startActivity(new Intent(mContext, RecipeDetailsActivity.class).putExtra("recipe_id", recipe.getId()));
            }
        });
    }

    public void loadData() {
        Api.getRecipeList(type, new NSCallback<Recipe>(mContext, Recipe.class) {
            @Override
            public void onSuccess(List<Recipe> t, int total) {
                listFramelayout.refreshComplete();
                listview.onLoadMoreComplete();

                if (total > 0)
                    adapter.setData(t);

                if (total < size) {
                    listview.setNoMoreData(true);
                }

            }
        });
    }
}
