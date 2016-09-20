package com.familyplan.ihealth.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.activity.AbstractActivity;
import com.lib.utils.AppTips;
import com.wq.photo.widget.PickConfig;
import com.yalantis.ucrop.UCrop;


/**
 * Created by LSD on 16/3/17.
 */
public class GalleryManager {
    /**
     * 选择相册 单选
     *
     * @param activity
     * @param needCorop
     */
    public static void startFromAlbum(final AbstractActivity activity, boolean needCorop) {
        //图片剪裁的一些设置
        UCrop.Options options = new UCrop.Options();
        //图片生成格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);//withAspectRatio
        //图片压缩比
        options.setCompressionQuality(90);
        new PickConfig.Builder(activity)
                .maxPickSize(9)//最多选择几张
                .isneedcamera(true)//是否需要第一项是相机
                .spanCount(3)//一行显示几张照片
                .actionBarcolor(activity.getResources().getColor(R.color.colorPrimary))//设置toolbar的颜色
                .statusBarcolor(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? activity.getResources().getColor(R.color.colorPrimaryDark)
                        : Color.parseColor("#00000000")) //设置状态栏的颜色(5.0以上)
                .isneedcrop(needCorop)//受否需要剪裁
                .setUropOptions(options) //设置剪裁参数
                .isSqureCrop(false) //是否是正方形格式剪裁
                .setAspectRatio(4, 3)//剪裁比例
                .pickMode(PickConfig.MODE_SINGLE_PICK)//单选还是多选
                .build();
    }


    /***
     * 相机
     *
     * @param activity
     * @param needCorop
     */
    public static void startFromCamra(final AbstractActivity activity, boolean needCorop) {
        //图片剪裁的一些设置
        UCrop.Options options = new UCrop.Options();
        //图片生成格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        //图片压缩比
        options.setCompressionQuality(90);
        new PickConfig.Builder(activity)
                .maxPickSize(9)//最多选择几张
                .isneedcamera(true)//是否需要第一项是相机
                .spanCount(3)//一行显示几张照片
                .actionBarcolor(activity.getResources().getColor(R.color.colorPrimary))//设置toolbar的颜色
                .statusBarcolor(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? activity.getResources().getColor(R.color.colorPrimaryDark)
                        : Color.parseColor("#00000000")) //设置状态栏的颜色(5.0以上)
                .isneedcrop(needCorop)//受否需要剪裁
                .setUropOptions(options) //设置剪裁参数
                .isSqureCrop(false) //是否是正方形格式剪裁
                .setAspectRatio(4, 3)//剪裁比例
                .pickMode(PickConfig.MODE_SINGLE_PICK)//单选还是多选
                .startwithcamera(true)//直接打开相机
                .build();
    }
}
