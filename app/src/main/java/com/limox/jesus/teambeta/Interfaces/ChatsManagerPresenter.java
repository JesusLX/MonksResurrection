package com.limox.jesus.teambeta.Interfaces;

import android.content.Context;

import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Model.Chat;
import com.limox.jesus.teambeta.Model.Message;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jesus on 07/06/2017.
 */

public interface ChatsManagerPresenter {

    void createChats(String forumKey, String[] participantsKeys);

    void optChat(String forumKey, ArrayList<Chat> chatsKey);

    void optChat(String forumKey, String chatKey, String[] usersKey, ValueEventListener userListener);

    void optChat(String forumKey, ArrayList<Chat> chatKey, String[] usersKey, ValueEventListener userListener);

    void addChat(String forumKey, String chatKey, String[] participantsKeys);

    void sendMessage(String forumKey, String chatKey, Message message);


    interface View {
        Context getContext();

        interface ChatsManager {
            void onChatsReceived(HashMap<String, ArrayList<Chat>> singleChatData);

            void onChatReceived(Chat chat);
        }

        interface MessagesManager {
            void onMessageSended(String messageKey);
        }
    }
}
