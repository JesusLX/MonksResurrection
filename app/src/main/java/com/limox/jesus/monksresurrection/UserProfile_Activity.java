package com.limox.jesus.monksresurrection;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Adapters.PostAdapterRecycler;
import com.limox.jesus.monksresurrection.Fragments.PostView.PostView_Fragment;
import com.limox.jesus.monksresurrection.Fragments.UserProfile.UserProfile_Fragment;
import com.limox.jesus.monksresurrection.Interfaces.HomeOfFragments;
import com.limox.jesus.monksresurrection.Model.Post;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

public class UserProfile_Activity extends AppCompatActivity implements HomeOfFragments, UserProfile_Fragment.OnUserProfileFragmentListener, PostAdapterRecycler.OnPostViewHolderListener, PostView_Fragment.OnPostViewFragmentListener {
    UserProfile_Fragment upf;
    Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        if (savedInstanceState == null)
            startUserProfile(getIntent().getExtras());
        else {
            startFragment(getSupportFragmentManager().getFragment(savedInstanceState, AllConstants.FRAGMENT_SAVESTATE_KEY), false);
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
        startFragment(UserProfile_Fragment.newInstance(user), false);
    }

    @Override
    public void startPostView(Bundle post) {
        startFragment(PostView_Fragment.newInstance(post), true);
    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack) {
        mCurrentFragment = fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(R.id.activity_user_profile, mCurrentFragment).commit();
    }
}
