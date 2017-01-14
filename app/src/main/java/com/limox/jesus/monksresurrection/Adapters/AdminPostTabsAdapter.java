package com.limox.jesus.monksresurrection.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.limox.jesus.monksresurrection.Fragments.GenericPosts.DashPost_Fragment;
import com.limox.jesus.monksresurrection.Fragments.Messages.MessagesList_Fragment;
import com.limox.jesus.monksresurrection.Repositories.Posts_Repository;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

/**
 * Created by jesus on 6/01/17.
 */
public class AdminPostTabsAdapter extends FragmentStatePagerAdapter {

    private String[] mTabsText;

    public AdminPostTabsAdapter(FragmentManager fm, String[] tabsText) {
        super(fm);
        mTabsText = tabsText;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment dpf = null;
        Bundle args = new Bundle();
        switch (position){
            case 0:
                args.putParcelableArrayList(AllConstants.ARRAYLIST_POST_PARCELABLE_KEY, Posts_Repository.get().getPostsNotPublished());
                args.putInt(AllConstants.TYPELIST_KEY,AllConstants.FOR_NONPUBLISHED);
                dpf = DashPost_Fragment.newInstance(args);
                break;
            case 1:
                args.putParcelableArrayList(AllConstants.ARRAYLIST_POST_PARCELABLE_KEY, Posts_Repository.get().getPostsPublished());
                args.putInt(AllConstants.TYPELIST_KEY,AllConstants.FOR_PUBLISHED);
                dpf = DashPost_Fragment.newInstance(args);
                break;
            case 2:

                dpf = new MessagesList_Fragment();
                break;
        }

        if (dpf != null) {
            dpf.setRetainInstance(true);
        }

        return dpf;
    }

    @Override
    public int getCount() {
        return mTabsText.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsText[position];
    }

}
