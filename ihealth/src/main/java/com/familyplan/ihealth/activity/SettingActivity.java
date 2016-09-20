package com.familyplan.ihealth.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.familyplan.ihealth.IApplication;
import com.familyplan.ihealth.R;
import com.familyplan.ihealth.adapter.FragmentMyAdapter;
import com.familyplan.ihealth.adapter.SettingAdapter;
import com.lib.utils.AppTips;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LSD on 16/8/7.
 */
public class SettingActivity extends CommonActivity {
    @BindView(R.id.listview)
    ListView listview;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("设置");

        listview.addFooterView(new Footer().footer);
        listview.setAdapter(new SettingAdapter(mActivity, loadData()));
    }

    private List<String> loadData() {
        List<String> list = new ArrayList<>();
        list.add("关于我们");
        list.add("联系我们");
        list.add("推荐APP给好友");
        return list;
    }

    public class Footer {
        public View footer;

        public Footer() {
            footer = LayoutInflater.from(mActivity).inflate(R.layout.view_setting_footer, null);
            ButterKnife.bind(this, footer);
        }

        @OnClick(R.id.tv_logout)
        public void logout() {
            AppTips.showToast("您已退出登录");
            IApplication.getInstance().logout(mActivity);
        }
    }
}
