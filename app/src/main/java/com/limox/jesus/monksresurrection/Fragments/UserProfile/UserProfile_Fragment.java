package com.limox.jesus.monksresurrection.Fragments.UserProfile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.limox.jesus.monksresurrection.Adapters.ProfilePostTabsAdapter;
import com.limox.jesus.monksresurrection.Model.Post;
import com.limox.jesus.monksresurrection.Model.User;
import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

public class UserProfile_Fragment extends Fragment implements AppBarLayout.OnOffsetChangedListener{

    private OnUserProfileFragmentListener mCallback;
    private ViewPager mVpContainer;
    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
	private boolean mIsAvatarShown = true;

	private ImageView mIvProfileImage;
	private int mMaxScrollSize;

    private User mUser;

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
            mUser = getArguments().getParcelable(AllConstants.USER_PARCELABLE_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container,false);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.up_tlTabs);
		ViewPager viewPager  = (ViewPager) rootView.findViewById(R.id.up_vpContainer);
		AppBarLayout appbarLayout = (AppBarLayout) rootView.findViewById(R.id.materialup_appbar);
		mIvProfileImage = (ImageView) rootView.findViewById(R.id.materialup_profile_image);

		appbarLayout.addOnOffsetChangedListener(this);
		mMaxScrollSize = appbarLayout.getTotalScrollRange();

		viewPager.setAdapter(new ProfilePostTabsAdapter(getContext(),getActivity().getSupportFragmentManager(),mUser.getIdUser()));
		tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIvProfileImage.setImageResource(mUser.getProfilePicture());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserProfileFragmentListener) {
            mCallback = (OnUserProfileFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUserProfileFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
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

    public interface OnUserProfileFragmentListener {
        void startPostView(Bundle post);
    }
}
