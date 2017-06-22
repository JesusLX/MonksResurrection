package com.limox.jesus.teambeta.Presenter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.ChatsManagerPresenter;
import com.limox.jesus.teambeta.Model.Chat;
import com.limox.jesus.teambeta.Model.Message;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.db.FirebaseContract;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Presenter class to manage Chats
 * Created by Jesus on 07/06/2017.
 */
public class ChatsManagerPresenterImpl implements ChatsManagerPresenter {
    private View.ChatsManager view;
    private User lastUserFound;

    public ChatsManagerPresenterImpl(View.ChatsManager view) {
        this.view = view;
    }

    @Override
    public void createChats(final String forumKey, final String[] participantsKeys) {
        final String newChatKey = FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Chats.ROOT_NODE).child(forumKey).push().getKey();

        FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Chats.ROOT_NODE).
                child(forumKey).child(newChatKey).child(FirebaseContract.Chats.NODE_PARTICIPANTS).setValue(new ArrayList<>(Arrays.asList(participantsKeys))).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                addChat(forumKey, newChatKey, participantsKeys);
            }
        });
    }

    private User containsUser(ArrayList<Chat> allChats, String userKey) {
        if (lastUserFound != null) {
            if (lastUserFound.getId().equals(userKey))
                return lastUserFound;
        }

        for (Chat chat : allChats) {
            for (int i = 0; i < chat.getUsersData().size(); i++) {
                if (chat.getUsersData().get(i).getId().equals(userKey)) {
                    lastUserFound = chat.getUsersData().get(i);
                    return chat.getUsersData().get(i);
                }
            }
        }
        return null;
    }

    @Override
    public void optChat(String forumKey, final ArrayList<Chat> chatsKey) {
        final ArrayList<Chat> allChats = new ArrayList<>();
        for (int i = 0; i < chatsKey.size(); i++) {
            final int finalI = i;
            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Chats.ROOT_NODE).child(forumKey).child(chatsKey.get(i).getKey()).child(FirebaseContract.Chats.NODE_PARTICIPANTS).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    final Chat thisChat = new Chat(chatsKey.get(finalI).getKey());
                    allChats.add(finalI, thisChat);

                    for (DataSnapshot userKey : dataSnapshot.getChildren()) {

                        final User alUserData = containsUser(allChats, userKey.getValue().toString());

                        if (alUserData != null) {
                            thisChat.getUsersData().add(alUserData);
                            if (thisChat.getUsersData().size() == dataSnapshot.getChildrenCount()) {
                                if (view != null)
                                    view.onChatReceived(thisChat);
                            }
                        } else {
                            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.User.ROOT_NODE).
                                    child(userKey.getValue().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot otherDataSnapshot) {
                                    User alUserData = new User();
                                    alUserData.setIdUser(otherDataSnapshot.getKey());
                                    alUserData.setName(otherDataSnapshot.child(FirebaseContract.User.NODE_NAME).getValue().toString());
                                    alUserData.setProfilePicture(otherDataSnapshot.child(FirebaseContract.User.NODE_PHOTO_URL).getValue().toString());
                                    alUserData.setToken(otherDataSnapshot.child(FirebaseContract.User.NODE_TOKEN).getValue() != null ? otherDataSnapshot.child(FirebaseContract.User.NODE_TOKEN).getValue().toString() : "");
                                    thisChat.getUsersData().add(alUserData);
                                    if (thisChat.getUsersData().size() == dataSnapshot.getChildrenCount()) {
                                        if (view != null)
                                            view.onChatReceived(thisChat);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void optChat(String forumKey, String chatKey, final String[] usersKey, ValueEventListener userListener) {
        final Chat superUserData = new Chat();
        superUserData.setKey(chatKey);
        for (String anUsersKey : usersKey) {
            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.User.ROOT_NODE).
                    child(anUsersKey).addListenerForSingleValueEvent(userListener != null ? userListener : new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User alUserData = new User();
                    alUserData.setIdUser(dataSnapshot.getKey());
                    alUserData.setName(dataSnapshot.child(FirebaseContract.User.NODE_NAME).getValue().toString());
                    alUserData.setProfilePicture(dataSnapshot.child(FirebaseContract.User.NODE_PHOTO_URL).getValue().toString());
                    superUserData.getUsersData().add(alUserData);
                    if (superUserData.getUsersData().size() == usersKey.length) {
                        if (view != null)
                            view.onChatReceived(superUserData);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void optChat(final String forumKey, final ArrayList<Chat> chatKey, final String[] usersKey, ValueEventListener userListener) {
        if (chatKey.size() == 0) {
            createChats(forumKey, usersKey);
        } else {
            boolean someFound = false;
            for (int i = 0; i < chatKey.size(); i++) {
                if (chatKey.get(i).getSoftInfo().size() == usersKey.length) {
                    int coincidences = 0;
                    for (String anUsersKey1 : usersKey) {
                        if (chatKey.get(i).getSoftInfo().contains(anUsersKey1)) {
                            ++coincidences;
                        }
                    }
                    if (coincidences == usersKey.length) {
                        final Chat chat = chatKey.get(i);
                        someFound = true;
                        for (int j = 0; j < usersKey.length; j++) {
                            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.User.ROOT_NODE).child(usersKey[j]).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User alUserData = new User();
                                    alUserData.setIdUser(dataSnapshot.getKey());
                                    alUserData.setName(dataSnapshot.child(FirebaseContract.User.NODE_NAME).getValue().toString());
                                    alUserData.setProfilePicture(dataSnapshot.child(FirebaseContract.User.NODE_PHOTO_URL).getValue().toString());
                                    chat.getUsersData().add(alUserData);
                                    if (chat.getUsersData().size() == usersKey.length) {
                                        if (view != null)
                                            view.onChatReceived(chat);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        break;
                    }
                }
            }
            if (!someFound) {
                createChats(forumKey, usersKey);
            }
        }
    }

    @Override
    public void addChat(final String forumKey, final String chatKey, final String[] participantsKeys) {
        for (int i = 0; i < participantsKeys.length; i++) {
            final int finalI = i;
            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.User.ROOT_NODE).
                    child(participantsKeys[finalI]).child(FirebaseContract.User.NODE_CHATS).child(forumKey).child(chatKey).setValue(new ArrayList<>(Arrays.asList(participantsKeys))).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    if (finalI == participantsKeys.length - 1) {
                        optChat(forumKey, chatKey, participantsKeys, null);
                    }
                }
            });

        }
    }

    @Override
    public void sendMessage(String forumKey, String chatKey, Message message) {

    }
}