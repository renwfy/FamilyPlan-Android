package com.familyplan.ihealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.familyplan.ihealth.R;
import com.lib.adapter.SimpleAdapter;
import com.lib.viewholder.ViewHolder;

import java.util.List;

/**
 * Created by LSD on 16/8/6.
 */
public class SettingAdapter extends SimpleAdapter<String> {
    public SettingAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, String data, View convertView) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_setting_item, null);
        }
        TextView my_item_text = ViewHolder.get(convertView, R.id.my_item_text);
        TextView my_item_tips = ViewHolder.get(convertView, R.id.my_item_tips);
        my_item_text.setText(data);
        return convertView;
    }
}
