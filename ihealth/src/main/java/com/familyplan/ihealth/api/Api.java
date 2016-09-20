package com.familyplan.ihealth.api;

import android.text.TextUtils;

import com.familyplan.ihealth.IApplication;
import com.familyplan.ihealth.model.Article;
import com.familyplan.ihealth.model.ArticleCategory;
import com.familyplan.ihealth.model.Recipe;
import com.familyplan.ihealth.model.RecipeDetails;
import com.familyplan.ihealth.model.SuccessComm;
import com.familyplan.ihealth.model.User;
import com.familyplan.ihealth.net.NSCallback;
import com.familyplan.ihealth.net.NSHttpClent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LSD on 16/7/23.
 */
public class Api extends NSHttpClent {

    /***
     * 创建食谱
     */
    public static void recipe_create(String playbill, String title, String brief, String material, String descript, String tips, NSCallback.NSTokenCallback<SuccessComm> callBack) {
        String url = "/recipe_create";
        Map<String, String> params = new HashMap<>();
        params.put("playbill", playbill);
        params.put("title", title);
        params.put("brief", brief);
        params.put("material", material);
        params.put("description", descript);
        params.put("tips", tips);
        post(url, params, callBack);
    }

    /***
     * 获取食谱列表
     */
    public static void getRecipeList(int type,NSCallback<Recipe> callBack) {
        String url = "/recipe_list";
        Map<String, String> params = new HashMap<>();
        params.put("type",type+"");
        get(url, params, callBack);
    }

    /**
     * 详情
     *
     * @param id
     * @param callBack
     */
    public static void getRecipeDetails(int id, NSCallback<RecipeDetails> callBack) {
        String url = "/recipe_details";
        Map<String, String> params = new HashMap<>();
        params.put("user_id", IApplication.getInstance().getUserId()+"");
        params.put("id", id + "");
        get(url, params, callBack);
    }

    /***
     * 收藏 / 取消收藏
     *
     * @param recipe_id
     * @param favor
     * @param callBack
     */
    public static void setRecipeFavor(int recipe_id, int favor, NSCallback.NSTokenCallback<SuccessComm> callBack) {
        String url = "/recipe_favor";
        Map<String, String> params = new HashMap<>();
        params.put("recipe_id", recipe_id + "");
        params.put("favor", favor + "");
        post(url, params, callBack);
    }

    /***
     * 点赞/ 取消点赞
     *
     * @param recipe_id
     * @param nice
     * @param callBack
     */
    public static void setRecipeNice(int recipe_id, int nice, NSCallback.NSTokenCallback<SuccessComm> callBack) {
        String url = "/recipe_nice";
        Map<String, String> params = new HashMap<>();
        params.put("recipe_id", recipe_id + "");
        params.put("nice", nice + "");
        post(url, params, callBack);
    }

    /***
     * 登陆
     *
     * @param user_name
     * @param password
     * @param callBack
     */
    public static void login(String user_name, String password, NSCallback<User> callBack) {
        String url = "/login";
        Map<String, String> params = new HashMap<>();
        params.put("user_name", user_name);
        params.put("password", password);
        post(url, params, callBack);
    }

    /***
     * 注册
     *
     * @param user_name
     * @param password
     * @param confirm_password
     * @param captcha
     * @param callBack
     */
    public static void register(String user_name, String password, String confirm_password, String captcha, NSCallback<User> callBack) {
        String url = "/register";
        Map<String, String> params = new HashMap<>();
        params.put("user_name", user_name);
        params.put("password", password);
        params.put("confirm_password", confirm_password);
        params.put("captcha", captcha);
        post(url, params, callBack);
    }

    /***
     * 获取验证码
     *
     * @param phone
     * @param callBack
     */
    public static void reqcaptcha(String phone, NSCallback<SuccessComm> callBack) {
        String url = "/reqcaptcha";
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        get(url, params, callBack);
    }


    /***
     * 修改密码/找回密码
     *
     * @param user_name
     * @param password
     * @param confirm_password
     * @param captcha
     * @param callBack
     */
    public static void findpass(String user_name, String password, String confirm_password, String captcha, NSCallback.NSTokenCallback<User> callBack) {
        String url = "/findpass";
        Map<String, String> params = new HashMap<>();
        params.put("user_name", user_name);
        params.put("password", password);
        params.put("confirm_password", confirm_password);
        params.put("captcha", captcha);
        post(url, params, callBack);
    }


    /***
     * 获取用户信息
     *
     * @param callBack
     */
    public static void userinfo(NSCallback.NSTokenCallback<User> callBack) {
        String url = "/userinfo";
        Map<String, String> params = new HashMap<>();
        get(url, params, callBack);
    }


    /***
     * 更新用户信息
     *
     * @param user
     * @param callBack
     */
    public static void update_userinfo(User user, NSCallback.NSTokenCallback<SuccessComm> callBack) {
        String url = "/update_userinfo";
        Map<String, String> params = new HashMap<>();
        params.put("nick_name", user.getNick_name() + "");
        params.put("avstart", user.getAvstart() + "");
        params.put("sign", user.getSign() + "");
        params.put("age", user.getAge() + "");
        params.put("sex", user.getSex() + "");
        params.put("height", user.getHeight() + "");
        params.put("weight", user.getWeight() + "");
        post(url, params, callBack);
    }

    /***
     * 科普分类
     *
     * @param callBack
     */
    public static void article_category(NSCallback<ArticleCategory> callBack) {
        String url = "/article_category";
        Map<String, String> params = new HashMap<>();
        get(url, params, callBack);
    }


    /***
     * 科普文章列表
     *
     * @param start
     * @param size
     * @param tag
     * @param callBack
     */
    public static void article_list(int start, int size, String tag, NSCallback<Article> callBack) {
        String url = "/article_list";
        Map<String, String> params = new HashMap<>();
        params.put("start", start + "");
        params.put("size", size + "");
        if (!TextUtils.isEmpty(tag)) {
            params.put("tag", tag);
        }
        get(url, params, callBack);
    }

    /***
     * 文章详情
     *
     * @param article_id
     * @param callBack
     */
    public static void article_details(int article_id , NSCallback<Article> callBack) {
        String url = "/article_details";
        Map<String, String> params = new HashMap<>();
        params.put("article_id", article_id + "");
        get(url, params, callBack);
    }

}
