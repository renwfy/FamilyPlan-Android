package com.familyplan.ihealth.model;

/**
 * Created by LSD on 16/7/23.
 */
public class User {

    /**
     * created_date : 2016-07-02 16:29:18
     * height : 167
     * role : 1
     * sex : 1
     * user_id : 12
     * user_name : 18662430879
     * weight : 45
     */

    private String created_date;
    private String user_name;
    private int height;
    private int role;
    private int sex;
    private int user_id;
    private int weight;

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
