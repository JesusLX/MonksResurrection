package com.limox.jesus.teambeta_sqlite.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.limox.jesus.teambeta_sqlite.Fragments.GenericPosts.DashPost_Fragment;
import com.limox.jesus.teambeta_sqlite.Fragments.Messages.MessagesList_Fragment;
import com.limox.jesus.teambeta_sqlite.Repositories.Posts_Repository;
import com.limox.jesus.teambeta_sqlite.Utils.AllConstants;

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
                args.putParcelableArrayList(AllConstants.Keys.Parcelables.ARRAYLIST_POST_PARCELABLE_KEY, Posts_Repository.get().getPostsNotPublished(null));
                args.putInt(AllConstants.TypeLists.TYPELIST_KEY,AllConstants.TypeLists.FOR_NONPUBLISHED);
                dpf = DashPost_Fragment.newInstance(args);
                break;
            case 1:
                args.putParcelableArrayList(AllConstants.Keys.Parcelables.ARRAYLIST_POST_PARCELABLE_KEY, Posts_Repository.get().getPostsPublished(null));
                args.putInt(AllConstants.TypeLists.TYPELIST_KEY,AllConstants.TypeLists.FOR_PUBLISHED);
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
