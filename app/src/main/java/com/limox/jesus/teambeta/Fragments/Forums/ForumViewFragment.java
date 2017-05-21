package com.limox.jesus.teambeta.Fragments.Forums;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Presenter.UserManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.Preferences;
import com.limox.jesus.teambeta.Utils.UIUtils;


public class ForumViewFragment extends Fragment {

    private Forum mForum;
    private Toolbar mToolbar;
    private ImageView mIvLogo;
    private TextView mTxvName, mTxvDescription;
    private Button mBtnAccesLikeUser, mBtnAccesLikeAdmin, mBtnAcces, mBtnEdit;
    private boolean shortedDesc = false;
    private OnForumViewFragmentListener mCallback;
    private UserManagerPresenterImpl mPresenter;

    public ForumViewFragment() {
        // Required empty public constructor
    }

    public static ForumViewFragment newInstance(Bundle forum) {
        ForumViewFragment fragment = new ForumViewFragment();
        fragment.setArguments(forum);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new UserManagerPresenterImpl();
        if (getArguments() != null) {
            mForum = getArguments().getParcelable(AllConstants.Keys.Parcelables.FORUM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forum_view, container, false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mIvLogo = (ImageView) rootView.findViewById(R.id.fv_ivLogo);
        mTxvName = (TextView) rootView.findViewById(R.id.fv_txvName);
        mTxvDescription = (TextView) rootView.findViewById(R.id.fv_txvDescription);
        mBtnAccesLikeUser = (Button) rootView.findViewById(R.id.fv_btnAccLUser);
        mBtnAccesLikeAdmin = (Button) rootView.findViewById(R.id.fv_btnAccLAdmin);
        mBtnAcces = (Button) rootView.findViewById(R.id.fv_btnAcc);
        mBtnEdit = (Button) rootView.findViewById(R.id.fv_btnEdit);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setTitle(mForum.getName());
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.startForumsListFragment();
            }
        });
        UIUtils.loadImage(getContext(), mForum.getImgUrl(), mIvLogo);
        mTxvName.setText(mForum.getName());
        mTxvDescription.setText(mForum.getShortedDescription());
        mTxvDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortedDesc = !shortedDesc;
                mTxvDescription.setText(shortedDesc ? mForum.getShortedDescription() : mForum.getDescription());
            }
        });
        mBtnAccesLikeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccessDialog(false);
            }
        });
        mBtnAccesLikeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccessDialog(true);
            }
        });
        mBtnAcces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users_Repository.get().setCurrentForum(mForum);
                mCallback.startHomeFragment();
            }
        });
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.snackBar(getView(), "In working progress");
            }
        });
        adaptButtons();
    }

    public void adaptButtons() {
        if (Users_Repository.get().getCurrentUser().getForumsAdmin().contains(mForum.getKey()) || Users_Repository.get().getCurrentUser().getForumsWIParticipate().contains(mForum.getKey())) {
            mBtnAcces.setVisibility(View.VISIBLE);
            mBtnEdit.setVisibility(View.GONE);
            mBtnAccesLikeUser.setVisibility(View.GONE);
            mBtnAccesLikeAdmin.setVisibility(View.GONE);
        } else if (Users_Repository.get().getCurrentUser().getForumsOwn().contains(mForum.getKey())) {
            mBtnAcces.setVisibility(View.GONE);
            mBtnEdit.setVisibility(View.VISIBLE);
            mBtnAccesLikeUser.setVisibility(View.GONE);
            mBtnAccesLikeAdmin.setVisibility(View.GONE);
        } else {
            mBtnAcces.setVisibility(View.GONE);
            mBtnEdit.setVisibility(View.GONE);
            mBtnAccesLikeUser.setVisibility(View.VISIBLE);
            mBtnAccesLikeAdmin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnForumViewFragmentListener) {
            mCallback = (OnForumViewFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnForumViewFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    private void showAccessDialog(final boolean admin) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(mForum.getName());
        builder.setMessage(String.format(getString(R.string.access_like_msg), getString(admin ? R.string.admin : R.string.user)));
        final EditText edtKey = new EditText(getContext());
        builder.setView(edtKey);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (admin ? mForum.getAdminsKey().equals(edtKey.getText().toString().trim()) : mForum.getUsersKey().equals(edtKey.getText().toString().trim())) {
                    if (admin) {
                        Users_Repository.get().getCurrentUser().getForumsAdmin().add(mForum.getKey());
                    } else {
                        Users_Repository.get().getCurrentUser().getForumsWIParticipate().add(mForum.getKey());
                    }
                    mPresenter.aggregateForum(mForum.getKey(), admin, new UserManagerPresenter.ManagerView() {
                        @Override
                        public Context getContext() {
                            return getContext();
                        }

                        @Override
                        public void onForumAggregated(boolean admin) {
                            if (admin) {
                                Users_Repository.get().getCurrentUser().getForumsAdmin().add(mForum.getKey());
                            } else {
                                Users_Repository.get().getCurrentUser().getForumsWIParticipate().add(mForum.getKey());
                            }
                            Users_Repository.get().setCurrentForum(mForum);
                            mCallback.startHomeFragment();
                        }

                        @Override
                        public void onError() {
                            UIUtils.snackBar(getView(), getString(R.string.internal_error));
                            if (admin) {
                                Users_Repository.get().getCurrentUser().getForumsAdmin().remove(mForum.getKey());
                            } else {
                                Users_Repository.get().getCurrentUser().getForumsWIParticipate().remove(mForum.getKey());
                            }
                        }
                    });
                } else {
                    UIUtils.snackBar(getView(), "Wrong key, sorry");
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();

    }


    public interface OnForumViewFragmentListener {

        void startHomeFragment();

        void startForumsListFragment();
    }
}
