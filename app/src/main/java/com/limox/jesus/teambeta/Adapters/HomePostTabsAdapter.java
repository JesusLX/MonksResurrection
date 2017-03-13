package com.limox.jesus.teambeta.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.limox.jesus.teambeta.Fragments.GenericPosts.DashPost_Fragment;
import com.limox.jesus.teambeta.Utils.AllConstants;

/**
 * An StatePagerAdapter with tho tabs, one for post published and other for posts fixes
 * Created by JesusLX on 22/12/16.
 */
public class HomePostTabsAdapter extends FragmentStatePagerAdapter {

    private String currentTitle;
    private String[] mTabsTitles;
    public HomePostTabsAdapter(FragmentManager fm, String[] tabsText) {
        super(fm);
        mTabsTitles = tabsText;
    }

    @Override
    public Fragment getItem(int position) {
        DashPost_Fragment dpf;
        Bundle args = new Bundle();
        switch (position){
            case 0:
                args.putInt(AllConstants.TypeLists.TYPELIST_KEY,AllConstants.TypeLists.FOR_PUBLISHED);
                break;
            case 1:
                args.putInt(AllConstants.TypeLists.TYPELIST_KEY,AllConstants.TypeLists.FOR_FIXES);
                break;
        }
        dpf = DashPost_Fragment.newInstance(args);
        dpf.setRetainInstance(true);


        return dpf;
    }

    @Override
    public int getCount() {
        return mTabsTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        currentTitle = mTabsTitles[position];
        return "";
    }
    public String getCurrentTitle(){
        return currentTitle;
    }
    public String[] getTitles(){
        return mTabsTitles;
    }
}
