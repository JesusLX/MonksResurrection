package com.limox.jesus.teambeta;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.teambeta.Fragments.Chats.ChatsList_Fragment;
import com.limox.jesus.teambeta.Fragments.Chats.MessagesList_Fragment;
import com.limox.jesus.teambeta.Fragments.UserProfile.UserExternalProfile_Fragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.Utils.AllConstants;

import java.util.ArrayList;

public class Chats_Activity extends AppCompatActivity implements HomeOfFragments, UserExternalProfile_Fragment.OnUserExternalProfileFragmentListener {

    ArrayList<String> chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        if (getIntent().getStringArrayListExtra(AllConstants.Keys.SimpleBundle.CHATS_KEYS) != null) {
            chats = getIntent().getStringArrayListExtra(AllConstants.Keys.SimpleBundle.CHATS_KEYS);
        } else if (savedInstanceState != null) {
            chats = getIntent().getStringArrayListExtra(AllConstants.Keys.SimpleBundle.CHATS_KEYS);
        }
        startChatsView(chats);

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putStringArrayList(AllConstants.Keys.SimpleBundle.CHATS_KEYS, chats);
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

    public void startChatsView(ArrayList<String> chats) {
        startFragment(ChatsList_Fragment.newInstance(chats), true, "mssg");
    }
}
