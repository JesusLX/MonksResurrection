package com.limox.jesus.monksresurrection.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.limox.jesus.monksresurrection.Fragments.GenericPosts.DashPost_Fragment;
import com.limox.jesus.monksresurrection.Model.Post;
import com.limox.jesus.monksresurrection.Repositories.Posts_Repository;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

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
                args.putParcelableArrayList(AllConstants.ARRAYLIST_POST_PARCELABLE_KEY, Posts_Repository.get().getPostsPublished(Post.LAST_FIRST));
                args.putInt(AllConstants.TYPELIST_KEY,AllConstants.FOR_PUBLISHED);
                break;
            case 1:
                args.putParcelableArrayList(AllConstants.ARRAYLIST_POST_PARCELABLE_KEY, Posts_Repository.get().getPostsFixed(Post.LAST_FIRST));
                args.putInt(AllConstants.TYPELIST_KEY,AllConstants.FOR_FIXES);
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
