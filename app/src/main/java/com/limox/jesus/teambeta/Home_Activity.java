package com.limox.jesus.teambeta;


import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.limox.jesus.teambeta.Adapters.PostAdapterRecycler;
import com.limox.jesus.teambeta.Fragments.AboutMe.AboutMe_Fragment;
import com.limox.jesus.teambeta.Fragments.Home.HomeDashPosts_Fragment;
import com.limox.jesus.teambeta.Fragments.PostView.PostView_Fragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.NavDrawerUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Activity extends AppCompatActivity implements HomeOfFragments, PostAdapterRecycler.OnPostViewHolderListener, HomeDashPosts_Fragment.OnHomeDashPostFragmentListener, NavDrawerUtils.OnNavDrawerListener {

    Fragment mCurrentFragment;
    DrawerLayout mDrawerLayout;
    CircleImageView mCIVProfileImage;
    TextView mTxvUserName;
    NavigationView mNavView;
    NavDrawerUtils navUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.limox.jesus.teambeta.R.layout.activity_home);

        init();
        initNavView();

        //TODO La imagen del usuario se descargará con Picasso de la base de datos
        //----------------------------------------------------------------------------------------------
            mCIVProfileImage.setImageResource(Users_Repository.get().getCurrentUser().getProfilePicture());
        //-----------------------------------------------------------------------------------------------
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
    private void init(){
        mDrawerLayout = (DrawerLayout) findViewById(com.limox.jesus.teambeta.R.id.activity_home);
        mCIVProfileImage = (CircleImageView) findViewById(com.limox.jesus.teambeta.R.id.cd_civUserProfile);
        mTxvUserName = (TextView) findViewById(com.limox.jesus.teambeta.R.id.cd_txvUserName);
        mNavView = (NavigationView) findViewById(com.limox.jesus.teambeta.R.id.nav_view_admins);
        navUtils = new NavDrawerUtils(Home_Activity.this, mDrawerLayout);
    }
    private void initNavView(){
        mNavView.getMenu().clear();
        mNavView.inflateMenu(navUtils.getMenu());
        mNavView.setNavigationItemSelectedListener(navUtils.getNavListener());
    }

    @Override
    public void startUserProfile(Bundle user) {
        Intent intent = new Intent(Home_Activity.this, UserProfile_Activity.class);
        intent.putExtras(user);
        startActivity(intent);
    }

    @Override
    public void startBugForum() {
        startFragment(new HomeDashPosts_Fragment(), true,AllConstants.BugForumTag);
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
        startFragment(AboutMe_Fragment.newInstance(), true, AllConstants.HelpTag);
    }

    @Override
    public void startPostView(Bundle post) {
        Intent intent = new Intent(Home_Activity.this,PostView_Activity.class);
        intent.putExtras(post);
        startActivity(intent);
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
    public void startFragment(Fragment fragment, boolean addStack, String tag) {
        mCurrentFragment = fragment;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(com.limox.jesus.teambeta.R.id.ah_container, mCurrentFragment,tag);
        ft.commit();
    }
}
