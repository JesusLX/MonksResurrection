package com.limox.jesus.teambeta.Adapters.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.limox.jesus.teambeta.Model.Chat;
import com.limox.jesus.teambeta.R;

import java.util.ArrayList;

/**
 * Created by jesus on 11/11/16.
 */

public class ChatsRecyclerAdapter extends RecyclerView.Adapter<ChatsRecyclerAdapter.PostViewHolder> {


    private ArrayList<Chat> mChats;
    private OnChatsViewHolderListener mCallback;


    public interface OnChatsViewHolderListener {
        void onChatPressed(Chat chat);

    }

    /**
     * @param chats    Arrylist of tags to show
     * @param listener listener
     */
    public ChatsRecyclerAdapter(ArrayList<Chat> chats, OnChatsViewHolderListener listener) {

        this.mChats = chats;
        if (chats == null)
            this.mChats = new ArrayList<>();
        mCallback = listener;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Here whe create the viewHolder, we take the collapsed post card
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);

        return new PostViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {
        holder.mTxvChatName.setText(mChats.get(position).optName());
        holder.mIvIcon.setImageResource(R.drawable.ic_action_chat);
        holder.mLlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onChatPressed(mChats.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public void add(Chat chat) {
        this.mChats.add(chat);
        notifyItemInserted(mChats.size() - 1);
    }

    public void addAll(ArrayList<Chat> chats) {
        this.mChats.clear();
        this.mChats.addAll(chats);
        notifyDataSetChanged();
    }

    public ArrayList<Chat> getAllTabs() {
        return mChats;
    }

    /**
     * This class is de ViewHolder of the Posts of the list
     */
    final static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView mTxvChatName;
        ImageView mIvIcon;
        LinearLayout mLlContainer;

        PostViewHolder(View itemView) {
            super(itemView);
            mTxvChatName = (TextView) itemView.findViewById(R.id.txvTag);
            mIvIcon = (ImageView) itemView.findViewById(R.id.ivItemIcon);
            mLlContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
        }

    }
}
