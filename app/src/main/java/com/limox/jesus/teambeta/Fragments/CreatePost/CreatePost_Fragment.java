package com.limox.jesus.teambeta.Fragments.CreatePost;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.limox.jesus.teambeta.Interfaces.PostManagerPresenter;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Notifications.Notifications;
import com.limox.jesus.teambeta.Presenter.PostManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.UIUtils;


public class CreatePost_Fragment extends Fragment implements PostManagerPresenter.View {

    private EditText mEdtTitle;
    private EditText mEdtDescription;
    private EditText mEdtTags;
    private String mTitle;
    private String mDescriptions;
    private Toolbar mToolbar;
    private String mTags;
    private RelativeLayout mRlContainer;
    private OnCreatePostFragmentListener mCallback;
    private Toolbar.OnMenuItemClickListener mMenuItemListener;
    private PostManagerPresenterImpl mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PostManagerPresenterImpl(this);
        /*setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(com.limox.jesus.teambeta.R.string.create_post));
        getSupportActionBar().setHomeAsUpIndicator(com.limox.jesus.teambeta.R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_post, container, false);
        mEdtTitle = (EditText) rootView.findViewById(com.limox.jesus.teambeta.R.id.crp_EdtPostTitle);
        mEdtDescription = (EditText) rootView.findViewById(com.limox.jesus.teambeta.R.id.crp_EdtPostDescription);
        mEdtTags = (EditText) rootView.findViewById(com.limox.jesus.teambeta.R.id.crp_EdtPostTags);
        mToolbar = (Toolbar) rootView.findViewById(com.limox.jesus.teambeta.R.id.acp_toolbar);
        mRlContainer = (RelativeLayout) rootView.findViewById(com.limox.jesus.teambeta.R.id.activity_create_post);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.inflateMenu(R.menu.menu_create_post);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setTitle(R.string.create_post);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                UIUtils.hideKeyboard(getActivity(), getView());
                if (UIUtils.isNetworkAvailable(getContext())) {
                    switch (item.getItemId()) {
                        case R.id.action_send:
                            sendPost();
                            break;
                    }
                } else {
                    UIUtils.toast(getContext(), getString(R.string.connection_error));
                }
                return true;
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mToolbar.setBackgroundColor(UIUtils.parseColor(Users_Repository.get().getCurrentForum().getColor()));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCreatePostFragmentListener) {
            mCallback = (OnCreatePostFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddTagsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface OnCreatePostFragmentListener {

    }

    private boolean validate() {
        boolean allRigth = false;

        mTitle = mEdtTitle.getText().toString();
        mDescriptions = mEdtDescription.getText().toString();
        mTags = mEdtTags.getText().toString();

        if (!TextUtils.isEmpty(mTitle) && !TextUtils.isEmpty(mDescriptions) && !TextUtils.isEmpty(mTags))
            allRigth = true;
        return allRigth;
    }


    private void sendPost() {
        if (validate()) {
            final Post tmp = new Post(Users_Repository.get().getCurrentUser().getId(), Users_Repository.get().getCurrentForum().getKey(), mTitle, mDescriptions, mTags);
            tmp.setIdPost(mPresenter.uploadPost(tmp, new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    if (isAdded()) {
                        Notifications.SentPublicationPostSent(getContext(), tmp.optBundle());
                        getActivity().finish();
                    }
                }
            }));

        } else
            UIUtils.snackBar(getView(), R.string.message_error_must_fill);

    }

    private void initMenuItemListener() {
        mMenuItemListener = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_send:
                        sendPost();
                        break;
                }
                return true;
            }
        };
    }
}
