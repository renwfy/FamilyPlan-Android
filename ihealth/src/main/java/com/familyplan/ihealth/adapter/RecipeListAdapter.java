package com.familyplan.ihealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.model.Recipe;
import com.lib.adapter.SimpleAdapter;
import com.lib.utils.DensityUtil;
import com.lib.viewholder.ViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by LSD on 16/7/29.
 */
public class RecipeListAdapter extends SimpleAdapter<Recipe> {
    public RecipeListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, Recipe data, View convertView) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_recipelist_item, null);
        }
        final ImageView ivPlayBill = ViewHolder.get(convertView, R.id.iv_playbill);
        TextView title = ViewHolder.get(convertView, R.id.tv_title);
        TextView brief = ViewHolder.get(convertView, R.id.tv_brief);
        TextView tvType = ViewHolder.get(convertView,R.id.tv_type);
        TextView hot = ViewHolder.get(convertView, R.id.tv_hot);
        TextView nice = ViewHolder.get(convertView,R.id.tv_nice);


        final int screenW = DensityUtil.screenWidthInPx(context);
        Picasso.with(context).load(data.getPlaybill()+ "?imageView2/5/w/"+screenW+"/h/380").into(ivPlayBill,new Callback.EmptyCallback(){
            @Override
            public void onSuccess() {
                int imageW = ivPlayBill.getDrawable().getIntrinsicWidth();
                int imageH = ivPlayBill.getDrawable().getIntrinsicHeight();

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivPlayBill.getLayoutParams();
                int H = screenW * imageH / imageW;
                params.width = screenW;
                params.height = H;
                ivPlayBill.setLayoutParams(params);
            }
        });
        title.setText(data.getTitle());
        brief.setText(data.getBrief());
        hot.setText(data.getFavor_num() + "");
        nice.setText(data.getNice_num()+"");
        int type = data.getType();
        if(1 == type){
            tvType.setText("减肥");
        }
        if(2 == type){
            tvType.setText("营养");
        }
        return convertView;
    }
}
