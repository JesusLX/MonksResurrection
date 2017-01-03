package com.limox.jesus.monksresurrection;




import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.limox.jesus.monksresurrection.Adapters.NavListViewAdapter;
import com.limox.jesus.monksresurrection.Adapters.PostAdapterRecycler;
import com.limox.jesus.monksresurrection.Fragments.AboutMe.AboutMe_Fragment;
import com.limox.jesus.monksresurrection.Fragments.DashPost.HomeDashPosts_Fragment;
import com.limox.jesus.monksresurrection.Fragments.PostView.PostView_Fragment;
import com.limox.jesus.monksresurrection.Fragments.UserProfile.UserProfile_Fragment;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

public class Home_Activity extends AppCompatActivity implements PostAdapterRecycler.OnPostViewHolderListener,UserProfile_Fragment.OnUserProfileFragmentListener, PostView_Fragment.OnPostViewFragmentListener, HomeDashPosts_Fragment.OnHomeDashPostFragmentListener, DrawerListListener.onDrawerListListenerCallbacks {

    Fragment mCurrentFragment;
    DrawerLayout mDragerLAyout;
    ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDragerLAyout = (DrawerLayout) findViewById(R.id.activity_home);
        mDrawerList = (ListView) findViewById(R.id.nav_lvList);
        mDrawerList.setAdapter(new NavListViewAdapter(this));
        mDrawerList.setOnItemClickListener(new DrawerListListener(this));

        if (savedInstanceState == null)
            startBugForum();
    }

    void startFragment(Fragment fragment,boolean addStack){
        mCurrentFragment = fragment;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(R.id.ah_container, mCurrentFragment);
        ft.commit();
    }

    @Override
    public void startUserProfile(Bundle user) {
        Intent intent = new Intent(Home_Activity.this,UserProfile_Activity.class);
        intent.putExtras(user);
        startActivity(intent);
    }

    @Override
    public void startBugForum() {
        startFragment(new HomeDashPosts_Fragment(),true);
    }

    @Override
    public void startAdminZone() {

    }

    @Override
    public void startSettings() {

    }

    @Override
    public void startHelp() {
        startFragment(AboutMe_Fragment.newInstance(),true);
    }

    @Override
    public void startPostView(Bundle post) {
        startFragment(PostView_Fragment.newInstance(post),true);
    }

    @Override
    public void onOpenNavigatorDrawer() {
        mDragerLAyout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (mDragerLAyout.isDrawerOpen(GravityCompat.START)) {
            mDragerLAyout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }
}
