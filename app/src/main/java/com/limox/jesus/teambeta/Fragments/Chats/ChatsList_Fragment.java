package com.limox.jesus.teambeta.Fragments.Chats;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.HashMap;


public class ChatsList_Fragment extends Fragment implements ChatsManagerPresenter.View.ChatsManager, ChatsRecyclerAdapter.OnChatsViewHolderListener {

    private ArrayList<String> mChatKeys;
    private RecyclerView mRvChats;
    private ChatsRecyclerAdapter mAdapter;
    private ChatsManagerPresenterImpl mPresenter;

    private OnFragmentInteractionListener mListener;

    public ChatsList_Fragment() {
        // Required empty public constructor
    }

    public static ChatsList_Fragment newInstance(ArrayList<String> chatKeys) {
        ChatsList_Fragment fragment = new ChatsList_Fragment();
        Bundle args = new Bundle();
        args.putStringArrayList(AllConstants.Keys.SimpleBundle.CHATS_KEYS, chatKeys);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ChatsManagerPresenterImpl(this);
        mAdapter = new ChatsRecyclerAdapter(null, this);
        if (getArguments() != null) {
            mChatKeys = getArguments().getStringArrayList(AllConstants.Keys.SimpleBundle.CHATS_KEYS);
            mPresenter.optChats(Users_Repository.get().getCurrentForum().getKey(), mChatKeys);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chats_list, container, false);
        mRvChats = (RecyclerView) rootView.findViewById(R.id.rvChats);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
        mAdapter.add(chat);
    }

    @Override
    public void onChatPressed(Chat chat) {

    }

    public interface OnFragmentInteractionListener {

    }
}
