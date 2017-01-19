package com.limox.jesus.monksresurrection;


import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.Adapters.PostAdapterRecycler;
import com.limox.jesus.monksresurrection.Fragments.AboutMe.AboutMe_Fragment;
import com.limox.jesus.monksresurrection.Fragments.Home.HomeDashPosts_Fragment;
import com.limox.jesus.monksresurrection.Fragments.PostView.PostView_Fragment;
import com.limox.jesus.monksresurrection.Interfaces.HomeOfFragments;
import com.limox.jesus.monksresurrection.Repositories.Users_Repository;
import com.limox.jesus.monksresurrection.Utils.AllConstants;
import com.limox.jesus.monksresurrection.Utils.NavDrawerUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Activity extends AppCompatActivity implements HomeOfFragments, PostAdapterRecycler.OnPostViewHolderListener, PostView_Fragment.OnPostViewFragmentListener, HomeDashPosts_Fragment.OnHomeDashPostFragmentListener, NavDrawerUtils.OnNavDrawerListener {

    Fragment mCurrentFragment;
    DrawerLayout mDrawerLayout;
    CircleImageView mCIVProfileImage;
    TextView mTxvUserName;
    NavigationView mNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_home);
        mCIVProfileImage = (CircleImageView) findViewById(R.id.cd_civUserProfile);
        mTxvUserName = (TextView) findViewById(R.id.cd_txvUserName);
        mNavView = (NavigationView) findViewById(R.id.nav_view_admins);
        NavDrawerUtils navUtils = new NavDrawerUtils(Home_Activity.this, mDrawerLayout);
        mNavView.getMenu().clear();
        mNavView.inflateMenu(navUtils.getMenu());
        mNavView.setNavigationItemSelectedListener(navUtils.getNavListener());

        mCIVProfileImage.setImageResource(Users_Repository.get().getCurrentUser().getProfilePicture());
        mCIVProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle user = new Bundle();
                user.putParcelable(AllConstants.USER_PARCELABLE_KEY, Users_Repository.get().getCurrentUser());
                startUserProfile(user);
            }
        });
        mTxvUserName.setText(Users_Repository.get().getCurrentUser().getName());

        if (savedInstanceState == null)
            startBugForum();
    }



    @Override
    public void startUserProfile(Bundle user) {
        Intent intent = new Intent(Home_Activity.this, UserProfile_Activity.class);
        intent.putExtras(user);
        startActivity(intent);
    }

    @Override
    public void startBugForum() {
        startFragment(new HomeDashPosts_Fragment(), true);
    }

    @Override
    public void startAdminZone() {
        startActivity(new Intent(this,Admins_Activity.class));
        finish();
    }

    @Override
    public void startSettings() {
        startActivity(new Intent(Home_Activity.this, Settings_Activity.class));
    }

    @Override
    public void startHelp() {
        startFragment(AboutMe_Fragment.newInstance(), true);
    }

    @Override
    public void startPostView(Bundle post) {
        startFragment(PostView_Fragment.newInstance(post), true);
    }

    @Override
    public void onOpenNavigatorDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void startAddPost() {
        startActivity(new Intent(Home_Activity.this,CreatePost_Activity.class));
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount()<= 1)
                finish();
            else
                super.onBackPressed();
        }
    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack) {
        mCurrentFragment = fragment;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(R.id.ah_container, mCurrentFragment);
        ft.commit();
    }
}
