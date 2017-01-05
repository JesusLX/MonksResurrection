package com.limox.jesus.monksresurrection.Repositories;

import android.content.Context;

import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Utils.NavItem;

import java.util.ArrayList;

/**
 * Created by jesus on 3/01/17.
 * Class to get lists of NavItems
 */
public class NavItem_Repository {
    private Context mContext;

    public NavItem_Repository(Context context) {
        this.mContext = context;
    }

    public ArrayList<NavItem> getNavDrawerItems() {
        ArrayList<NavItem> mItems=new ArrayList<>();
        mItems.add(new NavItem(R.drawable.ic_add_black_24dp,mContext.getString(R.string.profile)));
        mItems.add(new NavItem(R.drawable.ic_add_black_24dp,mContext.getString(R.string.forum_bugs)));
        mItems.add(new NavItem(R.drawable.ic_add_black_24dp,mContext.getString(R.string.admins),true));
        mItems.add(new NavItem(mContext.getString(R.string.settings)));
        mItems.add(new NavItem(mContext.getString(R.string.help)));
        return mItems;
    }


}
