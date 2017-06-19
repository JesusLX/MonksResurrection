package com.limox.jesus.teambeta.Adapters.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.limox.jesus.teambeta.R;

import java.util.ArrayList;

/**
 * Recycler Adapter to show search's results
 * Created by jesus on 11/11/16.
 */
public class SearchsRecyclerAdapter extends RecyclerView.Adapter<SearchsRecyclerAdapter.PostViewHolder> {


    private ArrayList<String> mTags;
    private OnTagsViewHolderListener mCallback;

    public interface OnTagsViewHolderListener {
        void onItemPressed(String tag);

    }

    /**
     * @param tags     Arrylist of tags to show
     * @param listener listener
     */
    public SearchsRecyclerAdapter(ArrayList<String> tags, OnTagsViewHolderListener listener) {

        this.mTags = tags;
        if (tags == null)
            this.mTags = new ArrayList<>();
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
        holder.mTxvTag.setText(mTags.get(position));
        holder.mLlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onItemPressed(mTags.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public void setData(ArrayList<String> tags) {
        this.mTags.clear();
        this.mTags.addAll(tags);
        notifyDataSetChanged();
    }

    public ArrayList<String> getAllTabs() {
        return mTags;
    }

    /**
     * This class is de ViewHolder of the Posts of the list
     */
    final static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView mTxvTag;
        LinearLayout mLlContainer;

        PostViewHolder(View itemView) {
            super(itemView);
            mTxvTag = (TextView) itemView.findViewById(R.id.txvTag);
            mLlContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
        }

    }
}
