package com.familyplan.ihealth.activity;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;

import com.familyplan.ihealth.IApplication;
import com.familyplan.ihealth.R;
import com.familyplan.ihealth.api.Api;
import com.familyplan.ihealth.model.User;
import com.familyplan.ihealth.net.NSCallback;
import com.lib.utils.AppTips;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LSD on 16/8/6.
 */
public class LoginActivity extends CommonActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pass)
    EditText etPass;

    boolean showpass;


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("登录");
    }

    @OnClick(R.id.bt_login)
    public void login() {
        String user_name = etPhone.getText().toString().trim();
        String password = etPass.getText().toString().trim();
        if (TextUtils.isEmpty(user_name)) {
            AppTips.showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            AppTips.showToast("请输入密码");
            return;
        }
        Api.login(user_name, password, new NSCallback<User>(mActivity, User.class, true, "登录中…") {
            @Override
            public void onSuccess(User user) {
                AppTips.showToast("登录成功");
                IApplication.getInstance().login(mActivity, user);
            }
        });
    }

    @OnClick(R.id.iv_showpass)
    public void showpass() {
        if (showpass) {
            showpass = false;
            etPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            showpass = true;
            etPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @OnClick(R.id.tv_register)
    public void register() {
        startActivity(new Intent(mActivity, RegisterActivity.class));
    }

    @OnClick(R.id.tv_forgot)
    public void forgot() {
        startActivity(new Intent(mActivity, FindPassActivity.class));
    }
}
