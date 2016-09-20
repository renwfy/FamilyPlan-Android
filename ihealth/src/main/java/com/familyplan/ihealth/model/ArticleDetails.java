package com.familyplan.ihealth.model;

import java.io.Serializable;

/**
 * Created by LSD on 16/7/31.
 */
public class ArticleDetails implements Serializable{
    String img;
    String text;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
