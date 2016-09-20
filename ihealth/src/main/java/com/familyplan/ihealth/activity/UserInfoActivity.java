package com.familyplan.ihealth.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.familyplan.ihealth.IApplication;
import com.familyplan.ihealth.R;
import com.familyplan.ihealth.api.Api;
import com.familyplan.ihealth.api.QiNiu;
import com.familyplan.ihealth.event.EditUserEvent;
import com.familyplan.ihealth.event.GallerySelectEvent;
import com.familyplan.ihealth.model.QiniuInfo;
import com.familyplan.ihealth.model.SuccessComm;
import com.familyplan.ihealth.model.User;
import com.familyplan.ihealth.net.NSCallback;
import com.familyplan.ihealth.utils.GalleryManager;
import com.familyplan.ihealth.utils.GallerySelecter;
import com.lib.utils.AppTips;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LSD on 16/8/6.
 */
public class UserInfoActivity extends CommonActivity {
    @BindView(R.id.iv_avstart)
    ImageView ivAvstart;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_sign)
    TextView tvSign;

    User user;
    boolean needSave = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("个人中心");
        EventBus.getDefault().register(this);

        user = IApplication.getInstance().getUser();
        setData();
    }

    public void setData() {
        tvNickname.setText(user.getNick_name());
        tvSign.setText(user.getSign());
        if (!TextUtils.isEmpty(user.getAvstart())) {
            Picasso.with(mActivity).load(user.getAvstart()).error(R.drawable.ic_defealt_av).into(ivAvstart);
        }
        tvAge.setText(user.getAge() + "");
        if (2 == user.getSex()) {
            tvSex.setText("女");
        } else {
            tvSex.setText("男");
        }
        tvHeight.setText(user.getHeight() + "");
        tvWeight.setText(user.getWeight() + "");
        tvSign.setText(user.getSign());
    }

    public void doUploadMedia(String localPath) {
        String key = "ic_avstart" + System.currentTimeMillis() + ".jpg";
        File file = new File(localPath);
        final String mediaKey = key;
        final File imgFile = file;
        QiNiu.getToken(mediaKey, new NSCallback<QiniuInfo>(mActivity, QiniuInfo.class) {
            @Override
            public void onSuccess(QiniuInfo info) {
                String token = info.getToken();
                QiNiu.doUpload(mediaKey, token, imgFile, new QiNiu.UpLoadListener() {
                    @Override
                    public void onSuccess(String url) {
                        Picasso.with(mActivity).load(url).placeholder(R.drawable.ic_defealt_av).error(R.drawable.ic_defealt_av).into(ivAvstart);
                        needSave = true;
                        setRightTxt("保存");
                        user.setAvstart(url);
                    }

                    @Override
                    public void onFail(String error) {
                    }

                    @Override
                    public void onProgress(double percent) {
                    }
                });

            }
        });
    }

    @OnClick(R.id.iv_avstart)
    public void avstart() {
        GallerySelecter.start(mActivity, true);
    }

    @OnClick(R.id.layout_unick)
    public void nickname() {
        startActivity(new Intent(mActivity, EditUserInfoActivity.class).putExtra("type", 1).putExtra("user", user));
    }

    @OnClick(R.id.layout_usex)
    public void sex() {
        startActivity(new Intent(mActivity, EditUserInfoActivity.class).putExtra("type", 2).putExtra("user", user));
    }

    @OnClick(R.id.layout_uage)
    public void age() {
        startActivity(new Intent(mActivity, EditUserInfoActivity.class).putExtra("type", 3).putExtra("user", user));
    }

    @OnClick(R.id.layout_uheight)
    public void height() {
        startActivity(new Intent(mActivity, EditUserInfoActivity.class).putExtra("type", 4).putExtra("user", user));
    }

    @OnClick(R.id.layout_uweight)
    public void weight() {
        startActivity(new Intent(mActivity, EditUserInfoActivity.class).putExtra("type", 5).putExtra("user", user));
    }

    @OnClick(R.id.layout_usign)
    public void sign() {
        startActivity(new Intent(mActivity, EditUserInfoActivity.class).putExtra("type", 6).putExtra("user", user));
    }

    public void confirExit() {
        if (needSave) {
            new AlertDialog.Builder(mActivity)
                    .setTitle("提醒")
                    .setMessage("您还未保存,是否退出?")
                    .setPositiveButton("确定", new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        } else {
            super.onBackClick();
        }
    }

    public void save() {
        Api.update_userinfo(user, new NSCallback.NSTokenCallback<SuccessComm>(mActivity, SuccessComm.class, true, "更新中…") {
            @Override
            public void onSuccess(SuccessComm comm) {
                AppTips.showToast("更新成功");
                needSave = false;
            }
        });
    }

    //选择媒体文件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGallerySelectEvent(final GallerySelectEvent event) {
        String localImagesPath = event.paths.get(0);
        doUploadMedia(localImagesPath);
    }

    //编辑用户信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEditUserEvent(final EditUserEvent event) {
        setRightTxt("保存");
        needSave = true;
        user = event.user;
        setData();
    }

    @Override
    public void onBackPressed() {
        confirExit();
    }

    @Override
    public void onBackClick() {
        confirExit();
    }

    @Override
    public void onRightClick() {
        save();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
