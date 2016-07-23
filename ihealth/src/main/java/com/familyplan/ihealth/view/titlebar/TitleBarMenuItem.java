package com.familyplan.ihealth.view.titlebar;

import java.util.List;

/**
 * Created by bensonwang on 15/5/21.
 */
public class TitleBarMenuItem {
    private boolean isRight;
    private String title;
    private String icon;
    private String url;
    private int drawable;

    private List<TitleBarMenuItem> children;

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<TitleBarMenuItem> getChildren() {
        return children;
    }

    public void setChildren(List<TitleBarMenuItem> children) {
        this.children = children;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
