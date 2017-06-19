package com.limox.jesus.teambeta.Fragments.Forums;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.limox.jesus.teambeta.Adapters.TabsAdapter.ForumUsersTabsAdapter;
import com.limox.jesus.teambeta.Interfaces.ForumManagerPresenter;
import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Presenter.ForumManagerPresenterImpl;
import com.limox.jesus.teambeta.Presenter.UserManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AeSimpleSHA1;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.UIUtils;


public class ForumViewFragment extends Fragment implements ForumManagerPresenter.View, UserManagerPresenter.View {

    private Forum mForum;
    private Toolbar mToolbar;
    private ImageView mIvLogo;
    private TextView mTxvName, mTxvDescription, mTxvWeb;
    private Button mBtnAccessLikeUser, mBtnAccessLikeAdmin, mBtnAccess;
    private boolean shortedDesc = false;
    private OnForumViewFragmentListener mCallback;
    private UserManagerPresenterImpl mPresenter;
    private ForumManagerPresenterImpl mForumPresenter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private RelativeLayout mRlHeader;
    public ProgressDialog mProgress;

    private ForumUsersTabsAdapter mAdapter;

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
        mPresenter = new UserManagerPresenterImpl(this);
        mForumPresenter = new ForumManagerPresenterImpl(this);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mForum = getArguments().getParcelable(AllConstants.Keys.Parcelables.FORUM);
        }
        mAdapter = new ForumUsersTabsAdapter(getContext(), getChildFragmentManager(), mForum.getKey());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (Users_Repository.get().getCurrentUser().getForumsOwn().contains(mForum.getKey()))
            inflater.inflate(R.menu.menu_forum_view, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapteMenu(mToolbar.getMenu());
        adaptButtons();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (Users_Repository.get().getCurrentUser().getForumsOwn().contains(mForum.getKey())) {
            if (Users_Repository.get().getCurrentUser().getId().equals(mForum.getOwnerId())) {
                menu.getItem(1).setVisible(false);
                menu.getItem(2).setVisible(false);
            } else {
                menu.getItem(0).setVisible(false);
            }
            getActivity().getMenuInflater().inflate(R.menu.menu_forum_view, menu);
        }
        super.onPrepareOptionsMenu(menu);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forum_view, container, false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mIvLogo = (ImageView) rootView.findViewById(R.id.fv_ivLogo);
        mTxvName = (TextView) rootView.findViewById(R.id.fv_txvName);
        mTxvDescription = (TextView) rootView.findViewById(R.id.fv_txvDescription);
        mTxvWeb = (TextView) rootView.findViewById(R.id.fv_txvWeb);
        mBtnAccessLikeUser = (Button) rootView.findViewById(R.id.fv_btnAccLUser);
        mBtnAccessLikeAdmin = (Button) rootView.findViewById(R.id.fv_btnAccLAdmin);
        mBtnAccess = (Button) rootView.findViewById(R.id.fv_btnAcc);
        mTabLayout = (TabLayout) rootView.findViewById(R.id.fv_tlTabs);
        mViewPager = (ViewPager) rootView.findViewById(R.id.fv_vpLists);
        mRlHeader = (RelativeLayout) rootView.findViewById(R.id.rlBgHeader);
        mForumPresenter.getForumFirebaseData(mForum);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mToolbar.inflateMenu(R.menu.menu_forum_view);
        mToolbar.setTitle(R.string.forum);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        if (mForum != null && mForum.getColor() != null)
                            mCallback.startEditForum(mForum.optBundle());
                        break;
                    case R.id.action_see_owner:
                        mProgress = new ProgressDialog(getContext());
                        mPresenter.getUser(mForum.getOwnerId());
                        mProgress.setMessage(getString(R.string.loading));
                        mProgress.show();
                        break;
                    case R.id.action_leave:

                        break;

                }
                return true;
            }
        });

        UIUtils.loadImage(getContext(), mForum.getImgUrl(), mIvLogo);
        mTxvName.setText(mForum.getName());

        mBtnAccessLikeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccessDialog(false);
            }
        });
        mBtnAccessLikeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccessDialog(true);
            }
        });
        mBtnAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users_Repository.get().setCurrentForum(mForum);
                mCallback.startHomeFragment();
            }
        });
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTxvWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mTxvWeb.getText().toString();
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
        adaptButtons();
    }

    public void adaptButtons() {
        if (Users_Repository.get().getCurrentUser().getForumsAdmin().contains(mForum.getKey()) || Users_Repository.get().getCurrentUser().getForumsWIParticipate().contains(mForum.getKey()) || Users_Repository.get().getCurrentUser().getForumsOwn().contains(mForum.getKey())) {
            mBtnAccess.setVisibility(View.VISIBLE);
            mBtnAccessLikeUser.setVisibility(View.INVISIBLE);
            mBtnAccessLikeAdmin.setVisibility(View.INVISIBLE);
        } else {
            mBtnAccess.setVisibility(View.GONE);
            mBtnAccessLikeUser.setVisibility(View.VISIBLE);
            mBtnAccessLikeAdmin.setVisibility(View.VISIBLE);
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
                String key = AeSimpleSHA1.SHA1(edtKey.getText().toString().trim());
                if (admin ? mForum.getAdminsKey().equals(key) : mForum.getUsersKey().equals(key)) {
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
                    UIUtils.snackBar(getView(), getString(R.string.wring_key));
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();

    }

    /**
     * Adapt the option menu for the current user
     *
     * @param menu menu inflated
     */
    private void adapteMenu(Menu menu) {
        menu.findItem(R.id.action_edit).setVisible(Users_Repository.get().isMyForum(mForum.getKey()));
        menu.findItem(R.id.action_leave).setVisible(/*Users_Repository.get().iParticipate(mForum.getKey())*/false);
        menu.findItem(R.id.action_see_owner).setVisible(!Users_Repository.get().isMyForum(mForum.getKey()));
    }

    @Override
    public void onForumCreated(String forumKey) {

    }

    @Override
    public void onImageUploaded(Uri downloadUrl) {

    }

    @Override
    public void onImageFailed() {

    }

    @Override
    public void onError() {
        mProgress.dismiss();

    }

    @Override
    public void onFirebaseForumObtained(Forum optForum) {
        mTxvDescription.setText(optForum.getDescription());
        mForum.setColor(optForum.getColor());
        if (mForum.getColor() != null) {
            mRlHeader.setBackgroundColor(Color.parseColor("#" + mForum.getColor()));
        }

        mTxvWeb.setText(optForum.getWeb());
        mBtnAccess.setEnabled(true);
        mBtnAccessLikeAdmin.setEnabled(true);
        mBtnAccessLikeUser.setEnabled(true);
    }


    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onUserCreated() {

    }

    @Override
    public void onUserObtained(User tryUser) {
        mCallback.startUserProfile(tryUser.optBundle(), true);
        mProgress.dismiss();
    }

    @Override
    public void onError(Exception exception) {

    }


    public interface OnForumViewFragmentListener {

        void startHomeFragment();

        void startForumsListFragment();

        void startUserProfile(Bundle user, boolean b);

        void startEditForum(Bundle forum);
    }
}
