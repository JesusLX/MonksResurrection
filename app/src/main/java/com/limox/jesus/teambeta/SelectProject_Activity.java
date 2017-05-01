package com.limox.jesus.teambeta;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.teambeta.Fragments.Forums.CreateForumFragment;
import com.limox.jesus.teambeta.Fragments.Forums.ForumsListFragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.Utils.AllConstants;

public class SelectProject_Activity extends AppCompatActivity implements HomeOfFragments, CreateForumFragment.OnFragmentInteractionListener, ForumsListFragment.OnForumsListFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_project);

        if (savedInstanceState == null){
            startFragment(new ForumsListFragment(), true, AllConstants.FragmentTag.ProjListTag);
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
    }

    @Override
    public void startHomeFragment() {
        startActivity(new Intent(SelectProject_Activity.this, Home_Activity.class));
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1)
            finish();
        else
            super.onBackPressed();
    }

    @Override
    public void onForumCreated() {

    }
}
