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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.limox.jesus.teambeta.Adapters.RecyclerView.ForumsListRecyclerAdapter;
import com.limox.jesus.teambeta.Interfaces.ForumsListManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Presenter.ForumsListManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;

import java.util.ArrayList;


public class ForumsListFragment extends Fragment implements ForumsListManagerPresenter.View, ForumsListRecyclerAdapter.ForumsListRecyclerAdapterListener {

    private RecyclerView rvForums;
    private Toolbar mToolbar;

    private ProgressBar mProgressB;

    private static int listType;

    private ForumsListRecyclerAdapter mAdapter;
    private ForumsListManagerPresenter mPresenter;
    private OnForumsListFragmentListener mCallback;

    private static ForumsListFragment mInstance;

    public ForumsListFragment() {
        // Required empty public constructor
    }

    public static ForumsListFragment getInstance(int list) {
        //if(mInstance==null)
        mInstance = new ForumsListFragment();
        listType = list;
        return mInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ForumsListRecyclerAdapter(getContext(), new ArrayList<Forum>(), this);
        mPresenter = new ForumsListManagerPresenterImpl(this);
    }

    private void search() {
        switch (listType) {
            case Forum.PARTAKER:
                mPresenter.searchForums(Users_Repository.get().getCurrentUser().getForumsWIParticipate());
                break;
            case Forum.ADMIN:
                mPresenter.searchForums(Users_Repository.get().getCurrentUser().getForumsAdmin());
                break;
            case Forum.OWN:
                mPresenter.searchForums(Users_Repository.get().getCurrentUser().getForumsOwn());
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_forums_list, container, false);
        rvForums = (RecyclerView) rootView.findViewById(R.id.fl_rvForums);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        mProgressB = (ProgressBar) rootView.findViewById(R.id.fl_load);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search();

        mToolbar.setTitle(R.string.select_forum);
        switch (listType) {
            case Forum.PARTAKER:
                mToolbar.setNavigationIcon(R.drawable.ic_action_forums);
                break;
            case Forum.ADMIN:
                mToolbar.setNavigationIcon(R.drawable.ic_action_admin);
                break;
            case Forum.OWN:
                mToolbar.setNavigationIcon(R.drawable.ic_action_own);
                break;
        }
        rvForums.setAdapter(mAdapter);
        rvForums.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //mProgressB.setP
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
        if (isAdded()) {
            if (forum == null) {
                Forum tmp = new Forum();
                if (listType != Forum.OWN) {
                    tmp.setName(getString(R.string.search_new_f));
                } else {
                    tmp.setName(getString(R.string.add_new_f));
                }
                mAdapter.add(tmp);
            } else {
                if (!mAdapter.contains(forum))
                    if (mAdapter.getItemCount() >= 1) {
                        mAdapter.add(forum, mAdapter.getItemCount() - 1);
                    } else {
                        Forum tmp = new Forum();
                        if (listType != Forum.OWN) {
                            tmp.setName(getString(R.string.search_new_f));
                        } else {
                            tmp.setName(getString(R.string.add_new_f));
                        }
                        mAdapter.add(tmp);
                        mAdapter.add(forum, mAdapter.getItemCount() - 1);
                    }
            }
            mProgressB.setVisibility(View.GONE);

        }

    }

    @Override
    public void onForumClicked(Forum forum) {
        if (forum.getKey() != null) {
            Users_Repository.get().setCurrentForum(forum);
            mCallback.startHomeFragment();

        } else {
            if (listType == Forum.OWN) {
                mCallback.startCreateForumFragment();
            } else {
                mCallback.startSearchFragment();
            }
        }
    }

    public interface OnForumsListFragmentListener {
        void startCreateForumFragment();

        void startHomeFragment();

        void startViewForum(Bundle forum);

        void startSearchFragment();
    }
}
