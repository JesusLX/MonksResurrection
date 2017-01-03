package com.limox.jesus.monksresurrection.Utils;

/**
 * Created by jesus on 3/01/17.
 */

public class NavItem {
    private boolean mIsAdmin;
    private int mIcon;
    private String mTitle;

    public NavItem(int icon, String title) {
        this.mIcon = icon;
        this.mTitle = title;
        this.mIsAdmin = false;
    }

    public NavItem(int mIcon, String mTitle,boolean mIsAdmin) {
        this.mIsAdmin = mIsAdmin;
        this.mIcon = mIcon;
        this.mTitle = mTitle;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        this.mIcon = icon;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public boolean isAdmin() {
        return mIsAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.mIsAdmin = isAdmin;
    }
}
