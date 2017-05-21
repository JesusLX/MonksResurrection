package com.limox.jesus.teambeta.Adapters.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by Jesus on 26/04/2017.
 */
public class ForumsListRecyclerAdapter extends RecyclerView.Adapter<ForumsListRecyclerAdapter.ForumsHolder> {

    private ArrayList<Forum> forums;
    private Context context;
    private ForumsListRecyclerAdapterListener mCallback;

    public boolean contains(Forum forum) {
        return this.forums.contains(forum);
    }

    public void addAll(ArrayList<Forum> forums) {
        if (this.forums == null)
            this.forums = new ArrayList<>();
        this.forums.clear();
        this.forums.addAll(forums);
        notifyDataSetChanged();
    }

    public interface ForumsListRecyclerAdapterListener {
        void onForumClicked(Forum forum);
    }

    public ForumsListRecyclerAdapter(Context context, ArrayList<Forum> forums, ForumsListRecyclerAdapterListener listener) {
        this.context = context;
        this.forums = forums;
        this.mCallback = listener;
    }

    @Override
    public ForumsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ForumsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum, parent, false));
    }

    @Override
    public void onBindViewHolder(ForumsHolder holder, int position) {
        holder.setForum(forums.get(position));
        if (forums.get(position).getKey() != null) {
            UIUtils.loadImage(context, forums.get(position).getImgUrl(), holder.ivLogo);
            holder.txvTitle.setText(forums.get(position).getName());
        } else {
            holder.ivLogo.setImageDrawable(context.getDrawable(R.drawable.ic_action_add));
            holder.txvTitle.setText(forums.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return forums.size();
    }

    public void setData(ArrayList<Forum> data) {
        this.forums = data;
        notifyDataSetChanged();
    }

    public void add(Forum forum) {
        this.forums.add(forum);
        notifyItemInserted(this.forums.size() > 0 ? this.forums.size() - 1 : 0);
    }

    public void add(Forum forum, int position) {
        this.forums.add(position, forum);
        notifyItemInserted(position);
    }

    class ForumsHolder extends RecyclerView.ViewHolder {

        ImageView ivLogo;
        TextView txvTitle;
        RelativeLayout rlContainer;
        Forum forum;

        public ForumsHolder(View itemView) {
            super(itemView);
            ivLogo = (ImageView) itemView.findViewById(R.id.if_ivIcon);
            txvTitle = (TextView) itemView.findViewById(R.id.if_txvTitle);
            rlContainer = (RelativeLayout) itemView.findViewById(R.id.if_forumContainer);
            rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onForumClicked(forum);
                }
            });
        }

        public void setForum(Forum forum) {
            this.forum = forum;
        }
    }
}
