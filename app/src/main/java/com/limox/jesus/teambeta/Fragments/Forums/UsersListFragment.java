package com.limox.jesus.teambeta.Fragments.Forums;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limox.jesus.teambeta.Adapters.RecyclerView.UsersListRecyclerAdapter;
import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Presenter.UserManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.AllConstants;

import java.util.ArrayList;


public class UsersListFragment extends Fragment implements UserManagerPresenter.View, UsersListRecyclerAdapter.ForumsListRecyclerAdapterListener {

    private String mForumId;
    private String mListName;
    private RecyclerView rvUsers;
    private UserManagerPresenterImpl mPresenter;
    private UsersListRecyclerAdapter mAdapter;
    private OnUsersListFragmentListener mCallback;
    private ArrayList<User> users;
    private TextView txvEmpty;

    public UsersListFragment() {
        // Required empty public constructor
    }

    public static UsersListFragment newInstance(String forumId, String listName) {
        Bundle b = new Bundle();
        b.putString(AllConstants.Keys.SimpleBundle.ID_FORUM_KEY, forumId);
        b.putString(AllConstants.Keys.SimpleBundle.LIST_NAME_KEY, listName);
        UsersListFragment fragment = new UsersListFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mForumId = getArguments().getString(AllConstants.Keys.SimpleBundle.ID_FORUM_KEY);
            mListName = getArguments().getString(AllConstants.Keys.SimpleBundle.LIST_NAME_KEY);
        }
        mPresenter = new UserManagerPresenterImpl(this);
        users = new ArrayList<>();
        mAdapter = new UsersListRecyclerAdapter(getContext(), users, this);
        mPresenter.getAllUsersOfForum(mForumId, mListName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_users_list, container, false);
        rvUsers = (RecyclerView) rootView.findViewById(R.id.ul_rvUsers);
        txvEmpty = (TextView) rootView.findViewById(R.id.ul_txvEmpty);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUsers.setAdapter(mAdapter);
        if (!mAdapter.isEmpty()) {
            txvEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUsersListFragmentListener) {
            mCallback = (OnUsersListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUsersListFragmentListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onUserCreated() {

    }

    @Override
    public void onUserObtained(User tryUser) {
        if (mAdapter != null) {
            mAdapter.add(tryUser);
            txvEmpty.setVisibility(View.GONE);
        } else
            users.add(tryUser);
    }

    @Override
    public void onUserClicked(Bundle user) {
        mCallback.onUserClicked(user);
    }

    public interface OnUsersListFragmentListener {

        void onUserClicked(Bundle User);

    }
}
