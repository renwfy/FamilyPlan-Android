package com.familyplan.ihealth.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.familyplan.ihealth.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 倒计时按钮
 * Created by wuhaojie on 2015/7/23.
 */
public class CountDownButton extends Button {
    /**
     * 默认倒计时时间
     */
    private static final int DEF_TIME = 60;
    /**
     * 倒计时状态
     */
    private boolean isCountDown = false;
    /**
     * 倒计时时间
     */
    private int time = DEF_TIME;
    /**
     * 倒计时状态文本格式：如 59秒后重新获取
     */
    private String format;
    /**
     * 非倒计时状态按钮文本
     */
    private String text;
    /**
     * 剩余时间
     */
    private int leftTime;
    /**
     * 定时任务
     */
    private TimerTask timerTask;
    /**
     * 定时器
     */
    private Timer timer;
    /**
     * handler处理消息
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -100:
                    stopCountDown();
                    break;
                case 100:
                    setText(String.format(format, leftTime));
                    break;
            }
        }
    };

    /**
     * 构造函数
     *
     * @param context
     * @param attrs
     */
    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    /**
     * 初始化视图
     */
    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CountDownButton);
            time = attributes.getInt(R.styleable.CountDownButton_time, DEF_TIME);
            text = attributes.getString(R.styleable.CountDownButton_text);
            setText(text);
            format = "%d后重新获取";
            if (!attributes.getString(R.styleable.CountDownButton_format).isEmpty()) {
                format = attributes.getString(R.styleable.CountDownButton_format);
            }
            attributes.recycle();
        }
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCountDown) {
                    startCountDown();
                }
            }
        });
    }

    /**
     * 停止倒计时
     */
    public void stopCountDown() {
        setEnabled(true);
        setText(text);
        if (timer != null && timerTask != null) {
            timerTask.cancel();
            timer.cancel();
            timerTask = null;
            timer = null;
        }
        isCountDown = false;
    }

    /**
     * 开始倒计时
     */
    public void startCountDown() {
        setEnabled(false);
        leftTime = time;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                leftTime--;
                if (leftTime < 0) {
                    handler.sendEmptyMessage(-100);
                } else {
                    handler.sendEmptyMessage(100);
                }
            }
        };
        timer.schedule(timerTask, 0, 1000);
        isCountDown = true;
    }
}
