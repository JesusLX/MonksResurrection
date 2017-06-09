package com.limox.jesus.teambeta;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.teambeta.Fragments.Forums.CreateForumFragment;
import com.limox.jesus.teambeta.Fragments.Forums.ForumViewFragment;
import com.limox.jesus.teambeta.Fragments.Forums.ForumsListFragment;
import com.limox.jesus.teambeta.Fragments.Forums.SearchFragment;
import com.limox.jesus.teambeta.Fragments.Forums.UsersListFragment;
import com.limox.jesus.teambeta.Fragments.UserProfile.UserExternalProfile_Fragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;

public class SelectProject_Activity extends AppCompatActivity implements HomeOfFragments, UsersListFragment.OnUsersListFragmentListener, CreateForumFragment.OnFragmentInteractionListener, UserExternalProfile_Fragment.OnUserExternalProfileFragmentListener, ForumsListFragment.OnForumsListFragmentListener, ForumViewFragment.OnForumViewFragmentListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_project);
        //startCreateForumFragment();
        startUserProfile(Users_Repository.get().getCurrentUser().optBundle(), false);
    }

    @Override
    public void startUserProfile(Bundle user, boolean retain) {
        startFragment(UserExternalProfile_Fragment.newInstance(user), retain, AllConstants.FragmentTag.ProjListTag);
    }


    @Override
    public void startFragment(Fragment fragment, boolean addStack, String tag) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(com.limox.jesus.teambeta.R.id.frContainer, fragment, tag);
        ft.commit();
    }

    @Override
    public void startCreateForumFragment() {
        startFragment(new CreateForumFragment(), true, AllConstants.FragmentTag.CreateForum);
    }

    @Override
    public void startHomeFragment() {
        startActivity(new Intent(SelectProject_Activity.this, Home_Activity.class));
    }

    @Override
    public void startViewForum(Bundle forum) {
        startFragment(ForumViewFragment.newInstance(forum), true, AllConstants.FragmentTag.FragmentView);
    }

    @Override
    public void startForumsListFragment() {
        startUserProfile(Users_Repository.get().getCurrentUser().optBundle(), true);
    }

    public void startSearchFragment() {
        startFragment(SearchFragment.getInstance(), true, AllConstants.FragmentTag.ProjSearchTag);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onUserClicked(Bundle user) {
        startUserProfile(user, true);
    }

    @Override
    public void startChat(String mIdUser) {
        Intent i = new Intent(SelectProject_Activity.this, Chats_Activity.class);
        i.putExtra(AllConstants.Keys.SimpleBundle.ID_USER_KEY, mIdUser);
        startActivity(i);
    }
}
