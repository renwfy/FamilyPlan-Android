package com.familyplan.ihealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.activity.WebViewActivity;
import com.familyplan.ihealth.model.Advert;
import com.familyplan.ihealth.model.Article;
import com.lib.adapter.SimpleAdapter;
import com.lib.utils.DensityUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LSD on 16/7/29.
 */
public class HomeListAdapter extends SimpleAdapter<Article> {
    public HomeListAdapter(Context context) {
        super(context);
    }

    public void addData(List<Article> list) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, Article data, View convertView) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = (holder = new ViewHolder()).convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(data);
        return convertView;
    }

    public class ViewHolder {
        View convertView;
        Article data;

        @BindView(R.id.iv_ad)
        ImageView ivAd;
        @BindView(R.id.ad_layout)
        LinearLayout adLayout;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_right)
        ImageView ivRight;
        @BindView(R.id.iv_lager)
        ImageView ivLager;
        @BindView(R.id.iv_small1)
        ImageView ivSmall1;
        @BindView(R.id.iv_small2)
        ImageView ivSmall2;
        @BindView(R.id.iv_small3)
        ImageView ivSmall3;
        @BindView(R.id.gridimg_layout)
        LinearLayout gridimgLayout;
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_uname)
        TextView tvUname;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_ad_text)
        TextView tvAdText;

        public ViewHolder() {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_homelist_item, null);
            ButterKnife.bind(this, convertView);
        }

        public void setData(Article data) {
            this.data = data;
            Advert ad = data.getAd();
            if (ad != null) {
                adLayout.setVisibility(View.VISIBLE);
                tvAdText.setText(ad.getText());
                Picasso.with(context).load(ad.getImg_url() + "?imageView2/5/w/500/h/220").into(ivAd, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        int imageW = ivAd.getDrawable().getIntrinsicWidth();
                        int imageH = ivAd.getDrawable().getIntrinsicHeight();

                        final int screenW = DensityUtil.screenWidthInPx(context) - DensityUtil.dip2px(context, 20);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivAd.getLayoutParams();
                        int H = screenW * imageH / imageW;
                        params.width = screenW;
                        params.height = H;
                        ivAd.setLayoutParams(params);
                    }
                });
            } else {
                adLayout.setVisibility(View.GONE);
            }

            tvTitle.setText(data.getTitle());//标题
            //图片
            ivRight.setVisibility(View.GONE);
            gridimgLayout.setVisibility(View.GONE);
            ivLager.setVisibility(View.GONE);
            String lagerImg = data.getLarger_img();
            String smallImg = data.getSmall_img();
            if (!TextUtils.isEmpty(lagerImg)) {
                //显示大图
                String lagerImgArr[] = lagerImg.split(",");
                if (lagerImgArr.length >= 3) {
                    //显示3图
                    gridimgLayout.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(lagerImgArr[0] + "?imageView2/5/w/200/h/110").into(ivSmall1);
                    Picasso.with(context).load(lagerImgArr[1] + "?imageView2/5/w/200/h/110").into(ivSmall2);
                    Picasso.with(context).load(lagerImgArr[2] + "?imageView2/5/w/200/h/110").into(ivSmall3);
                } else {
                    //显示一个大图
                    ivLager.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(lagerImgArr[0] + "?imageView2/5/w/500/h/220").into(ivLager, new Callback.EmptyCallback() {
                        @Override
                        public void onSuccess() {
                            int imageW = ivLager.getDrawable().getIntrinsicWidth();
                            int imageH = ivLager.getDrawable().getIntrinsicHeight();

                            final int screenW = DensityUtil.screenWidthInPx(context) - DensityUtil.dip2px(context, 20);
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivLager.getLayoutParams();
                            int H = screenW * imageH / imageW;
                            params.width = screenW;
                            params.height = H;
                            ivLager.setLayoutParams(params);
                        }
                    });
                }
            } else if (!TextUtils.isEmpty(smallImg)) {
                //显示小图
                String smallImgArr[] = smallImg.split(",");
                if (smallImgArr.length >= 3) {
                    //显示3图
                    gridimgLayout.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(smallImgArr[0] + "?imageView2/5/w/200/h/110").into(ivSmall1);
                    Picasso.with(context).load(smallImgArr[1] + "?imageView2/5/w/200/h/110").into(ivSmall2);
                    Picasso.with(context).load(smallImgArr[2] + "?imageView2/5/w/200/h/110").into(ivSmall3);
                } else {
                    //显示右边的图
                    ivRight.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(smallImgArr[0] + "?imageView2/5/w/110/h/70").into(ivRight);
                }
            }
            //最下边部分
            Picasso.with(context).load(data.getLogo() + "?imageView2/5/w/50/h/50").into(ivLogo);
            tvUname.setText(data.getAuthor());
            tvDate.setText(data.getUpdated_date());
        }

        @OnClick(R.id.ad_layout)
        public void adClick() {
            Advert ad = data.getAd();
            if (ad != null && !TextUtils.isEmpty(ad.getLink_url())) {
                context.startActivity(new Intent(context, WebViewActivity.class).putExtra("url", ad.getLink_url()));
            }
        }
    }
}
