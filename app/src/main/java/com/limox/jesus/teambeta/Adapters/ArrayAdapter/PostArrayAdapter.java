package com.limox.jesus.teambeta.Adapters.ArrayAdapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Provider.TeamBetaContract;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.db.FirebaseContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by jesus on 11/11/16.
 */

public class PostArrayAdapter extends ArrayAdapter<Post> {

    private OnPostViewHolderListener mCallback;

/*    public PostArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Posts> objects) {
        super(context, resource, objects);
    }*/

    public PostArrayAdapter(@NonNull Context context, OnPostViewHolderListener listener) {
        super(context, R.layout.item_collapsed_post, new ArrayList<Post>());
        this.mCallback = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        PostViewHolder holder = null;
        if (rootView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rootView = inflater.inflate(R.layout.item_collapsed_post, parent, false);
            holder = new PostViewHolder(rootView);
            rootView.setTag(holder);
        } else {
            holder = (PostViewHolder) rootView.getTag();
        }
        final PostViewHolder finalHolder2 = holder;
        FirebaseDatabase.getInstance().getReference().child(FirebaseContract.User.ROOT_NODE).child(getItem(position).getIdUser()).child(FirebaseContract.User.NODE_PHOTO_URL).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Picasso.with(getContext()).load((String) dataSnapshot.getValue()).into(finalHolder2.mIvProfile_item);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.mTxvPostTitle_item.setText(getItem(position).getTitle());
        holder.mTxvPostDescription_item.setText(getItem(position).getDescriptionShorted());
        holder.postition = position;
        final PostViewHolder finalHolder = holder;
        holder.mRlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                Post tmpPost = getItem(finalHolder.postition);
                b.putParcelable(AllConstants.Keys.Parcelables.POST_PARCELABLE_KEY, tmpPost);
                b.putLong(AllConstants.Keys.Parcelables.POST_CREATION_DATE, tmpPost.getCreationDate().getTime());
                mCallback.startPostView(b);
            }
        });
        final PostViewHolder finalHolder1 = holder;
        holder.mIvProfile_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString(AllConstants.Keys.SimpleBundle.ID_USER_KEY, getItem(finalHolder1.postition).getIdUser());
                mCallback.startUserProfile(b);
            }
        });
        return rootView;

    }

    public void setData(ArrayList<Post> data) {
        clear();
        addAll(data);
    }

    public interface OnPostViewHolderListener {
        void startUserProfile(Bundle user);

        void startPostView(Bundle post);
    }

    /**
     * This class is de ViewHolder of the Posts of the list
     */
    final static class PostViewHolder {

        ImageView mIvProfile_item;
        TextView mTxvPostDescription_item;
        TextView mTxvPostTitle_item;
        ImageView mIvwPoints_item;
        RelativeLayout mRlContainer;
        int postition;


        PostViewHolder(View itemView) {
            mIvProfile_item = (ImageView) itemView.findViewById(R.id.cp_iVProfile);
            mTxvPostDescription_item = (TextView) itemView.findViewById(R.id.cp_txvPostContent);
            mTxvPostTitle_item = (TextView) itemView.findViewById(R.id.cp_txvPostTitle);
            mIvwPoints_item = (ImageView) itemView.findViewById(R.id.cp_icstar);
            mRlContainer = (RelativeLayout) itemView.findViewById(R.id.cp_rlContainer);

        }

    }

    /*@Override
    public Object getItem(int position) {

        *//*if (position > 0)
            position = position-1;*//*
        getCursor().moveToPosition(position);
        Posts post = new Posts(getCursor().getInt(TeamBetaContract.Posts.ID_KEY));
        post.setIdUser(getCursor().getString(TeamBetaContract.Posts.USER_ID_KEY));
        post.setDeleted(getCursor().getInt(TeamBetaContract.Posts.DELETED_KEY)==1);
        int state =getCursor().getInt(TeamBetaContract.Posts.FIXED_KEY)+getCursor().getInt(TeamBetaContract.Posts.PUBLISHED_KEY);
        switch (state){
            case Posts.FIXED:
                post.setState(Posts.FIXED);
                break;
            case Posts.NOT_PUBLISHED:
                post.setState(Posts.NOT_PUBLISHED);
                break;
            case Posts.PUBLISHED:
                post.setState(Posts.PUBLISHED);
                break;

        }
        post.setCreationDate(new Date(Long.parseLong(getCursor().getString(TeamBetaContract.Posts.CREATION_DATE_KEY))));
        post.setDescription(getCursor().getString(TeamBetaContract.Posts.TEXT_KEY));
        post.setTitle(getCursor().getString(TeamBetaContract.Posts.TITLE_KEY));
        post.setTags(getCursor().getString(TeamBetaContract.Posts.TAGS_KEY));
        post.setScore(getCursor().getInt(TeamBetaContract.Posts.SCORE_KEY));

        return post;
    }*/
}
