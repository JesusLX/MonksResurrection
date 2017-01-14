package com.limox.jesus.monksresurrection;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.Adapters.PostAdapterRecycler;
import com.limox.jesus.monksresurrection.Fragments.AboutMe.AboutMe_Fragment;
import com.limox.jesus.monksresurrection.Fragments.Admins.AdminsDashPosts_Fragment;
import com.limox.jesus.monksresurrection.Fragments.PostView.PostView_Fragment;
import com.limox.jesus.monksresurrection.Fragments.UserProfile.UserProfile_Fragment;
import com.limox.jesus.monksresurrection.Interfaces.HomeOfFragments;
import com.limox.jesus.monksresurrection.Repositories.Users_Repository;
import com.limox.jesus.monksresurrection.Utils.AllConstants;
import com.limox.jesus.monksresurrection.Utils.NavDrawerUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admins_Activity extends AppCompatActivity implements HomeOfFragments, NavDrawerUtils.OnNavDrawerListener,PostView_Fragment.OnPostViewFragmentListener, AdminsDashPosts_Fragment.OnAdminDashPostFragmentListener, PostAdapterRecycler.OnPostViewHolderListener {
    Fragment mCurrentFragment;
    DrawerLayout mDrawerLayout;
    CircleImageView mCIVProfileImage;
    NavigationView mNavView;
    TextView mTxvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_admins);
        mCIVProfileImage = (CircleImageView) findViewById(R.id.cd_civUserProfile);
        mTxvUserName = (TextView) findViewById(R.id.cd_txvUserName);
        mNavView = (NavigationView) findViewById(R.id.nav_view_admins);

        NavDrawerUtils navUtils = new NavDrawerUtils(Admins_Activity.this, mDrawerLayout);
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
            startAdminZone();
    }

    @Override
    public void startUserProfile(Bundle user) {
        closeDrawerLayout();
        Intent intent = new Intent(this, UserProfile_Activity.class);
        intent.putExtras(user);
        startActivity(intent);
    }

    @Override
    public void startBugForum() {
        closeDrawerLayout();
        startActivity(new Intent(Admins_Activity.this, Home_Activity.class));
        finish();
    }

    @Override
    public void startAdminZone() {


            startFragment(new AdminsDashPosts_Fragment(), false);

    }

    @Override
    public void startSettings() {
        closeDrawerLayout();
        startActivity(new Intent(this, Settings_Activity.class));

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
    public void startFragment(Fragment fragment, boolean addStack) {
       closeDrawerLayout();
        mCurrentFragment = fragment;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(R.id.aa_container, mCurrentFragment);
        ft.commit();
    }

    @Override
    public void onOpenNavigatorDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (!closeDrawerLayout())
            super.onBackPressed();
    }
    private boolean closeDrawerLayout(){
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        else
            return false;

    }
}
