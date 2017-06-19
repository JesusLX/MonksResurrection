package com.limox.jesus.teambeta.Fragments.Chats;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limox.jesus.teambeta.Adapters.RecyclerView.ChatsRecyclerAdapter;
import com.limox.jesus.teambeta.Interfaces.ChatsManagerPresenter;
import com.limox.jesus.teambeta.Model.Chat;
import com.limox.jesus.teambeta.Presenter.ChatsManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;


public class ChatsList_Fragment extends Fragment implements ChatsManagerPresenter.View.ChatsManager, ChatsRecyclerAdapter.OnChatsViewHolderListener {

    private ArrayList<Chat> mChats;
    private RecyclerView mRvChats;
    private Toolbar mToolbar;
    private TextView mTxvNoChats;
    private ChatsRecyclerAdapter mAdapter;
    private ChatsManagerPresenterImpl mPresenter;

    private OnChatListFragmentListener mListener;

    public ChatsList_Fragment() {
        // Required empty public constructor
    }

    public static ChatsList_Fragment newInstance(Bundle chats) {
        ChatsList_Fragment fragment = new ChatsList_Fragment();
        fragment.setArguments(chats);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ChatsManagerPresenterImpl(this);
        if (getArguments() != null) {
            mChats = getArguments().getParcelableArrayList(AllConstants.Keys.SimpleBundle.CHATS_KEYS);
            mAdapter = new ChatsRecyclerAdapter(new ArrayList<Chat>(), this);
            mPresenter.optChat(Users_Repository.get().getCurrentForum().getKey(), mChats);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chats_list, container, false);
        mRvChats = (RecyclerView) rootView.findViewById(R.id.rvChats);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mTxvNoChats = (TextView) rootView.findViewById(R.id.txvNoChats);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setTitle(R.string.chats);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mToolbar.setBackgroundColor(UIUtils.parseColor(Users_Repository.get().getCurrentForum().getColor()));
        mRvChats.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvChats.setAdapter(mAdapter);
        if (mChats == null || mChats.size() == 0) {
            mTxvNoChats.setVisibility(View.VISIBLE);
        } else {
            mTxvNoChats.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChatListFragmentListener) {
            mListener = (OnChatListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChatListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onChatsReceived(HashMap<String, ArrayList<Chat>> singleChatData) {

    }

    @Override
    public void onChatReceived(Chat chat) {
        mTxvNoChats.setVisibility(View.GONE);
        mAdapter.add(chat);
    }

    @Override
    public void onChatPressed(Bundle chat) {
        mListener.onChatPressed(chat);
    }

    public interface OnChatListFragmentListener {

        void onChatPressed(Bundle chat);
    }
}
