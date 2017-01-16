package com.limox.jesus.monksresurrection.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.limox.jesus.monksresurrection.Fragments.GenericPosts.DashPost_Fragment;
import com.limox.jesus.monksresurrection.Model.Post;
import com.limox.jesus.monksresurrection.Repositories.Posts_Repository;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

import java.util.Collections;

/**
 * Created by usuario on 22/12/16.
 */
public class PostTabsAdapter extends FragmentStatePagerAdapter {

    String[] mTabsText;
    public PostTabsAdapter(FragmentManager fm, String[] tabsText) {
        super(fm);
        mTabsText = tabsText;
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
        return mTabsText.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsText[position];
    }
}
