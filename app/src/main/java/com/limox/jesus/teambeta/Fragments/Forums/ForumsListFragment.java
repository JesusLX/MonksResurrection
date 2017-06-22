package com.limox.jesus.teambeta.Fragments.Forums;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Presenter.ForumsListManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.UIUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ForumsListFragment extends Fragment implements ForumsListManagerPresenter.View, ForumsListRecyclerAdapter.ForumsListRecyclerAdapterListener {

    private RecyclerView rvForums;
    private Toolbar mToolbar;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar mProgressB;
    private TextView mTxvEmptyList;
    private FloatingActionButton mFabAdd;
    private User mUser;

    private int mItemStyle;
    private int mTypeList;

    private ForumsListRecyclerAdapter mAdapter;
    private ForumsListManagerPresenter mPresenter;
    private OnForumsListFragmentListener mCallback;


    public ForumsListFragment() {
        // Required empty public constructor
    }

    public static ForumsListFragment getInstance(int type_list, int item_style, Bundle user) {
        //if(mInstance==null)
        ForumsListFragment mInstance = new ForumsListFragment();
        Bundle b = new Bundle();
        b.putBundle(AllConstants.Keys.Parcelables.USER_PARCELABLE_KEY, user);
        b.putIntArray(AllConstants.Keys.SimpleBundle.FORUM_LIST_ARG, new int[]{item_style, type_list});
        mInstance.setArguments(b);
        return mInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mItemStyle = getArguments().getIntArray(AllConstants.Keys.SimpleBundle.FORUM_LIST_ARG)[0];
            mTypeList = getArguments().getIntArray(AllConstants.Keys.SimpleBundle.FORUM_LIST_ARG)[1];
            mUser = getArguments().getBundle(AllConstants.Keys.Parcelables.USER_PARCELABLE_KEY).getParcelable(AllConstants.Keys.Parcelables.USER_PARCELABLE_KEY);
        }
        if (mUser == null)
            mUser = Users_Repository.get().getCurrentUser();
        mAdapter = new ForumsListRecyclerAdapter(getContext(), new ArrayList<Forum>(), this, mItemStyle == Forum.TYPE_COLLAPSED ? R.layout.item_forum : R.layout.item_tiny);
        mPresenter = new ForumsListManagerPresenterImpl(this);

        setRetainInstance(true);
        mAdapter.clear();
    }


    private void search() {
        if (UIUtils.isNetworkAvailable(getContext())) {
            mProgressB.setVisibility(View.VISIBLE);
            switch (mTypeList) {
                case Forum.PARTAKER:
                    mPresenter.searchForums(mUser.getForumsWIParticipate());
                    break;
                case Forum.ADMIN:
                    mPresenter.searchForums(mUser.getForumsAdmin());
                    break;
                case Forum.OWN:
                    mPresenter.searchForums(mUser.getForumsOwn());
                    break;
            }
        } else {
            UIUtils.toast(getContext(), getString(R.string.no_internet));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_forums_list, container, false);
        rvForums = (RecyclerView) rootView.findViewById(R.id.fl_rvForums);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
        mProgressB = (ProgressBar) rootView.findViewById(R.id.fl_load);
        mTxvEmptyList = (TextView) rootView.findViewById(R.id.txvEmptyList);
        mFabAdd = (FloatingActionButton) rootView.findViewById(R.id.fl_fabAdd);
        search();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        search();
                    }
                }
        );
        mToolbar.setTitle(R.string.select_forum);
        switch (mTypeList) {
            case Forum.PARTAKER:
                mToolbar.setNavigationIcon(R.drawable.ic_action_forums);
                mFabAdd.setImageResource(R.drawable.ic_action_search);
                break;
            case Forum.ADMIN:
                mToolbar.setNavigationIcon(R.drawable.ic_action_admin);
                mFabAdd.setImageResource(R.drawable.ic_action_search);
                break;
            case Forum.OWN:
                mToolbar.setNavigationIcon(R.drawable.ic_action_own);
                mFabAdd.setImageResource(R.drawable.ic_action_add);
                break;
        }
        if (mUser.getId().equals(Users_Repository.get().getCurrentUser().getId())) {
            mFabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTypeList == Forum.OWN) {
                        mCallback.startCreateForumFragment();
                    } else {
                        mCallback.startSearchFragment();
                    }
                }
            });
            mFabAdd.setVisibility(View.VISIBLE);
        } else
            mFabAdd.setVisibility(View.GONE);

        rvForums.setAdapter(mAdapter);
        if (mItemStyle == Forum.TYPE_COLLAPSED)
            rvForums.setLayoutManager(new GridLayoutManager(getContext(), 2));
        else {
            rvForums.setLayoutManager(new LinearLayoutManager(getContext()));
            mToolbar.setVisibility(View.GONE);
        }
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
        refreshLayout.setRefreshing(false);
        mAdapter.setData(forums);
    }

    @Override
    public void addForum(Forum forum) {
        if (isAdded()) {
            refreshLayout.setRefreshing(false);
            if (forum != null)
                if (!mAdapter.contains(forum)) {
                    mAdapter.add(forum, mAdapter.getItemCount());
                }
            onEmptyList(mAdapter.getItemCount() == 0);

            mProgressB.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError() {

    }


    private void addBlankForum() {

        Forum tmp = new Forum();
        if (mTypeList != Forum.OWN) {
            tmp.setName(getString(R.string.search_new_f));
        } else {
            tmp.setName(getString(R.string.add_new_f));
        }

        mAdapter.add(tmp);
    }

    private void onEmptyList(boolean isEmpty) {
        if (isEmpty) {
            mTxvEmptyList.setVisibility(View.VISIBLE);
        } else {
            mTxvEmptyList.setVisibility(View.GONE);
        }
    }

    @Override
    public void onForumClicked(Forum forum) {
        if (forum.getKey() != null) {
            Users_Repository.get().setCurrentForum(forum);
            // mCallback.startHomeFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AllConstants.Keys.Parcelables.FORUM, forum);
            mCallback.startViewForum(bundle);
        } else {
            if (mTypeList == Forum.OWN) {
                mCallback.startCreateForumFragment();
            } else {
                mCallback.startSearchFragment();
            }
        }
    }

    public interface OnForumsListFragmentListener {
        void startCreateForumFragment();

        void startViewForum(Bundle forum);

        void startSearchFragment();
    }
}
