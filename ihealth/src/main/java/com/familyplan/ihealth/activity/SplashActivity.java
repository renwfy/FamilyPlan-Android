package com.familyplan.ihealth.activity;

import android.content.Intent;

import com.familyplan.ihealth.MainActivity;
import com.familyplan.ihealth.R;

/**
 * Created by LSD on 16/7/23.
 */
public class SplashActivity extends CommonActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        setTitleBarDisable();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mActivity, MainActivity.class));
                finish();
            }
        }, 1000);
    }
}
