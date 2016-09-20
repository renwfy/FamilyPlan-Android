package com.familyplan.ihealth.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.model.Material;
import com.lib.adapter.SimpleAdapter;
import com.lib.viewholder.ViewHolder;

/**
 * Created by LSD on 16/8/9.
 */
public class PublishMaterialAdapter extends SimpleAdapter<Material> {
    public PublishMaterialAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, final Material data, View convertView) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_publishmaterial_item, null);
        }
        EditText et_mname = ViewHolder.get(convertView, R.id.et_mname);
        EditText et_mmeasure = ViewHolder.get(convertView, R.id.et_mmeasure);
        EditText et_munit = ViewHolder.get(convertView, R.id.et_munit);

        et_mname.setText(data.getName() + "");
        et_mmeasure.setText(data.getMeasure() + "");
        et_munit.setText(data.getUnit() + "");

        et_mname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //data.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_mmeasure.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //data.setMeasure(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_munit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //data.setUnit(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return convertView;
    }
}
