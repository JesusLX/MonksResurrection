package com.limox.jesus.teambeta.Fragments.Forums;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limox.jesus.teambeta.Adapters.ForumsListRecyclerAdapter;
import com.limox.jesus.teambeta.Interfaces.ForumsListManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Presenter.ForumsListManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;

import java.util.ArrayList;


public class ForumsListFragment extends Fragment implements ForumsListManagerPresenter.View, ForumsListRecyclerAdapter.ForumsListRecyclerAdapterListener {

    private RecyclerView rvForums;
    private Toolbar mToolbar;

    private ForumsListRecyclerAdapter mAdapter;
    private ForumsListManagerPresenter mPresenter;
    private OnForumsListFragmentListener mCallback;


    public ForumsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ForumsListRecyclerAdapter(getContext(), new ArrayList<Forum>(), this);
        mPresenter = new ForumsListManagerPresenterImpl(this);
        mPresenter.searchForums(Users_Repository.get().getCurrentUser().getForumsWIParticipates());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_forums_list, container, false);
        rvForums = (RecyclerView) rootView.findViewById(R.id.fl_rvForums);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setTitle(R.string.select_forum);
        rvForums.setAdapter(mAdapter);
        rvForums.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnForumsListFragmentListener) {
            mCallback = (OnForumsListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnForumsListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onForumsObtained(ArrayList<Forum> forums) {
        mAdapter.setData(forums);
    }

    @Override
    public void addForum(Forum forum) {
        if (forum == null) {
            mAdapter.add(new Forum());
        } else {
            if (mAdapter.getItemCount() >= 1) {
                mAdapter.add(forum, mAdapter.getItemCount() - 1);
            } else {
                mAdapter.add(new Forum());
                mAdapter.add(forum, mAdapter.getItemCount() - 1);
            }
        }
    }

    @Override
    public void onItemClicked(Forum forum) {
        if (forum.getKey() != null) {
            Users_Repository.get().setCurrentForum(forum);
            mCallback.startHomeFragment();
        } else {
            mCallback.startCreateForumFragment();
        }
    }

    public interface OnForumsListFragmentListener {
        void startCreateForumFragment();

        void startHomeFragment();
    }
}
