package com.limox.jesus.teambeta.Adapters.TabsAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.limox.jesus.teambeta.Fragments.Forums.ForumsListFragment;
import com.limox.jesus.teambeta.Fragments.Forums.UsersListFragment;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.db.FirebaseContract;

/**
 * FragmentStatePager Adapter for the forum's users lists tabs
 * Created by jesus on 24/12/16.
 */
public class ForumUsersTabsAdapter extends FragmentStatePagerAdapter {
    String[] mTabNames;
    FragmentManager mSuppFM;
    String mIdUser;

    public ForumUsersTabsAdapter(Context context, FragmentManager supportFragmentManager, String idUser) {
        super(supportFragmentManager);
        mTabNames = new String[]{context.getString(R.string.users), context.getString(R.string.admins)};
        mIdUser = idUser;
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
                fragment = UsersListFragment.newInstance(mIdUser, FirebaseContract.User.NODE_FORUMS_PARTICIPATE);
                break;
            case 1:
                fragment = UsersListFragment.newInstance(mIdUser, FirebaseContract.User.NODE_FORUMS_ADMIN);
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
