package com.limox.jesus.monksresurrection.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

/**
 * Created by jesus on 3/01/17.
 * This class is an array adapter for the Navigator Drawer.
 * This class use the repository class NavItem_Repository to get the items to show
 */
public class NavListViewAdapter extends ArrayAdapter<NavItem> {

    public NavListViewAdapter(Context context) {
        super(context, R.layout.nav_item, new NavItem_Repository(context).getItems());
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        NavItemHolder holder = null;
        NavItem navItem = getItem(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.nav_item, parent, false);
            holder = new NavItemHolder();

            holder.mIcon = (ImageView) view.findViewById(R.id.ni_ivIco);
            holder.mTitle = (TextView) view.findViewById(R.id.ni_txvTitle);

            view.setTag(holder);
        } else
            holder = (NavItemHolder) view.getTag();

        holder.mIcon.setImageResource(navItem.getIcon());
        holder.mTitle.setText(navItem.getTitle());
        if (navItem.isAdmin() == true) {
            if (!Users_Repository.get().getCurrentUser().isAdmin()) {
                view.setVisibility(View.INVISIBLE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    private class NavItemHolder {
        private ImageView mIcon;
        private TextView mTitle;
    }
}
