package com.familyplan.ihealth.model;

/**
 * Created by LSD on 16/7/28.
 */
public class RecipeDetails {

    /**
     * id : 3
     * title : 营养健康食谱- 糖醋里脊
     * brief : 糖醋里脊，里脊肉外酥里嫩，吃起来酸甜可口。是一道不错的宴客菜。适合孩子和女性。这次纯属我自己切着玩。不太建议大家宴客的时候用，即使要展示自己的“花型”也要切的小一点，太大了不知道从哪里下口。
     * playbill : http://oac4ul6pe.bkt.clouddn.com/ic_recipe_tangculiji.png
     * type : 1
     * favor_num : 2
     * updated_date : 2016-07-22 20:42:59
     */

    private int id;
    private  int user_id;
    private String title;
    private String brief;
    private String playbill;
    private int type;
    private String material;
    private String description;
    private String tips;
    private String reviews;
    private int favor_num;
    private int nice_num;
    String avstart;
    String user_name;
    int is_favor;
    int is_nice;
    private String updated_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPlaybill() {
        return playbill;
    }

    public void setPlaybill(String playbill) {
        this.playbill = playbill;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFavor_num() {
        return favor_num;
    }

    public void setFavor_num(int favor_num) {
        this.favor_num = favor_num;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public int getNice_num() {
        return nice_num;
    }

    public void setNice_num(int nice_num) {
        this.nice_num = nice_num;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAvstart() {
        return avstart;
    }

    public void setAvstart(String avstart) {
        this.avstart = avstart;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getIs_favor() {
        return is_favor;
    }

    public void setIs_favor(int is_favor) {
        this.is_favor = is_favor;
    }

    public int getIs_nice() {
        return is_nice;
    }

    public void setIs_nice(int is_nice) {
        this.is_nice = is_nice;
    }
}
