package com.limox.jesus.monksresurrection.Repositories;

import android.content.Context;

import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Utils.NavItem;

import java.util.ArrayList;

/**
 * Created by jesus on 3/01/17.
 */
public class NavItem_Repository {
    private ArrayList<NavItem> mItems;
    private Context mContext;

    public NavItem_Repository(Context context) {
        this.mContext = context;
        addItem(new NavItem(R.drawable.ic_add_black_24dp,mContext.getString(R.string.profile)));
        addItem(new NavItem(R.drawable.ic_add_black_24dp,mContext.getString(R.string.forum_bugs)));
        addItem(new NavItem(R.drawable.ic_add_black_24dp,mContext.getString(R.string.admins),true));
        addItem(new NavItem(mContext.getString(R.string.settings)));
        addItem(new NavItem(mContext.getString(R.string.help)));
    }

    public void addItem(NavItem newItem){
        if (mItems == null)
            mItems = new ArrayList<>();
        mItems.add(newItem);
    }

    public ArrayList<NavItem> getItems() {
        return mItems;
    }

    public void setmItems(ArrayList<NavItem> mItems) {
        this.mItems = mItems;
    }
}
