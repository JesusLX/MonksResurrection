package com.limox.jesus.teambeta.Presenter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.ChatsManagerPresenter;
import com.limox.jesus.teambeta.Model.Chat;
import com.limox.jesus.teambeta.Model.Message;
import com.limox.jesus.teambeta.db.FirebaseContract;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jesus on 07/06/2017.
 */

public class ChatsManagerPresenterImpl implements ChatsManagerPresenter {
    private View.ChatsManager view;
    private ArrayList<String> lastUserFound;

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

    private ArrayList<String> contaisUser(ArrayList<Chat> allChats, String userKey) {
        if (lastUserFound != null) {
            if (lastUserFound.contains(userKey))
                return lastUserFound;
        }

        for (Chat chat : allChats) {
            for (int i = 0; i < chat.getUsersData().size(); i++) {
                if (chat.getUsersData().get(i).contains(userKey))
                    lastUserFound = chat.getUsersData().get(i);
                return chat.getUsersData().get(i);
            }
        }
        return null;
    }

    @Override
    public void optChats(String forumKey, final ArrayList<String> chatsKey) {
        final ArrayList<Chat> allChats = new ArrayList<>();
        for (int i = 0; i < chatsKey.size(); i++) {
            final int finalI = i;
            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Chats.ROOT_NODE).child(chatsKey.get(i)).child(FirebaseContract.Chats.NODE_PARTICIPANTS).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    final Chat thisChat = new Chat(chatsKey.get(finalI));
                    allChats.add(finalI, thisChat);

                    for (DataSnapshot userKey : dataSnapshot.getChildren()) {

                        final ArrayList<String> alUserData = contaisUser(allChats, userKey.toString());

                        if (alUserData != null) {
                            thisChat.getUsersData().add(alUserData);
                            if (thisChat.getUsersData().size() == dataSnapshot.getChildrenCount()) {
                                view.onChatReceived(thisChat);
                            }
                        } else {
                            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.User.ROOT_NODE).
                                    child(userKey.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot otherDataSnapshot) {
                                    ArrayList<String> alUserData = new ArrayList<>();
                                    alUserData.add(otherDataSnapshot.getKey());
                                    alUserData.add(otherDataSnapshot.child(FirebaseContract.User.NODE_NAME).toString());
                                    alUserData.add(otherDataSnapshot.child(FirebaseContract.User.NODE_PHOTO_URL).toString());
                                    thisChat.getUsersData().add(alUserData);
                                    if (thisChat.getUsersData().size() == dataSnapshot.getChildrenCount()) {
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
        for (int i = 0; i < usersKey.length; i++) {
            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.User.ROOT_NODE).
                    child(usersKey[i]).addListenerForSingleValueEvent(userListener != null ? userListener : new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<String> data = new ArrayList<>();
                    data.add(dataSnapshot.getKey());
                    data.add(dataSnapshot.child(FirebaseContract.User.NODE_NAME).toString());
                    data.add(dataSnapshot.child(FirebaseContract.User.NODE_PHOTO_URL).toString());
                    superUserData.getUsersData().add(data);
                    if (superUserData.getUsersData().size() == usersKey.length) {
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
    public void optChat(final String forumKey, ArrayList<Chat> chatKey, final String[] usersKey, ValueEventListener userListener) {
        if (chatKey.size() == 0) {
            createChats(forumKey, usersKey);
        } else {
            for (int i = 0; i < chatKey.size(); i++) {
                if (chatKey.get(i).getUsersData().size() == usersKey.length) {
                    int coincidences = 0;
                    for (int j = 0; j < chatKey.get(i).getUsersData().size(); j++) {
                        for (int k = 0; k < chatKey.get(i).getUsersData().get(j).size(); k++) {
                            if (chatKey.get(i).getUsersData().get(j).contains(usersKey[k])) {
                                coincidences++;
                            }
                        }
                        if (coincidences == usersKey.length) {
                            view.onChatReceived(chatKey.get(i));
                        }
                    }
                }

                FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Chats.ROOT_NODE).
                        child(forumKey).child(chatKey.get(i).getKey()).addListenerForSingleValueEvent(userListener != null ? userListener : new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(FirebaseContract.Chats.NODE_PARTICIPANTS).getChildrenCount() == usersKey.length) {
                            boolean isthis = true;
                            for (int j = 0; j < usersKey.length; j++) {
                                if (!((ArrayList<String>) dataSnapshot.getValue()).contains(usersKey[j])) {
                                    isthis = false;
                                    break;
                                }
                            }
                            if (isthis) {
                                final Chat chat = new Chat();
                                chat.setKey(dataSnapshot.getKey());
                                for (int j = 0; j < usersKey.length; j++) {
                                    FirebaseDatabase.getInstance().getReference().child(FirebaseContract.User.ROOT_NODE).child(usersKey[j]).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            ArrayList<String> userData = new ArrayList<>();
                                            userData.add(dataSnapshot.getKey());
                                            userData.add(dataSnapshot.child(FirebaseContract.User.NODE_NAME).toString());
                                            userData.add(dataSnapshot.child(FirebaseContract.User.NODE_PHOTO_URL).toString());
                                            chat.getUsersData().add(userData);
                                            if (userData.size() == usersKey.length) {
                                                view.onChatReceived(chat);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                            } else {
                                createChats(forumKey, usersKey);
                            }
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