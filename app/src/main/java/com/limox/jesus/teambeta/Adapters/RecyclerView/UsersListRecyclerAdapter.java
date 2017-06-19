package com.limox.jesus.teambeta.Adapters.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.UIUtils;

import java.util.ArrayList;

/**
 * Recycler Adapter to show User's lists
 * Created by Jesus on 26/04/2017.
 */
public class UsersListRecyclerAdapter extends RecyclerView.Adapter<UsersListRecyclerAdapter.UsersHolder> {

    private ArrayList<User> users;
    private Context context;
    private ForumsListRecyclerAdapterListener mCallback;

    public void addAll(ArrayList<User> users) {
        if (this.users == null)
            this.users = new ArrayList<>();
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return users.size() == 0;
    }

    public interface ForumsListRecyclerAdapterListener {
        void onUserClicked(Bundle user);
    }

    public UsersListRecyclerAdapter(Context context, ArrayList<User> users, ForumsListRecyclerAdapterListener listener) {
        this.context = context;
        this.users = users;
        this.mCallback = listener;
    }

    @Override
    public UsersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UsersHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tiny, parent, false));
    }

    @Override
    public void onBindViewHolder(UsersHolder holder, int position) {
        holder.setUser(users.get(position).optBundle());
        UIUtils.loadImage(context, users.get(position).getProfilePicture(), holder.ivLogo);
        holder.txvTitle.setText(users.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setData(ArrayList<User> data) {
        this.users = data;
        notifyDataSetChanged();
    }

    public void add(User user) {
        this.users.add(user);
        notifyItemInserted(this.users.size() > 0 ? this.users.size() - 1 : 0);
    }

    public void add(User user, int position) {
        this.users.add(position, user);
        notifyItemInserted(position);
    }

    class UsersHolder extends RecyclerView.ViewHolder {

        ImageView ivLogo;
        TextView txvTitle;
        RelativeLayout rlContainer;
        Bundle user;

        public UsersHolder(View itemView) {
            super(itemView);
            ivLogo = (ImageView) itemView.findViewById(R.id.if_ivIcon);
            txvTitle = (TextView) itemView.findViewById(R.id.if_txvTitle);
            rlContainer = (RelativeLayout) itemView.findViewById(R.id.if_forumContainer);
            rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onUserClicked(user);
                }
            });
        }

        public void setUser(Bundle user) {
            this.user = user;
        }
    }
}
