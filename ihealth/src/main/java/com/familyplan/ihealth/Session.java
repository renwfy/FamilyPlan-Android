package com.familyplan.ihealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.familyplan.ihealth.model.User;
import com.google.gson.Gson;

public class Session {
    private static String SESSION = "";

    private User user;

    /**
     * 加载到内存
     */
    public static Session load() {
        SESSION = Constants.DEBUG ? "_SESSION" : "_ONLINE_SESSION";
        SharedPreferences sp = IApplication.getInstance().getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        String info = sp.getString(SESSION, "");
        Session result = null;
        if (TextUtils.isEmpty(info)) {
            result = new Session();
        } else {
            result = new Gson().fromJson(info, Session.class);
        }
        return result;
    }

    /**
     * 持久化
     */
    public <T> void save() {
        SharedPreferences sp = IApplication.getInstance().getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String session = new Gson().toJson(this);
        editor.putString(SESSION, session);
        editor.commit();
    }

    public void saveUser(User user) {
        this.user = user;
        save();
    }

    public User getUser() {
        return user;
    }

    public void logout() {
        user = null;
        save();
    }
}
