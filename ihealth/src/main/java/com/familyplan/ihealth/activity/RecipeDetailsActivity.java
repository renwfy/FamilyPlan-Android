package com.familyplan.ihealth.activity;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.api.Api;
import com.familyplan.ihealth.model.Descript;
import com.familyplan.ihealth.model.Material;
import com.familyplan.ihealth.model.RecipeDetails;
import com.familyplan.ihealth.model.SuccessComm;
import com.familyplan.ihealth.net.NSCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lib.utils.AppTips;
import com.lib.utils.DensityUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LSD on 16/7/31.
 */
public class RecipeDetailsActivity extends CommonActivity {

    @BindView(R.id.iv_playbill)
    ImageView ivPlaybill;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_avstart)
    ImageView ivAvstart;
    @BindView(R.id.tv_uname)
    TextView tvUname;
    @BindView(R.id.tv_likeinfo)
    TextView tvLikeinfo;
    @BindView(R.id.tv_brief)
    TextView tvBrief;
    @BindView(R.id.layout_material)
    LinearLayout layoutMaterial;
    @BindView(R.id.layout_details)
    LinearLayout layoutDetails;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.layout_tips)
    LinearLayout layoutTips;
    @BindView(R.id.tv_nice)
    TextView tvNice;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_share)
    TextView tvShare;

    RecipeDetails details;
    boolean isCollect = false;
    boolean isNice = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_recipedetails;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("详情");

        loadData();
    }

    public void loadData() {
        final int recipe_id = getIntent().getIntExtra("recipe_id", 0);
        Api.getRecipeDetails(recipe_id, new NSCallback<RecipeDetails>(mActivity, RecipeDetails.class) {
            @Override
            public void onSuccess(RecipeDetails recipe) {
                details = recipe;
                Picasso.with(mActivity).load(recipe.getPlaybill()).into(ivPlaybill, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        int imageW = ivPlaybill.getDrawable().getIntrinsicWidth();
                        int imageH = ivPlaybill.getDrawable().getIntrinsicHeight();
                        final int screenW = DensityUtil.screenWidthInPx(mActivity);

                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivPlaybill.getLayoutParams();
                        int H = screenW * imageH / imageW;
                        params.width = screenW;
                        params.height = H;
                        ivPlaybill.setLayoutParams(params);
                    }
                });

                setTitle(recipe.getTitle());
                tvTitle.setText(recipe.getTitle());
                tvBrief.setText(recipe.getBrief());
                tvLikeinfo.setText("" + recipe.getFavor_num() + "人收藏 / " + recipe.getNice_num() + "人点赞 / " + recipe.getUpdated_date() + "发布");
                tvUname.setText(recipe.getUser_name());
                if(!TextUtils.isEmpty(recipe.getAvstart())){
                    ivAvstart.setVisibility(View.VISIBLE);
                    Picasso.with(mActivity).load(recipe.getAvstart()).into(ivAvstart);
                }else {
                    ivAvstart.setVisibility(View.GONE);
                }

                //材料
                String material = recipe.getMaterial();
                Type type = new TypeToken<ArrayList<Material>>() {
                }.getType();
                List<Material> materials = new Gson().fromJson(material, type);
                addMaterialView(materials);

                //步骤
                String description = recipe.getDescription();
                Type type1 = new TypeToken<ArrayList<Descript>>() {
                }.getType();
                List<Descript> descripts = new Gson().fromJson(description, type1);
                addDescriptView(descripts);

                //小提示
                String tips = recipe.getTips();
                if (!TextUtils.isEmpty(tips)) {
                    layoutTips.setVisibility(View.VISIBLE);
                    tvTips.setText(tips);
                } else {
                    layoutTips.setVisibility(View.GONE);
                }

                setNiceStatus(1 == recipe.getIs_nice());
                setCollectStatus(1 == recipe.getIs_favor());
            }

            @Override
            public void onFail(int code, String msg) {
                super.onFail(code, msg);
                finish();
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
        if (layoutDetails.getChildCount() > 0) {
            layoutDetails.removeAllViews();
        }
        for (Descript descript : descripts) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.view_descript_item, null);
            final RelativeLayout rl_descript = (RelativeLayout) view.findViewById(R.id.rl_descript);
            final ImageView imageView = (ImageView) view.findViewById(R.id.iv_playbill);
            TextView tv_text = (TextView) view.findViewById(R.id.tv_text);

            Picasso.with(mActivity).load(descript.getImg()).into(imageView, new Callback.EmptyCallback() {
                @Override
                public void onSuccess() {
                    final int screenW = DensityUtil.screenWidthInPx(mActivity) - DensityUtil.dip2px(mActivity, 20);
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
            tv_text.setText(descript.getText());
            layoutDetails.addView(view);
        }
    }

    public void setNiceStatus(boolean status){
        if(status){
            isNice = true;
            Drawable drawable = getResources().getDrawable(R.drawable.ic_nice_level2);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvNice.setCompoundDrawables(null, drawable, null, null);
            tvNice.setTextColor(getResources().getColor(R.color.main_color));
        }else{
            isNice = false;
            Drawable drawable = getResources().getDrawable(R.drawable.ic_nice_level1);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvNice.setCompoundDrawables(null, drawable, null, null);
            tvNice.setTextColor(getResources().getColor(R.color.text_gray_light));
        }
    }

    public void setCollectStatus(boolean status){
        if(status){
            isCollect = true;
            Drawable drawable = getResources().getDrawable(R.drawable.ic_collect_level2);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvCollect.setCompoundDrawables(null, drawable, null, null);
            tvCollect.setTextColor(getResources().getColor(R.color.main_color));
        }else{
            isCollect = false;
            Drawable drawable = getResources().getDrawable(R.drawable.ic_collect_level1);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvCollect.setCompoundDrawables(null, drawable, null, null);
            tvCollect.setTextColor(getResources().getColor(R.color.text_gray_light));
        }
    }

    @OnClick(R.id.tv_nice)
    public void nice(){
        if(!isNice){
            Api.setRecipeNice(details.getId(), 1, new NSCallback<SuccessComm>(mActivity,SuccessComm.class) {
                @Override
                public void onSuccess(SuccessComm successComm) {
                    AppTips.showToast("点赞成功");
                    setNiceStatus(true);
                }
            });
        }
    }

    @OnClick(R.id.tv_collect)
    public void collect(){
        if(isCollect){
            Api.setRecipeFavor(details.getId(), 0, new NSCallback<SuccessComm>(mActivity,SuccessComm.class) {
                @Override
                public void onSuccess(SuccessComm successComm) {
                    AppTips.showToast("您已取消收藏");
                    setCollectStatus(false);
                }
            });
        }else{
            Api.setRecipeFavor(details.getId(), 1, new NSCallback<SuccessComm>(mActivity,SuccessComm.class) {
                @Override
                public void onSuccess(SuccessComm successComm) {
                    AppTips.showToast("收藏成功");
                    setCollectStatus(true);
                }
            });
        }
    }

}
