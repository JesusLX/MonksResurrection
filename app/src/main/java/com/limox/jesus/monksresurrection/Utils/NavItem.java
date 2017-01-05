package com.limox.jesus.monksresurrection.Utils;

/**
 * This is a class for the items of navigation who can be used for lists
 * Created by jesus on 3/01/17.
 */

public class NavItem {
    private boolean mIsAdmin;
    private int mIcon;
    private String mTitle;
    public static final int NO_ICON = -1;
    private boolean mIsTitle;

    public NavItem(int icon, String title) {
        this.mIcon = icon;
        this.mTitle = title;
        this.mIsAdmin = false;
    }
    public NavItem(String title) {
        this.mIcon = NO_ICON;
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

    public boolean ismIsTitle() {
        return mIsTitle;
    }

    public void setmIsTitle(boolean mIsTitle) {
        this.mIsTitle = mIsTitle;
    }
}
