package com.familyplan.ihealth.api;

import com.familyplan.ihealth.IApplication;
import com.familyplan.ihealth.model.Recipe;
import com.familyplan.ihealth.model.RecipeDetails;
import com.familyplan.ihealth.model.SuccessComm;
import com.familyplan.ihealth.net.NSCallback;
import com.familyplan.ihealth.net.NSHttpClent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LSD on 16/7/23.
 */
public class Api extends NSHttpClent {

    /***
     * 获取食谱列表
     */
    public static void getRecipeList(NSCallback<Recipe> callBack) {
        String url = "/recipe_list";
        Map<String, String> params = new HashMap<>();
        get(url, params, callBack);
    }

    /**
     * 详情
     * @param id
     * @param callBack
     */
    public static void getRecipeDetails(int id,NSCallback<RecipeDetails> callBack) {
        String url = "/recipe_details";
        Map<String, String> params = new HashMap<>();
        params.put("id",id+"");
        get(url, params, callBack);
    }

    /***
     * 收藏 / 取消收藏
     * @param recipe_id
     * @param favor
     * @param callBack
     */
    public static void setRecipeFavor(int recipe_id,int favor,NSCallback<SuccessComm> callBack){
        String url = "/recipe_favor";
        Map<String, String> params = new HashMap<>();
        params.put("user_id", IApplication.getInstance().getUserId()+"");
        params.put("recipe_id", recipe_id+"");
        params.put("favor", favor+"");
        post(url, params, callBack);
    }

    /***
     * 点赞/ 取消点赞
     * @param recipe_id
     * @param nice
     * @param callBack
     */
    public static void setRecipeNice(int recipe_id,int nice,NSCallback<SuccessComm> callBack){
        String url = "/recipe_nice";
        Map<String, String> params = new HashMap<>();
        params.put("user_id", IApplication.getInstance().getUserId()+"");
        params.put("recipe_id", recipe_id+"");
        params.put("nice", nice+"");
        post(url, params, callBack);
    }
}
