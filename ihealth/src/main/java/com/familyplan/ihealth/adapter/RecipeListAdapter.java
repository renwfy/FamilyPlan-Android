package com.familyplan.ihealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.activity.RecipeDetailsActivity;
import com.familyplan.ihealth.model.Recipe;
import com.lib.adapter.SimpleAdapter;
import com.lib.utils.DensityUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LSD on 16/7/29.
 */
public class RecipeListAdapter extends SimpleAdapter<Recipe> {
    public RecipeListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, Recipe data, View convertView) {
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
        @BindView(R.id.iv_playbill)
        ImageView ivPlaybill;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_brief)
        TextView tvBrief;
        @BindView(R.id.tv_star)
        TextView tvStar;
        @BindView(R.id.ratingbar)
        RatingBar ratingbar;
        @BindView(R.id.tv_nice)
        TextView tvNice;
        @BindView(R.id.tv_hot)
        TextView tvHot;

        public ViewHolder() {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_recipelist_item, null);
            ButterKnife.bind(this, convertView);
        }

        public void setData(Recipe data) {
            final int screenW = DensityUtil.screenWidthInPx(context);
            int height = screenW / 2;
            Picasso.with(context).load(data.getPlaybill() + "?imageView2/5/w/" + screenW + "/h/" + height).into(ivPlaybill, new Callback.EmptyCallback() {
                @Override
                public void onSuccess() {
                    int imageW = ivPlaybill.getDrawable().getIntrinsicWidth();
                    int imageH = ivPlaybill.getDrawable().getIntrinsicHeight();

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivPlaybill.getLayoutParams();
                    int H = screenW * imageH / imageW;
                    params.width = screenW;
                    params.height = H;
                    ivPlaybill.setLayoutParams(params);
                }
            });
            tvTitle.setText(data.getTitle());
            tvBrief.setText(data.getBrief());
            if (data.getFavor_num() > 0)
                tvHot.setText(data.getFavor_num() + "");
            else
                tvHot.setText("");
            if (data.getNice_num() > 0)
                tvNice.setText(data.getNice_num() + "");
            else
                tvNice.setText("");
            int reviews_vote = data.getReviews_vote();
            if (reviews_vote < 2) reviews_vote = 2;
            if (reviews_vote > 5) reviews_vote = 5;
            ratingbar.setNumStars(reviews_vote);
        }
    }
}
