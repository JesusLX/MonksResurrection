package com.limox.jesus.monksresurrection;




import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Adapters.PostAdapterRecycler;
import com.limox.jesus.monksresurrection.Fragments.AboutMe.AboutMe_Fragment;
import com.limox.jesus.monksresurrection.Fragments.DashPost.HomeDashPosts_Fragment;
import com.limox.jesus.monksresurrection.Fragments.PostView.PostView_Fragment;
import com.limox.jesus.monksresurrection.Fragments.UserProfile.UserProfile_Fragment;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

public class Home_Activity extends AppCompatActivity implements PostAdapterRecycler.OnPostViewHolderListener,UserProfile_Fragment.OnUserProfileFragmentListener, PostView_Fragment.OnPostViewFragmentListener {

    Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null)
            startDashPostFragment();
       /* else
            mCurrentFragment = getSupportFragmentManager().getFragment(savedInstanceState,AllConstants.FRAGMENT_SAVESTATE_KEY);
    */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    //    getSupportFragmentManager().putFragment(outState, AllConstants.FRAGMENT_SAVESTATE_KEY, mCurrentFragment);
    }


    void startFragment(Fragment fragment,boolean addStack){
        mCurrentFragment = fragment;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(R.id.ah_container, mCurrentFragment);
        ft.commit();
    }

    void startDashPostFragment(){
        startFragment(new HomeDashPosts_Fragment(),true);
    }

    private void startIndexFragment(){
        startFragment(new Index_Fragment(),true);
    }
    private void openAboutMe(){
        startFragment(AboutMe_Fragment.newInstance(),true);
    }

    @Override
    public void startUserProfile(Bundle user) {
        Intent intent = new Intent(Home_Activity.this,UserProfile_Activity.class);
        intent.putExtras(user);
        startActivity(intent);
    }

    @Override
    public void startPostView(Bundle post) {
        startFragment(PostView_Fragment.newInstance(post),true);
    }

}
