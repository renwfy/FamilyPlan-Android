package com.familyplan.ihealth.utils;

import android.graphics.Bitmap;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.activity.AbstractActivity;
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
    public static void startFromAlbumSinglePick(final AbstractActivity activity, boolean needCorop) {
        //图片剪裁的一些设置
        UCrop.Options options = new UCrop.Options();
        //图片生成格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        //图片压缩比
        options.setCompressionQuality(100);
        new PickConfig.Builder(activity)
                .maxPickSize(1)//最多选择几张
                .isneedcamera(true)//是否需要第一项是相机
                .spanCount(3)//一行显示几张照片
                .actionBarcolor(activity.getResources().getColor(R.color.colorPrimary))//设置toolbar的颜色
                .statusBarcolor(activity.getResources().getColor(R.color.colorPrimaryDark)) //设置状态栏的颜色(5.0以上)
                .isneedcrop(needCorop)//受否需要剪裁
                .setUropOptions(options) //设置剪裁参数
                .isSqureCrop(true) //是否是正方形格式剪裁
                .pickMode(PickConfig.MODE_SINGLE_PICK)//单选还是多选
                .build();
    }

    /***
     * 选择相册 多选
     *
     * @param activity
     * @param selectNum
     */
    public static void startFromAlbumMultipPick(final AbstractActivity activity, int selectNum) {
        //图片剪裁的一些设置
        UCrop.Options options = new UCrop.Options();
        //图片生成格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        //图片压缩比
        options.setCompressionQuality(100);
        new PickConfig.Builder(activity)
                .maxPickSize(selectNum)//最多选择几张
                .isneedcamera(true)//是否需要第一项是相机
                .spanCount(3)//一行显示几张照片
                .actionBarcolor(activity.getResources().getColor(R.color.colorPrimary))//设置toolbar的颜色
                .statusBarcolor(activity.getResources().getColor(R.color.colorPrimaryDark)) //设置状态栏的颜色(5.0以上)
                .isneedcrop(false)//受否需要剪裁
                .setUropOptions(options) //设置剪裁参数
                .isSqureCrop(true) //是否是正方形格式剪裁
                .pickMode(PickConfig.MODE_MULTIP_PICK)//单选还是多选
                .build();
    }
}
