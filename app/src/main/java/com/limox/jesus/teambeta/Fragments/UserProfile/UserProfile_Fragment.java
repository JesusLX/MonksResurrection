package com.limox.jesus.teambeta.Fragments.UserProfile;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.limox.jesus.teambeta.Adapters.TabsAdapter.ProfilePostTabsAdapter;
import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Presenter.UserManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.UIUtils;

import it.sephiroth.android.library.picasso.Picasso;

public class UserProfile_Fragment extends Fragment implements AppBarLayout.OnOffsetChangedListener,UserManagerPresenter.View{

    private OnUserProfileFragmentListener mCallback;
    private ViewPager mVpContainer;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AppBarLayout mAppbarLayout;
    private ProfilePostTabsAdapter mAdapter;
    private ImageView mIvwBack;
    private ImageView mIvBackground;
    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
	private boolean mIsAvatarShown = true;

	private ImageView mIvProfileImage;
	private int mMaxScrollSize;

    private User mUser;
    private int idUser;

    private UserManagerPresenter mPresenter;

    public UserProfile_Fragment() {
        // Required empty public constructor
    }


    public static UserProfile_Fragment newInstance(Bundle args) {
        UserProfile_Fragment fragment = new UserProfile_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idUser = getArguments().getInt(AllConstants.Keys.SimpleBundle.ID_USER_KEY);
        }
        setHasOptionsMenu(true);
        mAdapter = new ProfilePostTabsAdapter(getContext(),getChildFragmentManager(),idUser);
        mPresenter = new UserManagerPresenterImpl(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        mUser = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container,false);

        mAppbarLayout = (AppBarLayout) rootView.findViewById(R.id.materialup_appbar);
        mIvProfileImage = (ImageView) rootView.findViewById(R.id.materialup_profile_image);
        mTabLayout = (TabLayout) rootView.findViewById(R.id.up_tlTabs);
        mViewPager = (ViewPager) rootView.findViewById(R.id.up_vpContainer);
        mIvwBack = (ImageView) rootView.findViewById(R.id.btnBack);
        mIvBackground = (ImageView) rootView.findViewById(R.id.ivBackground);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mMaxScrollSize = mAppbarLayout.getTotalScrollRange();
        mAppbarLayout.addOnOffsetChangedListener(this);

        UIUtils.loadImage(getContext(), Users_Repository.get().getCurrentForum().getImgUrl(), mIvBackground);
        //  UIUtils.loadImage(getContext(), mUser.getProfilePicture(), mIvProfileImage);

        mIvwBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.backPressed();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnUserProfileFragmentListener) {
            mCallback = (OnUserProfileFragmentListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnUserProfileFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onStart() {
        super.onStart();
   //     mPresenter.getUser(idUser);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            mIvProfileImage.animate().scaleY(0).scaleX(0).setDuration(200).start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mIvProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onUserCreated() {

    }

    @Override
    public void onUserObtained(User tryUser) {
        Picasso.with(getContext()).load(tryUser.getProfilePicture()).into(mIvProfileImage);

    }

    public interface OnUserProfileFragmentListener {
        void startPostView(Bundle post);
        void backPressed();
    }
}
