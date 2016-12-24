package com.limox.jesus.monksresurrection;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Adapters.PostAdapterRecycler;
import com.limox.jesus.monksresurrection.Fragments.UserProfile.UserProfile_Fragment;
import com.limox.jesus.monksresurrection.Model.Post;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

public class UserProfile_Activity extends AppCompatActivity implements UserProfile_Fragment.OnUserProfileFragmentListener, PostAdapterRecycler.OnPostViewHolderListener{
    UserProfile_Fragment upf;
    Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        if (savedInstanceState == null)
            startUserProfile(getIntent().getExtras());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCurrentFragment != null){
            getSupportFragmentManager().putFragment(outState,AllConstants.FRAGMENT_SAVESTATE_KEY,mCurrentFragment);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mCurrentFragment != null)
            getSupportFragmentManager().putFragment(savedInstanceState, AllConstants.FRAGMENT_SAVESTATE_KEY,mCurrentFragment);
    }

    /**
     * Replace the current view for the fragment introduced
     * This method put the @param fragment like the curren fragment
     * @param fragment fragment to start
     * @param addStack if add to the backStack
     */
    void startFragment(Fragment fragment,boolean addStack){
        mCurrentFragment = fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(R.id.activity_user_profile, mCurrentFragment).commit();
    }

    @Override
    public void startUserProfile(Bundle user) {
        if (mCurrentFragment == null) {
            upf = UserProfile_Fragment.newInstance(user);
            startFragment(upf, false);
        }
    }

    @Override
    public void startPostView(Bundle post) {

    }

    @Override
    public void startPostView(Post post) {

    }
}
