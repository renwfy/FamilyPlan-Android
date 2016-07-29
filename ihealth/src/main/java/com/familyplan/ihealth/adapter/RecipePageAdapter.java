package com.familyplan.ihealth.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.niusan.ctchaonao.model.HomeBanner;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by LSD on 16/6/2.
 */
public class RecipePageAdapter extends PagerAdapter {
    Context context;
    List<HomeBanner.BannerListEntity> data;

    public RecipePageAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HomeBanner.BannerListEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public final Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        view = getView(position, view, container);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data == null ? 0 : data.size();
    }

    @Override
    public final void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == object;
    }

    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Picasso.with(context).load(data.get(position).getImgUrl()).into(holder.imageView);
        return view;
    }

    private static class ViewHolder {
        ImageView imageView;
    }

}
