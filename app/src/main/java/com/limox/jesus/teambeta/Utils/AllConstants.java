package com.limox.jesus.teambeta.Utils;

import com.limox.jesus.teambeta.Model.Post;

/**
 * Class of constants for te application, this class include all the constants who can be used
 * CODES
 * 00 > 09 Types of lists of posts
 */

public final class AllConstants {
    //region typeList
    public static final String TYPELIST_KEY = "type";
    public static final int FOR_NONPUBLISHED = Post.NOT_PUBLISHED;
    public static final int FOR_PUBLISHED = Post.PUBLISHED;
    public static final int FOR_FIXES = Post.FIXED;
    public static final int FOR_ALL = Post.ALL;
    // endregion
    public static final int ADMIN_TYPE_ID = 0;
    public static final int NORMALUSER_TYPE_ID = 1;
    public static final int USERNAME_MAX_LENGTH = 16;
    public static final int USERNAME_MIN_LENGTH = 4;
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 255;
    public static final int PLATFORM_GOOGLE = 1;
    public static final int PLATFORM_FACEBOOK = 2;
    public static final int PLATFORM_STEAM = 3;
    //region shared keys
    static final String SHARED_USER_FILE = "usr_file";
    static final String SHARED_USER_NAME = "current_usr_nm";
    static final String SHARED_USER_PSWRD = "current_usr_pw";
    //endregion
    //region parcelable keys
    public static final String USER_PARCELABLE_KEY = "user";
    public static final String POST_PARCELABLE_KEY = "posts";
    public static final String ARRAYLIST_POST_PARCELABLE_KEY = "arraPosts";
    //endregion
    //region save estates keys
    public static final String FRAGMENT_SAVESTATE_KEY = "fragment";
    //region
    //region tags
    public static final String HelpLoginPassTag = "hlp";
    public static final String HelpLoginTag = "hl";
    public static final String HelpLoginFinalTag = "hlf";
    public static final String PostViewTag = "pv";
    public static final String HelpTag = "h";
    public static final String AdminZoneTag = "az";
    public static final String BugForumTag = "bf";
    public static final String SettingsTag = "s";
    public static final String SignUpTag = "su";
    public static final String SignUpEmailTag = "sue";
    public static final String SignUpUserTag = "suu";
    public static final String UserProfileTag = "up";
    //endregion

}