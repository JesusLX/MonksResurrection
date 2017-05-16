package com.limox.jesus.teambeta.Adapters.TabsAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.limox.jesus.teambeta.Fragments.GenericPosts.DashPost_Fragment;
import com.limox.jesus.teambeta.Fragments.Messages.MessagesList_Fragment;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.AllConstants;

/**
 * Created by jesus on 24/12/16.
 */
public class ProfilePostTabsAdapter extends FragmentStatePagerAdapter {
    String[] mTabNames;
    FragmentManager mSuppFM;
    int mIdUser;
    public ProfilePostTabsAdapter(Context context, FragmentManager supportFragmentManager, int idUser) {
        super(supportFragmentManager);
        mTabNames = context.getResources().getStringArray(R.array.profile_post_tabs);
        mIdUser = idUser;
    }

    @Override
    public int getCount() {
        return mTabNames.length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle args = new Bundle();
        args.putInt(AllConstants.Keys.SimpleBundle.ID_USER_KEY, mIdUser);
        switch (position){
            case 0:
                args.putInt(AllConstants.TypeLists.TYPELIST_KEY,AllConstants.TypeLists.FOR_ALL);

                fragment = DashPost_Fragment.newInstance(args);
                break;
            case 1:
                args.putInt(AllConstants.TypeLists.TYPELIST_KEY,AllConstants.TypeLists.FOR_PUBLISHED);
                fragment = DashPost_Fragment.newInstance(args);
                break;
            case 2:
                fragment = new MessagesList_Fragment();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        return mTabNames[position];
    }
}
