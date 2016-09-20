package com.familyplan.ihealth.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.api.Api;
import com.familyplan.ihealth.api.QiNiu;
import com.familyplan.ihealth.event.CreateDescriptEvent;
import com.familyplan.ihealth.event.CreateMaterialEvent;
import com.familyplan.ihealth.event.EditDescriptEvent;
import com.familyplan.ihealth.event.EditUserEvent;
import com.familyplan.ihealth.event.GallerySelectEvent;
import com.familyplan.ihealth.model.Descript;
import com.familyplan.ihealth.model.Material;
import com.familyplan.ihealth.model.QiniuInfo;
import com.familyplan.ihealth.model.SuccessComm;
import com.familyplan.ihealth.net.NSCallback;
import com.familyplan.ihealth.utils.GalleryManager;
import com.familyplan.ihealth.utils.GallerySelecter;
import com.google.gson.Gson;
import com.lib.utils.AppTips;
import com.lib.utils.DensityUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LSD on 16/8/8.
 */
public class PublishRecipeActivity extends CommonActivity {
    @BindView(R.id.iv_playbill)
    ImageView ivPlaybill;
    @BindView(R.id.tv_playbill_tips)
    TextView tvPlaybillTips;
    @BindView(R.id.tv_playbill_change)
    TextView tvPlaybillChange;
    @BindView(R.id.rl_playbill)
    RelativeLayout rlPlaybill;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_brief)
    EditText etBrief;
    @BindView(R.id.layout_material)
    LinearLayout layoutMaterial;
    @BindView(R.id.ll_add_material)
    LinearLayout llAddMaterial;
    @BindView(R.id.layout_descript)
    LinearLayout layoutDescript;
    @BindView(R.id.dir_mb)
    View dirMb;
    @BindView(R.id.layout_manager_descript)
    LinearLayout layoutManagerDescript;
    @BindView(R.id.et_tips)
    EditText etTips;
    @BindView(R.id.bt_publis)
    Button btPublis;

    String playbill;
    List<Material> materialList = new ArrayList<>();
    List<Descript> descriptList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_publishrecipe;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("发布食谱");
        EventBus.getDefault().register(this);
    }

    public void loadPlayBill(String url) {
        playbill = url;
        Picasso.with(mActivity).load(url).into(ivPlaybill, new Callback.EmptyCallback() {
            @Override
            public void onSuccess() {
                tvPlaybillTips.setVisibility(View.GONE);
                tvPlaybillChange.setVisibility(View.VISIBLE);

                int imageW = ivPlaybill.getDrawable().getIntrinsicWidth();
                int imageH = ivPlaybill.getDrawable().getIntrinsicHeight();
                final int screenW = DensityUtil.screenWidthInPx(mActivity);
                int H = screenW * imageH / imageW;

                //imageview
                RelativeLayout.LayoutParams ivParams = (RelativeLayout.LayoutParams) ivPlaybill.getLayoutParams();
                ivParams.width = screenW;
                ivParams.height = H;
                ivPlaybill.setLayoutParams(ivParams);

                //relativielayout
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rlPlaybill.getLayoutParams();
                params.width = screenW;
                params.height = H;
                rlPlaybill.setLayoutParams(params);
            }
        });
    }

    /***
     * 材料
     *
     * @param materials
     */
    public void addMaterialView(List<Material> materials) {
        if (materials.size() <= 0) {
            return;
        }
        dirMb.setVisibility(View.GONE);//隐藏中间间隔
        if (layoutMaterial.getChildCount() > 0) {
            layoutMaterial.removeAllViews();
        }
        for (Material material : materials) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.view_material_item, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_measure = (TextView) view.findViewById(R.id.tv_measure);

            tv_name.setText(material.getName());
            tv_measure.setText(material.getMeasure() + material.getUnit());
            layoutMaterial.addView(view);
        }
    }


    /**
     * 步骤
     *
     * @param descripts
     */
    public void addDescriptView(List<Descript> descripts) {
        if (descripts.size() <= 0) {
            return;
        }
        layoutManagerDescript.setVisibility(View.VISIBLE);
        if (layoutDescript.getChildCount() > 0) {
            layoutDescript.removeAllViews();
        }
        for (int i = 0; i < descripts.size(); i++) {
            Descript descript = descripts.get(i);
            View view = LayoutInflater.from(mActivity).inflate(R.layout.view_descript_item, null);
            final RelativeLayout rl_descript = (RelativeLayout) view.findViewById(R.id.rl_descript);
            final ImageView imageView = (ImageView) view.findViewById(R.id.iv_playbill);
            TextView tv_text = (TextView) view.findViewById(R.id.tv_text);

            Picasso.with(mActivity).load(descript.getImg()).into(imageView, new Callback.EmptyCallback() {
                @Override
                public void onSuccess() {
                    final int screenW = DensityUtil.screenWidthInPx(mActivity) - DensityUtil.dip2px(mActivity, 10);
                    int imageW = imageView.getDrawable().getIntrinsicWidth();
                    int imageH = imageView.getDrawable().getIntrinsicHeight();
                    int H = screenW * imageH / imageW;

                    //布局处理
                    LinearLayout.LayoutParams layparams = (LinearLayout.LayoutParams) rl_descript.getLayoutParams();
                    layparams.height = H;
                    rl_descript.setLayoutParams(layparams);

                    //图片处理
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                    params.width = screenW;
                    params.height = H;
                    imageView.setLayoutParams(params);
                }
            });
            tv_text.setText((i + 1) + "、" + descript.getText());
            layoutDescript.addView(view);
        }
    }

    public void doUploadMedia(String localPath) {
        String key = "ic_playbill" + System.currentTimeMillis() + ".jpg";
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
                        loadPlayBill(url);
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

    @OnClick({R.id.tv_playbill_tips, R.id.tv_playbill_change})
    public void getPlayBill() {
        GallerySelecter.start(mActivity, true);
    }

    @OnClick(R.id.ll_add_material)
    public void addMaterial() {
        startActivity(new Intent(mActivity, PublishMaterialActivity.class).putExtra("material", (Serializable) materialList));
    }

    @OnClick({R.id.ll_add_descript, R.id.tv_add_descript})
    public void addDescript() {
        startActivity(new Intent(mActivity, PublishDescriptActivity.class));
    }

    @OnClick(R.id.tv_edit_descript)
    public void editDescript() {
        startActivity(new Intent(mActivity, PublishDescriptEditActivity.class).putExtra("descript", (Serializable) descriptList));
    }

    @OnClick(R.id.bt_publis)
    public void publish() {
        String title = etTitle.getText().toString().trim();
        String brief = etBrief.getText().toString().trim();
        String tips = etTips.getText().toString().trim();
        if (TextUtils.isEmpty(playbill)) {
            AppTips.showToast("请上传封面");
            return;
        }
        if (TextUtils.isEmpty(brief)) {
            AppTips.showToast("请输入名称");
            return;
        }
        if (TextUtils.isEmpty(brief)) {
            AppTips.showToast("请输入简介");
            return;
        }
        if (TextUtils.isEmpty(brief)) {
            AppTips.showToast("请输入简介");
            return;
        }
        if (materialList == null) {
            AppTips.showToast("请添加食材");
            return;
        }
        if (descriptList == null) {
            AppTips.showToast("请添加步骤");
            return;
        }
        String materialJson = new Gson().toJson(materialList);
        String desctiptJson = new Gson().toJson(descriptList);

        Api.recipe_create(playbill, title, brief, materialJson, desctiptJson, tips, new NSCallback.NSTokenCallback<SuccessComm>(mActivity, SuccessComm.class, true, "正在发布……") {
            @Override
            public void onSuccess(SuccessComm comm) {
                AppTips.showToast("发布成功");
                finish();
            }
        });
    }

    // 隐藏输入法
    private void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    //选择媒体文件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGallerySelectEvent(final GallerySelectEvent event) {
        String localImagesPath = event.paths.get(0);
        doUploadMedia(localImagesPath);
    }

    //添加食材
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCreateMaterialEvent(final CreateMaterialEvent event) {
        materialList = event.materialList;
        addMaterialView(materialList);
    }

    //添加步骤
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCreateDescriptEvent(final CreateDescriptEvent event) {
        Descript descript = event.descript;
        if (descript != null) {
            descriptList.add(descript);
            addDescriptView(descriptList);
        }
    }

    //管理步骤
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEditDescriptEvent(final EditDescriptEvent event) {
        descriptList = event.descriptList;
        addDescriptView(descriptList);
    }

    @Override
    protected void onPause() {
        dismissKeyboard();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
