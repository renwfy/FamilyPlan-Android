package com.familyplan.ihealth.model;

/**
 * Created by LSD on 16/8/19.
 */
public class Article {
    private int id;
    private String title;
    private String tag;
    private String larger_img;
    private String small_img;
    private String author;
    private String logo;
    private String updated_date;
    private String details;
    private Advert ad;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLarger_img() {
        return larger_img;
    }

    public void setLarger_img(String larger_img) {
        this.larger_img = larger_img;
    }

    public String getSmall_img() {
        return small_img;
    }

    public void setSmall_img(String small_img) {
        this.small_img = small_img;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Advert getAd() {
        return ad;
    }

    public void setAd(Advert ad) {
        this.ad = ad;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
