package com.limox.jesus.teambeta_sqlite;

import android.app.Application;
import android.content.Context;

/**
 * Created by jesus on 2/03/17.
 */

public class TeamBetaApplication extends Application {

    private static Context context;

    public TeamBetaApplication(){
        context = this;
    }

    public static Context getContext(){
        return context;
    }

}
