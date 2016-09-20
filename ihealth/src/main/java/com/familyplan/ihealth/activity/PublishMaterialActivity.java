package com.familyplan.ihealth.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.event.CreateMaterialEvent;
import com.familyplan.ihealth.model.Material;
import com.lib.viewholder.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LSD on 16/8/9.
 */
public class PublishMaterialActivity extends CommonActivity {
    @BindView(R.id.bt_new)
    Button btNew;
    @BindView(R.id.layout_material)
    LinearLayout layoutMaterial;

    List<Material> materialList;

    @Override
    protected int getContentView() {
        return R.layout.activity_publishmaterial;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("添加食材");
        setRightTxt("保存");

        materialList = (List<Material>) getIntent().getSerializableExtra("material");
        if (materialList == null) {
            materialList = new ArrayList<>();
        }
        addMaterialView();
    }

    public void addMaterialView() {
        if (layoutMaterial.getChildCount() > 0) {
            layoutMaterial.removeAllViews();
        }
        for (int i = 0; i < materialList.size(); i++) {
            View convertView = LayoutInflater.from(mActivity).inflate(R.layout.view_publishmaterial_item, null);
            EditText et_mname = ViewHolder.get(convertView, R.id.et_mname);
            EditText et_mmeasure = ViewHolder.get(convertView, R.id.et_mmeasure);
            EditText et_munit = ViewHolder.get(convertView, R.id.et_munit);
            ImageView remove = ViewHolder.get(convertView, R.id.iv_remove);

            final Material data = materialList.get(i);
            et_mname.setText(data.getName() + "");
            et_mmeasure.setText(data.getMeasure() + "");
            et_munit.setText(data.getUnit() + "");

            et_mname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    data.setName(s.toString());
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
                    data.setMeasure(s.toString());
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
                    data.setUnit(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialList.remove(data);
                    addMaterialView();
                }
            });
            layoutMaterial.addView(convertView);
        }
    }

    @Override
    public void onRightClick() {
        for (int i = 0; i < materialList.size(); i++) {
            Material material = materialList.get(i);
            if (TextUtils.isEmpty(material.getName())) {
                materialList.remove(i);
                i--;
            }
        }
        EventBus.getDefault().post(new CreateMaterialEvent(materialList));
        finish();
    }

    @OnClick(R.id.bt_new)
    public void newMaterial() {
        materialList.add(new Material());
        addMaterialView();
    }
}
