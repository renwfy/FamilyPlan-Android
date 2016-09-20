package com.familyplan.ihealth.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.familyplan.ihealth.Constants;
import com.familyplan.ihealth.IApplication;
import com.familyplan.ihealth.event.DescriptSelectEvent;
import com.familyplan.ihealth.event.GallerySelectEvent;
import com.lib.activity.ActivityManager;
import com.lib.utils.AppTips;
import com.wq.photo.widget.PickConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by LSD on 16/3/4.
 */
public class AbstractActivity extends BaseActivity {
    public AbstractActivity mActivity;
    private ProgressDialog _progressDialog;
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            AbstractActivity.this.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        ActivityManager.getScreenManager().pushActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Constants.DEBUG) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!Constants.DEBUG) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getScreenManager().popActivity(this);
    }

    protected void handleMessage(Message msg) {
    }

    @Override
    public void showProgressDialog(final String msg) {
        showProgressDialog(msg, false);
    }

    public void showProgressDialog(final String msg, final boolean canCancle) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (_progressDialog == null) {
                    _progressDialog = ProgressDialog.show(AbstractActivity.this, null,
                            msg, true);
                    _progressDialog.setCancelable(canCancle);
                } else {
                    _progressDialog.setMessage(msg);
                }
                if (!_progressDialog.isShowing()) {
                    _progressDialog.show();
                }
            }
        });
    }

    @Override
    public void hideProgressDialog() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (_progressDialog != null) {
                    _progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void notLogin() {
        IApplication.getInstance().notLogin(mActivity);
    }

    // 两次返回退出
    private long firstTime = 0;

    @Override
    public void onBackPressed() {
        if (ActivityManager.getScreenManager().popBackStackImmediate()) {
            super.onBackPressed();
        } else {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 800) {// 如果两次按键时间间隔大于800毫秒，则不退出
                AppTips.showToast("再按一次退出程序...");
                firstTime = secondTime;// 更新firstTime
            } else {
                IApplication.exit();// 否则退出程序
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == PickConfig.PICK_REQUEST_CODE) {
            ArrayList<String> paths = data.getStringArrayListExtra("data");
            if(mActivity instanceof PublishDescriptActivity){
                EventBus.getDefault().post(new DescriptSelectEvent(paths));
            }else{
                EventBus.getDefault().post(new GallerySelectEvent(paths));
            }
        }
    }
}
