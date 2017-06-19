package com.limox.jesus.teambeta.Adapters.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.limox.jesus.teambeta.Model.Chat;
import com.limox.jesus.teambeta.Model.Commentary;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.AllConstants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Recycler Adapter to show post's commentaries lists
 * Created by jesus on 11/11/16.
 */
public class CommentaryRecyclerViewAdapter extends RecyclerView.Adapter<CommentaryRecyclerViewAdapter.CommentaryHolder> {


    private ArrayList<Commentary> mComments;
    private OnCommentaryHolderListener mCallback;
    private Context context;


    public interface OnCommentaryHolderListener {
        void onUserPressed(Bundle userId);

    }

    /**
     * @param comments Arrylist of tags to show
     * @param listener listener
     * @param context
     */
    public CommentaryRecyclerViewAdapter(ArrayList<Commentary> comments, OnCommentaryHolderListener listener, Context context) {

        this.mComments = comments;
        this.context = context;
        if (comments == null)
            this.mComments = new ArrayList<>();
        mCallback = listener;
    }

    @Override
    public CommentaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Here whe create the viewHolder, we take the collapsed post card
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commentary, parent, false);

        return new CommentaryHolder(item);
    }

    @Override
    public void onBindViewHolder(final CommentaryHolder holder, int position) {
        holder.mTxvCommentary.setText(mComments.get(position).getContent());
        holder.mTxvUserName.setText(mComments.get(position).getUserName());
        Glide.with(context).load(mComments.get(position).getUserImgProf()).into(holder.mIvUser);
        holder.mIvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString(AllConstants.Keys.SimpleBundle.ID_USER_KEY, mComments.get(holder.getAdapterPosition()).getKeyUser());
                mCallback.onUserPressed(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void add(Commentary commentary) {
        this.mComments.add(0, commentary);
        notifyItemInserted(0);
    }

    public void addAll(ArrayList<Commentary> comments) {
        this.mComments.clear();
        this.mComments.addAll(comments);
        notifyDataSetChanged();
    }

    public ArrayList<Commentary> getAllCommetns() {
        return mComments;
    }

    /**
     * This class is de ViewHolder of the Posts of the list
     */
    final static class CommentaryHolder extends RecyclerView.ViewHolder {

        TextView mTxvCommentary;
        TextView mTxvUserName;
        CircleImageView mIvUser;

        CommentaryHolder(View itemView) {
            super(itemView);
            mTxvCommentary = (TextView) itemView.findViewById(R.id.txvCommentary);
            mTxvUserName = (TextView) itemView.findViewById(R.id.txvUserName);
            mIvUser = (CircleImageView) itemView.findViewById(R.id.civUser);
        }

    }
}
