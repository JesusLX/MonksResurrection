package com.limox.jesus.monksresurrection;




import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Adapters.PostAdapterRecycler;
import com.limox.jesus.monksresurrection.Fragments.AboutMe.AboutMe_Fragment;
import com.limox.jesus.monksresurrection.Fragments.DashPost.HomeDashPosts_Fragment;
import com.limox.jesus.monksresurrection.Fragments.UserProfile.UserProfile_Fragment;
import com.limox.jesus.monksresurrection.Model.Post;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

public class Home_Activity extends AppCompatActivity implements PostAdapterRecycler.OnPostViewHolderListener,UserProfile_Fragment.OnUserProfileFragmentListener {
    HomeDashPosts_Fragment hdpf;
    Index_Fragment inf;
    AboutMe_Fragment fr;
    Fragment mCurrentFrament;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState != null){
            startFragment(getSupportFragmentManager().getFragment(savedInstanceState,AllConstants.FRAGMENT_SAVESTATE_KEY));
        }
        startDashPostFragment();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, AllConstants.FRAGMENT_SAVESTATE_KEY,mCurrentFrament);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getSupportFragmentManager().putFragment(savedInstanceState, AllConstants.FRAGMENT_SAVESTATE_KEY,mCurrentFrament);
    }

    void startFragment(Fragment fragment){
        mCurrentFrament = fragment;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.ah_container, mCurrentFrament);
        ft.commit();
    }

    void startDashPostFragment(){
        startFragment(new HomeDashPosts_Fragment());
    }

    private void startIndexFragment(){
        startFragment(new Index_Fragment());
    }
    private void openAboutMe(){
        startFragment(AboutMe_Fragment.newInstance());
    }

    @Override
    public void startUserProfile(Bundle user) {
        Intent intent = new Intent(Home_Activity.this,UserProfile_Activity.class);
        intent.putExtras(user);
        startActivity(intent);
    }

    @Override
    public void startPostView(Bundle post) {

    }

    @Override
    public void startPostView(Post post) {

    }

}
