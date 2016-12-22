package com.limox.jesus.monksresurrection.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.limox.jesus.monksresurrection.Fragments.GenericPosts.DashPost_Fragment;
import com.limox.jesus.monksresurrection.Singleton.Posts_Singleton;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

/**
 * Created by usuario on 22/12/16.
 */
public class PostPagerAdapter extends FragmentStatePagerAdapter {

    String[] mTabsText;
    public PostPagerAdapter(FragmentManager fm, String[] tabsText) {
        super(fm);
        mTabsText = tabsText;
    }

    @Override
    public Fragment getItem(int position) {
        DashPost_Fragment dpf;
        Bundle args = new Bundle();
        switch (position){
            case 0:
                args.putParcelableArrayList(AllConstants.ARRAYLIST_POST_PARCELABLE_KEY, Posts_Singleton.get().getPostsPublished());
                args.putInt(AllConstants.TYPELIST_KEY,AllConstants.FOR_PUBLISHED);
                break;
            case 1:
                args.putParcelableArrayList(AllConstants.ARRAYLIST_POST_PARCELABLE_KEY, Posts_Singleton.get().getPostsFixed());
                args.putInt(AllConstants.TYPELIST_KEY,AllConstants.FOR_FIXES);
                break;
        }
        dpf = DashPost_Fragment.newInstance(args);

        return null;
    }

    @Override
    public int getCount() {
        return mTabsText.length;
    }
}
