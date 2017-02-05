package com.limox.jesus.teambeta;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.teambeta.Adapters.PostAdapterRecycler;
import com.limox.jesus.teambeta.Fragments.PostView.PostView_Fragment;
import com.limox.jesus.teambeta.Fragments.UserProfile.UserProfile_Fragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.Utils.AllConstants;

public class UserProfile_Activity extends AppCompatActivity implements HomeOfFragments, UserProfile_Fragment.OnUserProfileFragmentListener, PostAdapterRecycler.OnPostViewHolderListener, PostView_Fragment.OnPostViewFragmentListener {
    UserProfile_Fragment upf;
    Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.limox.jesus.teambeta.R.layout.activity_user_profile);
        if (savedInstanceState == null)
            startUserProfile(getIntent().getExtras());
        else {
            startFragment(getSupportFragmentManager().getFragment(savedInstanceState, AllConstants.FRAGMENT_SAVESTATE_KEY), false,AllConstants.UserProfileTag);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, AllConstants.FRAGMENT_SAVESTATE_KEY, mCurrentFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }


    @Override
    public void startUserProfile(Bundle user) {
        startFragment(UserProfile_Fragment.newInstance(user), false,AllConstants.UserProfileTag);
    }

    @Override
    public void startPostView(Bundle post) {
        startFragment(PostView_Fragment.newInstance(post), true,AllConstants.PostViewTag);
    }

    @Override
    public void backPressed() {
        onBackPressed();
    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack,String tag) {
        mCurrentFragment = fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        try {
            ft.replace(com.limox.jesus.teambeta.R.id.activity_user_profile, mCurrentFragment,tag).commit();
        }catch (Exception e)
        {
            ft.replace(com.limox.jesus.teambeta.R.id.activity_user_profile, mCurrentFragment,tag).commitNow();

        }
    }
}
