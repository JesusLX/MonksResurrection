package com.limox.jesus.monksresurrection.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.Model.Post;
import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Singleton.Posts_Singleton;
import com.limox.jesus.monksresurrection.Singleton.Users_Singleton;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesus on 11/11/16.
 */

public class PostAdapterRecycler extends RecyclerView.Adapter<PostAdapterRecycler.PostViewHolder> {


    private List<Post> mPosts;
    private Context mContext;
    private int mTypeOfList;

    /**
     * @param context
     * @param typeOfList Type of list offer by AllConstants with the firs range
     */
    public PostAdapterRecycler(Context context, int typeOfList) {
        this.mContext = context;
        if (typeOfList == AllConstants.FOR_FIXES) {
            mPosts = new ArrayList<>(Posts_Singleton.getPosts_Singleton().getPostsFixed());
        } else if (typeOfList == AllConstants.FOR_PUBLISHED) {
            mPosts = new ArrayList<>(Posts_Singleton.getPosts_Singleton().getPostsPublished());
        } else {
            mPosts = new ArrayList<>(Posts_Singleton.getPosts_Singleton().getPostsNotPublished());
        }
        this.mTypeOfList = typeOfList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Here whe create the viewHolder, we take the collapsed post card
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collapsed_post, parent, false);
        return new PostViewHolder(item);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        // Initializing the components of the holder created above
        // If it will host a type of list of other we need to put in
        holder.mIvProfile_item.setImageResource(Users_Singleton.getUsers_Singleton().getUserById(mPosts.get(position).getIdUser()).getProfilePicture());
        holder.mTxvUserName_item.setText(Users_Singleton.getUsers_Singleton().getUserById(mPosts.get(position).getIdUser()).getNick());
        holder.mTxvPostTitle_item.setText(mPosts.get(position).getDescription());
        holder.mPost = mPosts.get(position);//TODO Poner que lo de arriba se rellene por este


    }

    @Override
    public int getItemCount() {
        int itemCount = 0;

        itemCount = mPosts.size();

        return itemCount;
    }

    public void getAllPosts() {
        mPosts.clear();
        if (mTypeOfList == AllConstants.FOR_PUBLISHED) {
            mPosts.addAll(Posts_Singleton.getPosts_Singleton().getPostsPublished());
        } else if (mTypeOfList == AllConstants.FOR_FIXES) {
            mPosts.addAll(Posts_Singleton.getPosts_Singleton().getPostsPublished());
        } else
            mPosts.addAll(Posts_Singleton.getPosts_Singleton().getPostsNotPublished());
    }

    /**
     * This class is de ViewHolder of the Posts of the list
     */
    static class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView mIvProfile_item;
        TextView mTxvUserName_item;
        TextView mTxvPostTitle_item;
        public Post mPost;

        PostViewHolder(View itemView) {
            super(itemView);
            mIvProfile_item = (ImageView) itemView.findViewById(R.id.cp_iVProfile);
            mTxvUserName_item = (TextView) itemView.findViewById(R.id.cp_txvUserName);
            mTxvPostTitle_item = (TextView) itemView.findViewById(R.id.cp_txvPostTitle);
        }
    }
}
