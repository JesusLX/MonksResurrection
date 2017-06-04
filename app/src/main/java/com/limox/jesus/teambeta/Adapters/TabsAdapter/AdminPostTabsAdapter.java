package com.limox.jesus.teambeta.Adapters.TabsAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.limox.jesus.teambeta.Fragments.GenericPosts.DashPost_Fragment;
import com.limox.jesus.teambeta.Fragments.Chats.MessagesList_Fragment;
import com.limox.jesus.teambeta.Utils.AllConstants;

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
                args.putInt(AllConstants.TypeLists.TYPELIST_KEY,AllConstants.TypeLists.FOR_NONPUBLISHED);
                dpf = DashPost_Fragment.newInstance(args);
                break;
            case 1:
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
