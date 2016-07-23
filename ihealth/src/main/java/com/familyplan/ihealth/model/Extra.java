package com.familyplan.ihealth.model;

import java.io.Serializable;

/**
 * Created by LSD on 16/4/5.
 */
public class Extra implements Serializable{
    String title;
    String url;
    boolean isLandscape;
    boolean isNewActivity;
    boolean disableTitleBar;
    boolean fullScreen;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isLandscape() {
        return isLandscape;
    }

    public void setIsLandscape(boolean isLandscape) {
        this.isLandscape = isLandscape;
    }

    public boolean isNewActivity() {
        return isNewActivity;
    }

    public void setIsNewActivity(boolean isNewActivity) {
        this.isNewActivity = isNewActivity;
    }

    public boolean isDisableTitleBar() {
        return disableTitleBar;
    }

    public void setDisableTitleBar(boolean disableTitleBar) {
        this.disableTitleBar = disableTitleBar;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }
}
