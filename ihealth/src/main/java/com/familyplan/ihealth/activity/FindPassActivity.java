package com.familyplan.ihealth.activity;

import android.text.TextUtils;
import android.widget.EditText;

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
 * Created by LSD on 16/6/4.
 */
public class FindPassActivity extends CommonActivity {

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
        return R.layout.activity_findpass;
    }

    @Override
    protected void onViewCreated() {
        setTitle("找回密码");
        int type = getIntent().getIntExtra("type",-1);
        if(2 == type){
            setTitle("修改密码");
        }
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
                AppTips.showToast("验证码已发送到您的手机");
            }
        });
    }

    @OnClick(R.id.bt_confirm)
    public void register() {
        String phone = etPhone.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String cpass = etCpass.getText().toString().trim();
        String code = etCode.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            AppTips.showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            AppTips.showToast("请输入密码");
            return;
        }
        if (!StringUtils.isMobile(phone)) {
            AppTips.showToast("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(cpass)) {
            AppTips.showToast("请输入确认密码");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            AppTips.showToast("请输入验证码");
            return;
        }
        Api.findpass(phone, pass, cpass, code, new NSCallback.NSTokenCallback<User>(mActivity, User.class, true, "正在操作……") {
            @Override
            public void onSuccess(User user) {
                AppTips.showToast("修改成功，请重新登录");
                finish();
            }
        });
    }
}
