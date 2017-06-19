package com.limox.jesus.teambeta.Adapters.TabsAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.limox.jesus.teambeta.Fragments.Forums.ForumsListFragment;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.R;

/**
 * FragmentStatePager Adapter for the user's profile at the forums section views
 * Created by jesus on 24/12/16.
 */
public class ProfileForumsTabsAdapter extends FragmentStatePagerAdapter {
    String[] mTabNames;
    FragmentManager mSuppFM;
    Bundle mUser;

    public ProfileForumsTabsAdapter(Context context, FragmentManager supportFragmentManager, Bundle user) {
        super(supportFragmentManager);
        mTabNames = new String[]{context.getString(R.string.own), context.getString(R.string.admin), context.getString(R.string.partaker)};
        mUser = user;
    }

    @Override
    public int getCount() {
        return mTabNames.length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = ForumsListFragment.getInstance(Forum.OWN, Forum.TYPE_TINTY, mUser);
                break;
            case 1:
                fragment = ForumsListFragment.getInstance(Forum.ADMIN, Forum.TYPE_TINTY, mUser);
                break;
            case 2:
                fragment = ForumsListFragment.getInstance(Forum.PARTAKER, Forum.TYPE_TINTY, mUser);
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
