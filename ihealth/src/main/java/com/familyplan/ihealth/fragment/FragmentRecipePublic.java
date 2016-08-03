package com.familyplan.ihealth.fragment;

import android.content.Intent;
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
public class FragmentRecipePublic extends NativeFragment {

    @BindView(R.id.listview)
    AutoLoadListView listview;
    @BindView(R.id.list_framelayout)
    PtrClassicFrameLayout listFramelayout;

    RecipeListAdapter adapter;
    int size = 10;

    public static Fragment getInstance() {
        return new FragmentRecipePublic();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_recipepublic;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitleBarDisable();

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
                startActivity(new Intent(mContext, RecipeDetailsActivity.class).putExtra("recipe_id",recipe.getId()));
            }
        });
        listview.setAdapter(adapter = new RecipeListAdapter(mContext));
    }

    public void loadData() {
        Api.getRecipeList(new NSCallback<Recipe>(mContext, Recipe.class) {
            @Override
            public void onSuccess(List<Recipe> t, int total) {
                listFramelayout.refreshComplete();
                listview.onLoadMoreComplete();

                t.add(t.get(0));
                t.add(t.get(0));
                adapter.setData(t);

                if (total < size) {
                    listview.setNoMoreData(true);
                }

            }
        });
    }
}
