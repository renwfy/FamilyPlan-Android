package com.familyplan.ihealth.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.familyplan.ihealth.IApplication;
import com.familyplan.ihealth.R;
import com.familyplan.ihealth.activity.FindPassActivity;
import com.familyplan.ihealth.activity.LoginActivity;
import com.familyplan.ihealth.activity.PublishRecipeActivity;
import com.familyplan.ihealth.activity.SettingActivity;
import com.familyplan.ihealth.activity.UserInfoActivity;
import com.familyplan.ihealth.adapter.FragmentMyAdapter;
import com.familyplan.ihealth.api.Api;
import com.familyplan.ihealth.model.User;
import com.familyplan.ihealth.net.NSCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by LSD on 16/7/23.
 */
public class FragmentMy extends NativeFragment {
    @BindView(R.id.listview)
    ListView listview;

    MyHeader myHeader;

    @Override
    public int getContentView() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("我");

        listview.addHeaderView((myHeader = new MyHeader()).header);
        listview.setAdapter(new FragmentMyAdapter(mContext, loadData()));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    IApplication.startActivityWithAuth(mContext,UserInfoActivity.class);
                }
                if (1 == position) {
                    IApplication.startActivityWithAuth(mContext,UserInfoActivity.class);
                }
                if (2 == position) {
                    IApplication.startActivityWithAuth(mContext,new Intent(mContext, FindPassActivity.class).putExtra("type", 2));
                }
                if (3 == position) {
                    IApplication.startActivityWithAuth(mContext,SettingActivity.class);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refrshUserInfo();
    }

    public void refrshUserInfo() {
        if (IApplication.isLogin()) {
            Api.userinfo(new NSCallback.NSTokenCallback<User>(mContext, User.class) {
                @Override
                public void onSuccess(User user) {
                    IApplication.getInstance().saveUser(user);
                    myHeader.setData();
                }
            });
        } else {
            myHeader.setData();
        }
    }

    private List<String> loadData() {
        List<String> list = new ArrayList<>();
        list.add("个人资料");
        list.add("修改密码");
        list.add("设置");
        list.add("帮助与反馈");
        list.add("推荐APP给好友");
        return list;
    }

    public class MyHeader {
        public View header;
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.tv_sign)
        TextView tvSign;
        @BindView(R.id.iv_avstart)
        CircleImageView ivAvstart;

        public MyHeader() {
            header = LayoutInflater.from(mContext).inflate(R.layout.view_my_header, null);
            ButterKnife.bind(this, header);
        }

        public void setData() {
            User user = IApplication.getInstance().getUser();
            if(user != null){
                tvNickname.setText(user.getNick_name());
                tvSign.setText(user.getSign());
                if (!TextUtils.isEmpty(user.getAvstart()))
                    Picasso.with(mContext).load(user.getAvstart()).placeholder(R.drawable.ic_defealt_av).error(R.drawable.ic_defealt_av).into(ivAvstart);
            }else{
                tvNickname.setText("^^^");
                tvSign.setText("……");
                ivAvstart.setImageResource(R.drawable.ic_defealt_av);
            }
        }

        @OnClick(R.id.tv_pubulish)
        public void publish() {
            IApplication.startActivityWithAuth(mContext,PublishRecipeActivity.class);
        }

        @OnClick(R.id.tv_haspublish)
        public void haspublish() {

        }

        @OnClick(R.id.tv_hascollect)
        public void hascollect() {

        }
    }
}
