package com.limox.jesus.teambeta;

import android.app.Application;
import android.content.Context;

/**
 * Created by jesus on 2/03/17.
 */

public class TeamBetaApplication extends Application {

    private static Context context;
    private static String mCurrentActivity;

    public TeamBetaApplication() {
        context = this;
    }

    public static Context getContext() {
        return context;
    }

    public static String getCurrentActivity() {
        return mCurrentActivity;
    }

    public static void setCurrentActivity(String currentActivity) {
        mCurrentActivity = currentActivity;
    }
}
