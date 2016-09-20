package com.familyplan.ihealth.activity;

import android.text.TextUtils;
import android.widget.EditText;

import com.familyplan.ihealth.IApplication;
import com.familyplan.ihealth.R;
import com.familyplan.ihealth.api.Api;
import com.familyplan.ihealth.model.SuccessComm;
import com.familyplan.ihealth.model.User;
import com.familyplan.ihealth.net.NSCallback;
import com.familyplan.ihealth.view.CountDownButton;
import com.lib.utils.AppTips;
import com.lib.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LSD on 16/8/6.
 */
public class RegisterActivity extends CommonActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_getCode)
    CountDownButton btnGetCode;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_cpass)
    EditText etCpass;

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitle("注册");
    }

    @OnClick(R.id.btn_getCode)
    public void getCode() {
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone.trim())) {
            AppTips.showToast("请输入手机号");
            return;
        }
        if (!StringUtils.isMobile(phone)) {
            AppTips.showToast("请输入正确的手机号");
            return;
        }

        btnGetCode.startCountDown();
        Api.reqcaptcha(phone, new NSCallback<SuccessComm>(mActivity, SuccessComm.class, true, "正在获取") {
            @Override
            public void onSuccess(SuccessComm comm) {
                AppTips.showToast("验证码已发送到您的手机,请注意查收");
            }
        });
    }

    @OnClick(R.id.bt_register)
    public void register() {
        final String phone = etPhone.getText().toString();
        final String pass = etPass.getText().toString();
        final String cpass = etCpass.getText().toString();
        final String code = etCode.getText().toString();

        if (TextUtils.isEmpty(phone.trim())) {
            AppTips.showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(pass.trim())) {
            AppTips.showToast("请输入密码");
            return;
        }
        if (!StringUtils.isMobile(phone)) {
            AppTips.showToast("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(cpass.trim())) {
            AppTips.showToast("请输入确认密码");
            return;
        }
        if (TextUtils.isEmpty(code.trim())) {
            AppTips.showToast("请输入验证码");
            return;
        }
        Api.register(phone, pass, cpass, code, new NSCallback<User>(mActivity, User.class, true, "正在注册") {
            @Override
            public void onSuccess(User user) {
                IApplication.getInstance().login(mActivity, user);
            }
        });
    }

    @OnClick(R.id.tv_login)
    public void login() {
        finish();
    }

}
