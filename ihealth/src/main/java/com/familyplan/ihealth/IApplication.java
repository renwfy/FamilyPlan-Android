package com.familyplan.ihealth;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.familyplan.ihealth.activity.LoginActivity;
import com.familyplan.ihealth.model.User;
import com.familyplan.ihealth.net.NSRequestManager;
import com.lib.AppLib;
import com.lib.activity.ActivityManager;

/**
 * Created by LSD on 16/7/23.
 */
public class IApplication extends Application {
    private static IApplication app;
    private Session session = null;

    public static IApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDebug();
        app = this;
        session = Session.load();

        //app
        AppLib.init(this);
        AppLib.setDebug(Constants.DEBUG);
        AppLib.initFileUtil(Constants.BASE_FOLDER);
        NSRequestManager.init(this, Constants.BASE_URL);
    }

    private void initDebug() {
        Constants.initDebug(Boolean.parseBoolean(getString(R.string.debug)));
    }

    public static void exit() {
        ActivityManager.getScreenManager().popAllActivity();
        System.exit(1);
    }

    public static boolean isLogin() {
        return getInstance().getUserId() > 0;
    }

    /***
     * 登录
     *
     * @param context
     * @param user
     */
    public void login(Context context, User user) {
        session.saveUser(user);
        //syncJPush();
        ActivityManager.getScreenManager().popLoginActivity();
    }

    /***
     * 退出登录
     *
     * @param context
     */
    public void logout(Context context) {
        //清除用户数据
        session.logout();
        //syncJPush();
        ActivityManager.getScreenManager().popTopActivity();
    }

    /***
     * 没有登录
     *
     * @param context
     */
    public void notLogin(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    /***
     * 必须登录
     */
    public static void startActivityWithAuth(Context context, Class<?> clz) {
        startActivityWithAuth(context,new Intent(context,clz));
    }
    public static void startActivityWithAuth(Context context, Intent intent) {
        if (isLogin()) {
            context.startActivity(intent);
        } else {
            IApplication.getInstance().notLogin(context);
        }
    }


    public User getUser() {
        return session.getUser();
    }

    public void saveUser(User user) {
        session.saveUser(user);
    }

    public int getUserId() {
        User user = getUser();
        if (user != null) {
            return user.getUser_id();
        } else {
            return 0;
        }
    }


}
