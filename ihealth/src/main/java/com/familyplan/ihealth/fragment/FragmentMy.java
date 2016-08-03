package com.familyplan.ihealth.fragment;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.activity.AbstractActivity;
import com.familyplan.ihealth.utils.GalleryManager;

import butterknife.OnClick;

/**
 * Created by LSD on 16/7/23.
 */
public class FragmentMy extends NativeFragment {
    @Override
    public int getContentView() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("我");
    }

    @OnClick(R.id.tv_go)
    public void go() {
        GalleryManager.startFromAlbumSinglePick((AbstractActivity) mContext,true);
    }
}
