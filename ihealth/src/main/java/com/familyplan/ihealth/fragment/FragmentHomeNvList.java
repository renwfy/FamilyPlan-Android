package com.familyplan.ihealth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.activity.ArticleDetailsActivity;
import com.familyplan.ihealth.adapter.HomeListAdapter;
import com.familyplan.ihealth.api.Api;
import com.familyplan.ihealth.model.Article;
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
public class FragmentHomeNvList extends NativeFragment {
    @BindView(R.id.hm_list_framelayout)
    PtrClassicFrameLayout listFramelayout;
    @BindView(R.id.hm_list_listview)
    AutoLoadListView listview;

    HomeListAdapter adapter;
    int size = 10;
    int page = 1;
    int cat_id;
    String cat_tag;


    public static Fragment getInstance(int cat_id, String cat_tag) {
        FragmentHomeNvList publicFragment = new FragmentHomeNvList();
        Bundle args = new Bundle();
        args.putInt("cat_id", cat_id);
        args.putString("cat_tag", cat_tag);
        publicFragment.setArguments(args);
        return publicFragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_homelist;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitleBarDisable();

        cat_id = getArguments().getInt("cat_id");
        cat_tag = getArguments().getString("cat_tag");

        initView();
        loadData();
    }

    public void initView() {
        listFramelayout.setLastUpdateTimeRelateObject(this);
        listFramelayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
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
                page = page + 1;
                loadData();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = (Article) adapter.getItem(position);
                startActivity(new Intent(mContext, ArticleDetailsActivity.class).putExtra("article_id", article.getId()));
            }
        });
        listview.setAdapter(adapter = new HomeListAdapter(mContext));
    }

    public void loadData() {
        int start = (page - 1) * size;
        Api.article_list(start, size, cat_tag, new NSCallback<Article>(mContext, Article.class) {
            @Override
            public void onSuccess(List<Article> t, int total) {
                listFramelayout.refreshComplete();
                listview.onLoadMoreComplete();

                if (page == 1) {
                    adapter.setData(t);
                } else {
                    adapter.addData(t);
                }

                if (total < size) {
                    listview.setNoMoreData(true);
                }
            }
        });
    }
}
