package com.limox.jesus.teambeta.Fragments.PostView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limox.jesus.teambeta.Adapters.RecyclerView.CommentaryRecyclerViewAdapter;
import com.limox.jesus.teambeta.Interfaces.CommentaryManagerPresenter;
import com.limox.jesus.teambeta.Model.Commentary;
import com.limox.jesus.teambeta.Presenter.CommentaryManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.UIUtils;

import java.util.ArrayList;
import java.util.Date;


public class CommentsList_Fragment extends Fragment implements CommentaryManagerPresenter.View, CommentaryRecyclerViewAdapter.OnCommentaryHolderListener {

    private Toolbar mToolbar;
    private RecyclerView mRvComments;
    private EditText mEdtCommentary;
    private ImageButton mIvBtnSend;
    private CommentaryManagerPresenterImpl mPresenter;
    private CommentaryRecyclerViewAdapter mAdapter;
    private String mPostKey;

    public static Fragment newInstance(String postKey) {
        CommentsList_Fragment fragment = new CommentsList_Fragment();
        Bundle b = new Bundle();
        b.putString(AllConstants.Keys.SimpleBundle.ID_POST_KEY, postKey);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPostKey = getArguments().getString(AllConstants.Keys.SimpleBundle.ID_POST_KEY);
        mPresenter = new CommentaryManagerPresenterImpl(this);
        mAdapter = new CommentaryRecyclerViewAdapter(new ArrayList<Commentary>(), this, getContext());
        mPresenter.getComments(Users_Repository.get().getCurrentForum().getKey(), mPostKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_comments_list, container, false);
        mToolbar = (Toolbar) rootview.findViewById(R.id.toolbar);
        mRvComments = (RecyclerView) rootview.findViewById(R.id.rvComments);
        mEdtCommentary = (EditText) rootview.findViewById(R.id.edtComments);
        mIvBtnSend = (ImageButton) rootview.findViewById(R.id.btnSend);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setTitle(getString(R.string.comments));
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mRvComments.setAdapter(mAdapter);
        mRvComments.setLayoutManager(new LinearLayoutManager(getContext()));

        mIvBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commentary commentary = new Commentary();
                commentary.setCreationDate(new Date().getTime());
                commentary.setKeyPost(mPostKey);
                commentary.setUserName(Users_Repository.get().getCurrentUser().getName());
                commentary.setUserImgProf(Users_Repository.get().getCurrentUser().getProfilePicture());
                commentary.setKeyUser(Users_Repository.get().getCurrentUser().getId());
                commentary.setContent(mEdtCommentary.getText().toString());
                mPresenter.sendComment(Users_Repository.get().getCurrentForum().getKey(), mPostKey, commentary);
                mAdapter.add(commentary);
                mEdtCommentary.getText().clear();
                UIUtils.hideKeyboard(getActivity(), v);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCommentreated(String forumKey) {

    }

    @Override
    public void onImageUploaded(Uri downloadUrl) {

    }

    @Override
    public void onImageFailed() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onCommentsObtained(ArrayList<Commentary> comments) {
        mAdapter.addAll(comments);
    }

    @Override
    public void onUserPressed(Bundle userId) {

    }
}
