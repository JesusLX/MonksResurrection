package com.limox.jesus.teambeta.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;

import java.util.ArrayList;

/**
 * Created by jesus on 11/11/16.
 */

public class PostAdapterRecycler extends RecyclerView.Adapter<PostAdapterRecycler.PostViewHolder> {


    private ArrayList<Post> mPosts;
    private Context mContext;
    private OnPostViewHolderListener mCallback;

    public interface OnPostViewHolderListener{
        void startUserProfile(Bundle user);
        void startPostView(Bundle post);

    }

    /**
     *
     * @param posts Arrylist of posts to show
     * @param context context of the app
     */
    public PostAdapterRecycler(ArrayList<Post> posts, Context context) {

        this.mPosts = posts;
        mCallback = (OnPostViewHolderListener) context;
        this.mContext = context;
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
        holder.mIvProfile_item.setImageResource(Users_Repository.get().getUserById(holder.mPost.getIdUser()).getProfilePicture());
        holder.mTxvPostTitle_item.setText(holder.mPost.getTitle());
        holder.mTxvPostDescription_item.setText(holder.mPost.getDescriptionShorted());
        // TODO Poner que se vea solo si el usuario le ha dado like
       // holder.mIvwPoints_item.setVisibility(View.VISIBLE);
        holder.mIvProfile_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putParcelable(AllConstants.USER_PARCELABLE_KEY, Users_Repository.get().getUserById(mPosts.get(position).getIdUser()));
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

    public ArrayList<Post> getAllPosts() {
        return mPosts;
    }

    /**
     * This class is de ViewHolder of the Posts of the list
     */
    final static class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView mIvProfile_item;
        TextView mTxvPostDescription_item;
        TextView mTxvPostTitle_item;
        ImageView mIvwPoints_item;
        RelativeLayout mRlContainer;
        Post mPost;



        PostViewHolder(View itemView) {
            super(itemView);
            mIvProfile_item = (ImageView) itemView.findViewById(R.id.cp_iVProfile);
            mTxvPostDescription_item = (TextView) itemView.findViewById(R.id.cp_txvPostContent);
            mTxvPostTitle_item = (TextView) itemView.findViewById(R.id.cp_txvPostTitle);
            mIvwPoints_item = (ImageView) itemView.findViewById(R.id.cp_icstar);
            mRlContainer = (RelativeLayout) itemView.findViewById(R.id.cp_rlContainer);
        }

    }
}
