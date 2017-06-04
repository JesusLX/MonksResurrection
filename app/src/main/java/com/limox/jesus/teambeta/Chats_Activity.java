package com.limox.jesus.teambeta;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.teambeta.Fragments.Chats.MessagesList_Fragment;
import com.limox.jesus.teambeta.Fragments.UserProfile.UserExternalProfile_Fragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.Utils.AllConstants;

public class Chats_Activity extends AppCompatActivity implements HomeOfFragments, UserExternalProfile_Fragment.OnUserExternalProfileFragmentListener {

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        if (getIntent().getStringExtra(AllConstants.Keys.SimpleBundle.ID_USER_KEY) != null) {
            userId = getIntent().getStringExtra(AllConstants.Keys.SimpleBundle.ID_USER_KEY);
        } else if (savedInstanceState != null) {
            userId = savedInstanceState.getString(AllConstants.Keys.SimpleBundle.ID_USER_KEY);
        }
        startChat(userId);

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(AllConstants.Keys.SimpleBundle.ID_USER_KEY, userId);
    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack, String tag) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(R.id.fragment_container, fragment, tag);
        ft.commit();
    }

    @Override
    public void startChat(String mIdUser) {
        startFragment(new MessagesList_Fragment(), true, "mssg");
    }
}
