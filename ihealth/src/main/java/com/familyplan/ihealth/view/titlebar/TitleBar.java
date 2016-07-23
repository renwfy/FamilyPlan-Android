package com.familyplan.ihealth.view.titlebar;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.webapp.WebviewUtils;
import com.familyplan.ihealth.webapp.ZMWebView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LSD on 16/3/5.
 */
public class TitleBar extends LinearLayout {
    Context context;
    private ZMWebView webView;
    @BindView(R.id.titlebar_left)
    RelativeLayout titlebar_left;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.titlebar_right)
    RelativeLayout titlebar_right;

    OnTitleBarClickListener listener;

    public interface OnTitleBarClickListener {
        void onBackClick();

        void onLeftClick();

        void onRightClick();
    }

    public TitleBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    void init() {
        View.inflate(context, R.layout.view_title_bar, this);
        ButterKnife.bind(this);
    }

    public void setWebClient(ZMWebView webView) {
        this.webView = webView;
    }

    public ZMWebView getWebClient() {
        return webView;
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener listener) {
        this.listener = listener;
    }

    public void setTitleMenuItem(TitleBarMenuItem item) {
        if (item.isRight()) {
            setRightMenu(item);
        } else {
            setLeftMenu(item);
        }
    }

    public void setLeftMenu(final TitleBarMenuItem item) {
        if (item == null) {
            return;
        }
        tvLeft.setVisibility(GONE);
        ivLeft.setVisibility(GONE);
        ivBack.setVisibility(GONE);
        if (!TextUtils.isEmpty(item.getTitle())) {
            tvLeft.setVisibility(VISIBLE);
            tvLeft.setText(item.getTitle());
        } else if (!TextUtils.isEmpty(item.getIcon())) {
            ivLeft.setVisibility(VISIBLE);
            Picasso.with(context).load(item.getIcon()).into(ivLeft);
        } else if (0 != item.getDrawable()) {
            ivLeft.setVisibility(VISIBLE);
            Picasso.with(context).load(item.getDrawable()).into(ivLeft);
        } else {
            ivBack.setVisibility(VISIBLE);
        }
        titlebar_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(item.getUrl())) {
                    if (item.getChildren().isEmpty()) {
                        if (webView != null) {
                            WebviewUtils.shouldOverrideUrlLoading(webView, item.getUrl());
                        }
                    } else {
                        new TitleBarMenuView(context, item.getChildren()).showAsDropDown(titlebar_left);
                    }
                } else {
                    if (listener != null)
                        listener.onLeftClick();
                }
            }
        });
    }

    public void setRightMenu(final TitleBarMenuItem item) {
        if (item == null) {
            return;
        }
        tvRight.setVisibility(GONE);
        ivRight.setVisibility(GONE);
        if (!TextUtils.isEmpty(item.getTitle())) {
            tvRight.setVisibility(VISIBLE);
            tvRight.setText(item.getTitle());
        } else if (!TextUtils.isEmpty(item.getIcon())) {
            ivRight.setVisibility(VISIBLE);
            Picasso.with(context).load(item.getIcon()).into(ivRight);
        } else if (0 != item.getDrawable()) {
            ivRight.setVisibility(VISIBLE);
            Picasso.with(context).load(item.getDrawable()).into(ivRight);
        }
        titlebar_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(item.getUrl())) {
                    if (item.getChildren().isEmpty()) {
                        if (webView != null) {
                            WebviewUtils.shouldOverrideUrlLoading(webView, item.getUrl());
                        }
                    } else {
                        new TitleBarMenuView(context, item.getChildren()).showAsDropDown(titlebar_right);
                    }
                } else {
                    if (listener != null)
                        listener.onRightClick();
                }
            }
        });
    }

    public void setBackDisEnable() {
        ivBack.setVisibility(GONE);
    }

    public void setBackEnable() {
        ivBack.setVisibility(VISIBLE);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @OnClick(R.id.iv_back)
    public void back() {
        if (listener != null)
            listener.onBackClick();
    }
}
