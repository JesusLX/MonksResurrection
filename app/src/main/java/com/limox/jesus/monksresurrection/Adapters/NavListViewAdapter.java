package com.limox.jesus.monksresurrection.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Repositories.NavItem_Repository;
import com.limox.jesus.monksresurrection.Repositories.Users_Repository;
import com.limox.jesus.monksresurrection.Utils.NavItem;

import java.util.ArrayList;

/**
 * Created by jesus on 3/01/17.
 * This class is an array adapter for the Navigator Drawer.
 * This class use the repository class NavItem_Repository to get the items to show
 */
public class NavListViewAdapter extends ArrayAdapter<NavItem> {

    public NavListViewAdapter(Context context, ArrayList<NavItem> navItems) {
        super(context, R.layout.nav_item, navItems);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        NavItemHolder holder;
        NavItem navItem = getItem(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.nav_item, parent, false);
            holder = new NavItemHolder();

            holder.mIcon = (ImageView) view.findViewById(R.id.ni_ivIco);
            holder.mTitle = (TextView) view.findViewById(R.id.ni_txvTitle);

            view.setTag(holder);
        } else
            holder = (NavItemHolder) view.getTag();

        if (navItem != null) {
            if (navItem.getIcon() == NavItem.NO_ICON)
                holder.mIcon.setVisibility(View.GONE);
            else {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageResource(navItem.getIcon());
            }
        }
        if (navItem != null) {
            holder.mTitle.setText(navItem.getTitle());
        }
        boolean isAdmin = false;
        if (navItem != null) {
            isAdmin = navItem.isAdmin();
        }
        if (isAdmin) {
            if (Users_Repository.get().getCurrentUser().isAdmin()) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.INVISIBLE);
            }
        }else
            view.setVisibility(View.VISIBLE);

        return view;
    }

    private class NavItemHolder {
        private ImageView mIcon;
        private TextView mTitle;
    }
}
