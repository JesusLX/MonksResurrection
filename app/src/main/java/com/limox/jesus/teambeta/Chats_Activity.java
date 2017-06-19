package com.limox.jesus.teambeta;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.teambeta.Fragments.Chats.ChatsList_Fragment;
import com.limox.jesus.teambeta.Fragments.Chats.MessagesList_Fragment;
import com.limox.jesus.teambeta.Fragments.UserProfile.UserExternalProfile_Fragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.Model.Chat;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;

import java.util.ArrayList;

public class Chats_Activity extends AppCompatActivity implements HomeOfFragments, ChatsList_Fragment.OnChatListFragmentListener {

    ArrayList<Chat> chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        if (getIntent().getExtras().getString(AllConstants.Keys.SimpleBundle.ID_USER_KEY) != null && getIntent().getExtras().getString(AllConstants.Keys.SimpleBundle.ID_CHAT_KEY) != null && getIntent().getExtras().getString(AllConstants.Keys.SimpleBundle.ID_FORUM_KEY) != null) {
            TeamBetaApplication.setCurrentActivity("MessagesList_Fragment_" + getIntent().getExtras().getString(AllConstants.Keys.SimpleBundle.ID_CHAT_KEY));
            startChat(getIntent().getExtras());
        } else if (getIntent().getExtras().getParcelable(AllConstants.Keys.Parcelables.CHAT_KEY) != null) {
            TeamBetaApplication.setCurrentActivity("MessagesList_Fragment_" + ((Chat) getIntent().getExtras().getParcelable(AllConstants.Keys.Parcelables.CHAT_KEY)).optOtherUserKey(Users_Repository.get().getCurrentUser().getId()));
            startChat(getIntent().getExtras());
        } else if (getIntent().getStringArrayListExtra(AllConstants.Keys.SimpleBundle.CHATS_KEYS) != null) {
            TeamBetaApplication.setCurrentActivity("Chats_Fragment");
            chats = getIntent().getParcelableArrayListExtra(AllConstants.Keys.SimpleBundle.CHATS_KEYS);
            startChatsView(getIntent().getExtras());
        } else if (savedInstanceState != null) {
            TeamBetaApplication.setCurrentActivity("Chats_Fragment");
            chats = getIntent().getParcelableArrayListExtra(AllConstants.Keys.SimpleBundle.CHATS_KEYS);
            startChatsView(getIntent().getExtras());
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList(AllConstants.Keys.SimpleBundle.CHATS_KEYS, chats);
    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack, String tag) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(R.id.fragment_container, fragment, tag);
        ft.commit();
    }

    public void startChat(Bundle chat) {
        startFragment(MessagesList_Fragment.newInstance(chat), true, "mssg");
    }

    public void startChatsView(Bundle chats) {
        startFragment(ChatsList_Fragment.newInstance(chats), true, "mssg");
    }

    @Override
    public void onChatPressed(Bundle chat) {
        startChat(chat);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            super.onBackPressed();
        else
            finish();
    }
}
