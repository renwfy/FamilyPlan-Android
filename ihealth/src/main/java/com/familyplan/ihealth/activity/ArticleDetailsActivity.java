package com.familyplan.ihealth.activity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.api.Api;
import com.familyplan.ihealth.model.Article;
import com.familyplan.ihealth.model.ArticleDetails;
import com.familyplan.ihealth.net.NSCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lib.utils.DensityUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by LSD on 16/7/31.
 */
public class ArticleDetailsActivity extends CommonActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_uname)
    TextView tvUname;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;
    @BindView(R.id.tv_nice)
    TextView tvNice;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_share)
    TextView tvShare;

    @Override
    protected int getContentView() {
        return R.layout.activity_articledetails;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("");

        loadData();
    }

    public void loadData() {
        int article_id = getIntent().getIntExtra("article_id", 0);
        Api.article_details(article_id, new NSCallback<Article>(mActivity, Article.class) {
            @Override
            public void onSuccess(Article article) {
                tvTitle.setText(article.getTitle());
                Picasso.with(mActivity).load(article.getLogo() + "?imageView2/5/w/50/h/50").into(ivLogo);
                tvUname.setText(article.getAuthor());
                tvDate.setText(article.getUpdated_date());

                String detailsStr = article.getDetails();
                if (!TextUtils.isEmpty(detailsStr)) {
                    Type type = new TypeToken<ArrayList<ArticleDetails>>() {
                    }.getType();
                    List<ArticleDetails> details = new Gson().fromJson(detailsStr, type);
                    addDetialsView(details);
                }
            }

            @Override
            public void onFail(int code, String msg) {
                super.onFail(code, msg);
                finish();
            }
        });
    }

    public void addDetialsView(List<ArticleDetails> detailList) {
        if (detailList.size() <= 0) {
            return;
        }
        if (contentLayout.getChildCount() > 0) {
            contentLayout.removeAllViews();
        }
        for (int i = 0; i < detailList.size(); i++) {
            ArticleDetails detail = detailList.get(i);
            View view = LayoutInflater.from(mActivity).inflate(R.layout.view_articledeatils_item, null);
            final ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
            TextView tv_text = (TextView) view.findViewById(R.id.tv_text);

            String imgurl = detail.getImg();
            if (!TextUtils.isEmpty(imgurl)) {
                Picasso.with(mActivity).load(imgurl).into(imageView, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        final int screenW = DensityUtil.screenWidthInPx(mActivity) - DensityUtil.dip2px(mActivity, 20);
                        int imageW = imageView.getDrawable().getIntrinsicWidth();
                        int imageH = imageView.getDrawable().getIntrinsicHeight();
                        int H = screenW * imageH / imageW;

                        //图片处理
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                        params.width = screenW;
                        params.height = H;
                        imageView.setLayoutParams(params);
                    }
                });
            }
            String text = detail.getText();
            if (!TextUtils.isEmpty(text)) {
                tv_text.setText(text);
            }
            contentLayout.addView(view);
        }
    }
}
