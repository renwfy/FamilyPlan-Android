package com.familyplan.ihealth.model;

/**
 * Created by LSD on 16/8/19.
 */
public class ArticleCategory {
    int id;
    String title;
    String tag;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
