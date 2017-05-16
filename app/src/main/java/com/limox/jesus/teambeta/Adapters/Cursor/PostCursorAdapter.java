package com.limox.jesus.teambeta.Adapters.Cursor;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Provider.TeamBetaContract;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.AllConstants;

import java.util.Date;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by jesus on 11/11/16.
 */

public class PostCursorAdapter extends CursorAdapter {


    private OnPostViewHolderListener mCallback;

    public PostCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mCallback = (OnPostViewHolderListener) context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.item_collapsed_post,viewGroup,false);
        PostViewHolder holder = new PostViewHolder(rootView);
        rootView.setTag(holder);
        return rootView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final PostViewHolder holder = (PostViewHolder) view.getTag();
        Picasso.with(context).load(cursor.getString(TeamBetaContract.Posts.USER_ICON_KEY)).into(holder.mIvProfile_item);
        holder.mTxvPostTitle_item.setText(cursor.getString(TeamBetaContract.Posts.TITLE_KEY));
        holder.mTxvPostDescription_item.setText(cursor.getString(TeamBetaContract.Posts.TEXT_KEY));
        holder.postition = getCursor().getPosition();
        holder.mRlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                Post tmpPost = (Post)getItem(holder.postition);
                b.putParcelable(AllConstants.Keys.Parcelables.POST_PARCELABLE_KEY,tmpPost);
                b.putLong(AllConstants.Keys.Parcelables.POST_CREATION_DATE,tmpPost.getCreationDate().getTime());
                mCallback.startPostView(b);
            }
        });
        holder.mIvProfile_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt(AllConstants.Keys.SimpleBundle.ID_USER_KEY,getCursor().getInt(TeamBetaContract.Posts.USER_ID_KEY));
                mCallback.startUserProfile(b);
            }
        });
    }

    public interface OnPostViewHolderListener{
        void startUserProfile(Bundle user);
        void startPostView(Bundle post);
    }

    /**
     * This class is de ViewHolder of the Posts of the list
     */
    final static class PostViewHolder  {

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

    @Override
    public Object getItem(int position) {

        /*if (position > 0)
            position = position-1;*/
        getCursor().moveToPosition(position);
        Post post = new Post(getCursor().getString(TeamBetaContract.Posts.ID_KEY));
        post.setIdUser(getCursor().getString(TeamBetaContract.Posts.USER_ID_KEY));
        post.setIdForum(getCursor().getString(TeamBetaContract.Posts.FORUM_ID_KEY));
        post.setDeleted(getCursor().getInt(TeamBetaContract.Posts.DELETED_KEY)==1);
        int state =getCursor().getInt(TeamBetaContract.Posts.FIXED_KEY)+getCursor().getInt(TeamBetaContract.Posts.PUBLISHED_KEY);
        switch (state){
            case Post.FIXED:
                post.setState(Post.FIXED);
                break;
            case Post.NOT_PUBLISHED:
                post.setState(Post.NOT_PUBLISHED);
                break;
            case Post.PUBLISHED:
                post.setState(Post.PUBLISHED);
                break;

        }
        post.setCreationDate(new Date(Long.parseLong(getCursor().getString(TeamBetaContract.Posts.CREATION_DATE_KEY))));
        post.setDescription(getCursor().getString(TeamBetaContract.Posts.TEXT_KEY));
        post.setTitle(getCursor().getString(TeamBetaContract.Posts.TITLE_KEY));
        post.setTags(getCursor().getString(TeamBetaContract.Posts.TAGS_KEY));
        post.setScore(getCursor().getInt(TeamBetaContract.Posts.SCORE_KEY));

        return post;
    }
}
