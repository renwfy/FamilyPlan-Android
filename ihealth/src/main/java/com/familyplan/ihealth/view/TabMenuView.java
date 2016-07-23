package com.familyplan.ihealth.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.familyplan.ihealth.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lsd on 15-1-4.
 */
public class TabMenuView {

    private View root;
    private Context context;

    @BindView(R.id.iv_tab_view)
    ImageView image;

    @BindView(R.id.tv_tab_text)
    TextView text;

    @BindView(R.id.iv_tips)
    ImageView tips;

    public TabMenuView(Context context){
        this.context = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        root = layoutInflater.inflate(R.layout.view_tab_item, null);
        //image = (ImageView) root.findViewById(R.id.iv_tab_view);
        //text = (TextView) root.findViewById(R.id.tv_tab_text);
        ButterKnife.bind(this, root);
    }

    public void render(String text,int ir){
        image.setImageResource(ir);
        this.text.setText(text);
    }

    public void setNewTips(boolean tips){
        this.tips.setVisibility(tips ? View.VISIBLE : View.GONE);
    }

    public View getView(){
        return root;
    }

}
