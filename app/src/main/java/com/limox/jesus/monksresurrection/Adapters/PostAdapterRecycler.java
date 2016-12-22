package com.limox.jesus.monksresurrection.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.Model.Post;
import com.limox.jesus.monksresurrection.Model.User;
import com.limox.jesus.monksresurrection.PostView_Activity;
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

    public interface OnPostViewHolderListener{
        void startUserProfile(Bundle user);
        void startPostView(Bundle post);
    }

    OnPostViewHolderListener mCallback;

    /**
     * @param context
     * @param typeOfList Type of list offer by AllConstants with the firs range
     */
    public PostAdapterRecycler(Context context, int typeOfList) {
        this.mContext = context;
        mCallback = (OnPostViewHolderListener) context;
        if (typeOfList == AllConstants.FOR_FIXES) {
            mPosts = new ArrayList<>(Posts_Singleton.get().getPostsFixed());
        } else if (typeOfList == AllConstants.FOR_PUBLISHED) {
            mPosts = new ArrayList<>(Posts_Singleton.get().getPostsPublished());
        } else {
            mPosts = new ArrayList<>(Posts_Singleton.get().getPostsNotPublished());
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
    public void onBindViewHolder(PostViewHolder holder, final int position) {
        // Initializing the components of the holder created above
        // If it will host a type of list of other we need to put in
        holder.mPost = mPosts.get(position);
        holder.mIvProfile_item.setImageResource(Users_Singleton.get().getUserById(holder.mPost.getIdUser()).getProfilePicture());
        holder.mTxvPostTitle_item.setText(holder.mPost.getTitle());
        holder.mTxvPostDescription_item.setText(holder.mPost.getDescriptionShorted());
        holder.mIvProfile_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putParcelable(AllConstants.USER_PARCELABLE_KEY,Users_Singleton.get().getUserById(mPosts.get(position).getIdUser()));
                mCallback.startUserProfile(args);
            }
        });
        holder.mRlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putParcelable(AllConstants.POST_PARCELABLE_KEY,mPosts.get(position));
                mCallback.startPostView(args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public void getAllPosts() {
        // Clear the list
        mPosts.clear();
        // if it will be a list of posts published
        if (mTypeOfList == AllConstants.FOR_PUBLISHED) {
            mPosts.addAll(Posts_Singleton.get().getPostsPublished());

            // or a list of posts fixes
        } else if (mTypeOfList == AllConstants.FOR_FIXES) {
            mPosts.addAll(Posts_Singleton.get().getPostsPublished());

            // Or a simple list of all the posts
        } else
            mPosts.addAll(Posts_Singleton.get().getPostsNotPublished());
    }

    /**
     * This class is de ViewHolder of the Posts of the list
     */
    public final static class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView mIvProfile_item;
        TextView mTxvPostDescription_item;
        TextView mTxvPostTitle_item;
        RelativeLayout mRlContainer;
        public Post mPost;



        PostViewHolder(View itemView) {
            super(itemView);
            mIvProfile_item = (ImageView) itemView.findViewById(R.id.cp_iVProfile);
            mTxvPostDescription_item = (TextView) itemView.findViewById(R.id.cp_txvPostContent);
            mTxvPostTitle_item = (TextView) itemView.findViewById(R.id.cp_txvPostTitle);
            mRlContainer = (RelativeLayout) itemView.findViewById(R.id.cp_rlContainer);


        }
    }
}
