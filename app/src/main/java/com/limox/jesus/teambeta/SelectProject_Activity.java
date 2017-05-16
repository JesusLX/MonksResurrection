package com.limox.jesus.teambeta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.limox.jesus.teambeta.Fragments.Forums.CreateForumFragment;
import com.limox.jesus.teambeta.Fragments.Forums.ForumsListFragment;
import com.limox.jesus.teambeta.Fragments.Forums.SearchFragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Utils.AllConstants;

public class SelectProject_Activity extends AppCompatActivity implements HomeOfFragments, CreateForumFragment.OnFragmentInteractionListener, ForumsListFragment.OnForumsListFragmentListener {

    BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_project);
        mBottomNav = (BottomNavigationView) findViewById(R.id.botton_nav);
        if (savedInstanceState == null){
            startForumsListFragment(Forum.PARTAKER, String.valueOf(Forum.PARTAKER));
            mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_list:
                            startForumsListFragment(Forum.PARTAKER, String.valueOf(Forum.PARTAKER));
                            break;
                        case R.id.action_owned_forums:
                            startForumsListFragment(Forum.OWN, String.valueOf(Forum.OWN));
                            break;
                        case R.id.action_owned_admins:
                            startForumsListFragment(Forum.ADMIN, String.valueOf(Forum.ADMIN));
                            break;
                        case R.id.action_search:
                            startSearchFragment();
                            break;
                        case R.id.action_profile:
                            break;
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack, String tag) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(com.limox.jesus.teambeta.R.id.fragment_container, fragment, tag);
        ft.commit();
    }

    @Override
    public void startCreateForumFragment() {
        startFragment(new CreateForumFragment(), false, AllConstants.FragmentTag.CreateForum);
        mBottomNav.setVisibility(View.GONE);
    }

    @Override
    public void startHomeFragment() {
        startActivity(new Intent(SelectProject_Activity.this, Home_Activity.class));
    }

    @Override
    public void startForumsListFragment() {
        startFragment(new ForumsListFragment(), false, AllConstants.FragmentTag.ProjListTag);
        mBottomNav.setVisibility(View.VISIBLE);
    }

    public void startForumsListFragment(int list, String tag) {
        startFragment(ForumsListFragment.getInstance(list), false, AllConstants.FragmentTag.ProjListTag);
        mBottomNav.setVisibility(View.VISIBLE);
    }

    public void startSearchFragment() {
        startFragment(SearchFragment.getInstance(), false, AllConstants.FragmentTag.ProjSearchTag);
        mBottomNav.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1)
            finish();
        else
            super.onBackPressed();
    }
}
