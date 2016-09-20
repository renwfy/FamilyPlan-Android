package com.familyplan.ihealth.activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.api.QiNiu;
import com.familyplan.ihealth.event.CreateDescriptEvent;
import com.familyplan.ihealth.event.DescriptSelectEvent;
import com.familyplan.ihealth.model.Descript;
import com.familyplan.ihealth.model.QiniuInfo;
import com.familyplan.ihealth.net.NSCallback;
import com.familyplan.ihealth.utils.GalleryManager;
import com.familyplan.ihealth.utils.GallerySelecter;
import com.lib.utils.AppTips;
import com.lib.utils.DensityUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LSD on 16/8/9.
 */
public class PublishDescriptActivity extends CommonActivity {
    @BindView(R.id.iv_despic)
    ImageView ivDespic;
    @BindView(R.id.tv_despic_tips)
    TextView tvDespicTips;
    @BindView(R.id.tv_despic_change)
    TextView tvDespicChange;
    @BindView(R.id.rl_despic)
    RelativeLayout rlDespic;
    @BindView(R.id.et_decript)
    EditText etDecript;

    String imgUrl;

    @Override
    protected int getContentView() {
        return R.layout.activity_publishdescript;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        EventBus.getDefault().register(this);
        setTitle("添加步骤");
        setRightTxt("保存");
    }

    @Override
    public void onRightClick() {
        String desctipt = etDecript.getText().toString();
        if(TextUtils.isEmpty(imgUrl)){
            AppTips.showToast("请上传图片");
            return;
        }
        if(TextUtils.isEmpty(desctipt)){
            AppTips.showToast("请输入描述");
            return;
        }
        Descript descript = new Descript();
        descript.setImg(imgUrl);
        descript.setText(desctipt);
        EventBus.getDefault().post(new CreateDescriptEvent(descript));
        finish();
    }

    public void doUploadMedia(String localPath) {
        String key = "ic_despic" + System.currentTimeMillis() + ".jpg";
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
                        loadImage(url);
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

    public void loadImage(String url) {
        imgUrl = url;
        Picasso.with(mActivity).load(url).into(ivDespic, new Callback.EmptyCallback() {
            @Override
            public void onSuccess() {
                tvDespicTips.setVisibility(View.GONE);
                tvDespicChange.setVisibility(View.VISIBLE);

                int imageW = ivDespic.getDrawable().getIntrinsicWidth();
                int imageH = ivDespic.getDrawable().getIntrinsicHeight();
                final int screenW = DensityUtil.screenWidthInPx(mActivity);
                int H = screenW * imageH / imageW;

                //imageview
                RelativeLayout.LayoutParams ivParams = (RelativeLayout.LayoutParams) ivDespic.getLayoutParams();
                ivParams.width = screenW;
                ivParams.height = H;
                ivDespic.setLayoutParams(ivParams);

                //relativielayout
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rlDespic.getLayoutParams();
                params.width = screenW;
                params.height = H;
                rlDespic.setLayoutParams(params);
            }
        });
    }

    @OnClick({R.id.tv_despic_tips, R.id.tv_despic_change})
    public void getPlayBill() {
        GallerySelecter.start(mActivity, true);
    }

    // 隐藏输入法
    private void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    //选择媒体文件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDescriptSelectEvent(final DescriptSelectEvent event) {
        String localImagesPath = event.paths.get(0);
        doUploadMedia(localImagesPath);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissKeyboard();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
