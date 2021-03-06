package com.limox.jesus.teambeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.limox.jesus.teambeta.Adapters.ArrayAdapter.PostArrayAdapter;
import com.limox.jesus.teambeta.Fragments.Admins.AdminsDashPosts_Fragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.Model.Chat;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.ExternalUtils;
import com.limox.jesus.teambeta.Utils.NavDrawerUtils;
import com.limox.jesus.teambeta.Utils.UIUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admins_Activity extends AppCompatActivity implements HomeOfFragments, NavDrawerUtils.OnNavDrawerListener, AdminsDashPosts_Fragment.OnAdminDashPostFragmentListener, PostArrayAdapter.OnPostViewHolderListener {
    Fragment mCurrentFragment;
    DrawerLayout mDrawerLayout;
    CircleImageView mCIVProfileImage;
    NavigationView mNavView;
    TextView mTxvUserName;
    ImageView ivHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TeamBetaApplication.setCurrentActivity("Admins_Activity");
        setContentView(com.limox.jesus.teambeta.R.layout.activity_admins);
        mDrawerLayout = (DrawerLayout) findViewById(com.limox.jesus.teambeta.R.id.activity_admins);
        mCIVProfileImage = (CircleImageView) findViewById(com.limox.jesus.teambeta.R.id.cd_civUserProfile);
        mTxvUserName = (TextView) findViewById(com.limox.jesus.teambeta.R.id.cd_txvUserName);
        mNavView = (NavigationView) findViewById(com.limox.jesus.teambeta.R.id.nav_view_admins);
        ivHeader = (ImageView) findViewById(R.id.ivHeader);

        UIUtils.loadImage(Admins_Activity.this, Users_Repository.get().getCurrentForum().getImgUrl(), ivHeader);

        NavDrawerUtils navUtils = new NavDrawerUtils(Admins_Activity.this, mDrawerLayout);
        mNavView.getMenu().clear();
        mNavView.inflateMenu(navUtils.getMenu());
        mNavView.setNavigationItemSelectedListener(navUtils.getNavListener());

        Glide.with(Admins_Activity.this).load(Users_Repository.get().getCurrentUser().getProfilePicture()).into(mCIVProfileImage);
        mCIVProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle user = new Bundle();
                user.putString(AllConstants.Keys.SimpleBundle.ID_USER_KEY, Users_Repository.get().getCurrentUser().getId());
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
    public void startChats(ArrayList<Chat> user) {
        Intent i = new Intent(Admins_Activity.this, Chats_Activity.class);
        i.putExtra(AllConstants.Keys.SimpleBundle.CHATS_KEYS, user);
        startActivity(i);
    }
    @Override
    public void startSelectProject() {
        closeDrawerLayout();
        startActivity(new Intent(Admins_Activity.this, SelectProject_Activity.class));
        finish();
    }

    @Override
    public void startBugForum() {
        closeDrawerLayout();
        startActivity(new Intent(Admins_Activity.this, Home_Activity.class));
        finish();
    }

    @Override
    public void startAdminZone() {

        startFragment(new AdminsDashPosts_Fragment(), false, AllConstants.FragmentTag.AdminZoneTag);

    }

    @Override
    public void startSettings() {
        closeDrawerLayout();
        startActivity(new Intent(this, Settings_Activity.class));

    }

    @Override
    public void startHelp() {
        ExternalUtils.openBrowser(Admins_Activity.this, AllConstants.HELP_WEB);
    }

    @Override
    public void startPostView(Bundle post) {
        Intent intent = new Intent(Admins_Activity.this,PostView_Activity.class);
        intent.putExtras(post);
        startActivity(intent);
    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack, String tag) {
        closeDrawerLayout();
        mCurrentFragment = fragment;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(com.limox.jesus.teambeta.R.id.aa_container, mCurrentFragment, tag);
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

    private boolean closeDrawerLayout() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else
            return false;

    }
}
