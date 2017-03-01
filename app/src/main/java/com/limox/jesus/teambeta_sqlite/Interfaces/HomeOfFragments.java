package com.limox.jesus.teambeta_sqlite.Interfaces;

import android.support.v4.app.Fragment;

/**
 * Created by jesus on 3/01/17.
 */

public interface HomeOfFragments {
    /**
     * Replace the current view for the fragment introduced
     * This method put the @param fragment like the curren fragment
     * @param fragment fragment to start
     * @param addStack if add to the backStack
     */
    void startFragment(Fragment fragment, boolean addStack,String tag);
}
