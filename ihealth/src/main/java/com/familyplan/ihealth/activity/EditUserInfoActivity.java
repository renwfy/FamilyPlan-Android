package com.familyplan.ihealth.activity;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.familyplan.ihealth.R;
import com.familyplan.ihealth.event.EditUserEvent;
import com.familyplan.ihealth.model.User;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LSD on 16/8/6.
 */
public class EditUserInfoActivity extends CommonActivity {
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.et_sign)
    EditText etSign;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.layout3)
    LinearLayout layout3;

    int type = -1;
    User user;

    @Override
    protected int getContentView() {
        return R.layout.activity_edituserinfo;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();

        type = getIntent().getIntExtra("type", -1);
        user = (User) getIntent().getSerializableExtra("user");
        if (1 == type) {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
            setTitle("编辑昵称");
            etInput.setText(user.getNick_name());
            etInput.setHint("请输入昵称");
            etInput.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        if (2 == type) {
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.VISIBLE);
            setTitle("选择性别");
        }
        if (3 == type) {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
            setTitle("编辑年龄");
            etInput.setText(user.getAge() + "");
            etInput.setHint("请输入年龄");
            etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (4 == type) {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
            setTitle("编辑身高");
            etInput.setText(user.getHeight() + "");
            etInput.setHint("请输入身高");
            etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (5 == type) {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
            setTitle("编辑体重");
            etInput.setText(user.getWeight() + "");
            etInput.setHint("请输入体重");
            etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (6 == type) {
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.GONE);
            setTitle("编辑个性签名");
            etSign.setText(user.getSign() + "");
            etSign.setHint("请输入个性签名");
        }
    }

    private void save(){
        if(1 == type){
            user.setNick_name(etInput.getText().toString());
        }
        if(3 == type){
            user.setAge(Integer.parseInt(etInput.getText().toString()));
        }
        if(4 == type){
            user.setHeight(Integer.parseInt(etInput.getText().toString()));
        }
        if(5 == type){
            user.setWeight(Integer.parseInt(etInput.getText().toString()));
        }
        if(6 == type){
            user.setSign(etSign.getText().toString());
        }
        EventBus.getDefault().post(new EditUserEvent(user));
    }

    @OnClick(R.id.tv_male)
    public void male(){
        user.setSex(1);
        EventBus.getDefault().post(new EditUserEvent(user));
        super.onBackClick();
    }
    @OnClick(R.id.tv_fmale)
    public void fmale(){
        user.setSex(2);
        EventBus.getDefault().post(new EditUserEvent(user));
        super.onBackClick();
    }

    @Override
    public void onBackClick() {
        save();
        super.onBackClick();
    }

    @Override
    public void onBackPressed() {
        save();
        super.onBackPressed();
    }
}
