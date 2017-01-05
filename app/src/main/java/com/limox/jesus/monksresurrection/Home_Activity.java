package com.limox.jesus.monksresurrection;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.Adapters.NavListViewAdapter;
import com.limox.jesus.monksresurrection.Adapters.PostAdapterRecycler;
import com.limox.jesus.monksresurrection.Fragments.AboutMe.AboutMe_Fragment;
import com.limox.jesus.monksresurrection.Fragments.DashPost.HomeDashPosts_Fragment;
import com.limox.jesus.monksresurrection.Fragments.PostView.PostView_Fragment;
import com.limox.jesus.monksresurrection.Fragments.UserProfile.UserProfile_Fragment;
import com.limox.jesus.monksresurrection.Interfaces.HomeOfFragments;
import com.limox.jesus.monksresurrection.Repositories.NavItem_Repository;
import com.limox.jesus.monksresurrection.Repositories.Users_Repository;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Activity extends AppCompatActivity implements HomeOfFragments, PostAdapterRecycler.OnPostViewHolderListener, UserProfile_Fragment.OnUserProfileFragmentListener, PostView_Fragment.OnPostViewFragmentListener, HomeDashPosts_Fragment.OnHomeDashPostFragmentListener, DrawerListListener.onDrawerListListenerCallbacks {

    Fragment mCurrentFragment;
    DrawerLayout mDragerLAyout;
    ListView mDrawerList;
    CircleImageView mCIVProfileImage;
    TextView mTxvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDragerLAyout = (DrawerLayout) findViewById(R.id.activity_home);
        mDrawerList = (ListView) findViewById(R.id.nav_lvList);
        mCIVProfileImage = (CircleImageView) findViewById(R.id.cd_civUserProfile);
        mTxvUserName = (TextView) findViewById(R.id.cd_txvUserName);

        mDrawerList.setAdapter(new NavListViewAdapter(this,new NavItem_Repository(this).getNavDrawerItems()));
        mDrawerList.setOnItemClickListener(new DrawerListListener(this));

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
        mDragerLAyout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (mDragerLAyout.isDrawerOpen(GravityCompat.START)) {
            mDragerLAyout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
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
