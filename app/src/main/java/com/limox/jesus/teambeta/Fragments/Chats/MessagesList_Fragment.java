package com.limox.jesus.teambeta.Fragments.Chats;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.google.firebase.appindexing.builders.PersonBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.limox.jesus.teambeta.Interfaces.ChatsManagerPresenter;
import com.limox.jesus.teambeta.Model.Chat;
import com.limox.jesus.teambeta.Model.Message;
import com.limox.jesus.teambeta.Notifications.PushNotification;
import com.limox.jesus.teambeta.Presenter.ChatsManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.db.FirebaseContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class MessagesList_Fragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, ChatsManagerPresenter.View.ChatsManager {

    private static final String TAG = "MESSAGE_FRAGMENT";
    private ImageButton mSendButton;
    private Chat mChat;
    private Toolbar mToolbar;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private EditText mMessageEditText;
    private ImageView mAddMessageImageView;
    private static final int REQUEST_INVITE = 1;
    private static final int REQUEST_IMAGE = 2;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 165;
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private String mUserKey;
    private String forumKey;
    private SharedPreferences mSharedPreferences;
    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<Message, MessageViewHolder>
            mFirebaseAdapter;
    private String chatKey;

    private String getMessageUrl() {
        return "chats/" + forumKey + "/" + chatKey + "/" + FirebaseContract.Chats.NODE_MESSAGES;
    }

    private String getFullMessagesUrl() {
        return "https://team-beta-f34f4.firebaseio.com/" + getMessageUrl();
    }

    public MessagesList_Fragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Bundle chat) {
        MessagesList_Fragment fragment = new MessagesList_Fragment();
        fragment.setArguments(chat);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChatsManagerPresenterImpl mPresenter = new ChatsManagerPresenterImpl(this);
        if (getArguments() != null) {
            mChat = getArguments().getParcelable(AllConstants.Keys.Parcelables.CHAT_KEY);
            forumKey = getArguments().getString(AllConstants.Keys.SimpleBundle.ID_FORUM_KEY);
            if (mChat == null) {
                chatKey = getArguments().getString(AllConstants.Keys.SimpleBundle.ID_CHAT_KEY);
                mPresenter.optChat(forumKey, chatKey, new String[]{Users_Repository.get().getCurrentUser().getId(), getArguments().getString(AllConstants.Keys.SimpleBundle.ID_USER_KEY)}, null);
            } else {
                chatKey = mChat.getKey();
                if (Users_Repository.get().getCurrentForum() != null)
                    forumKey = Users_Repository.get().getCurrentForum().getKey();
            }
        }
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        // Set default username is anonymous.
        mUserKey = Users_Repository.get().getCurrentUser().getId();
    }

    private void initAdapter() {
        if (mChat != null) {
            mFirebaseAdapter = new FirebaseRecyclerAdapter<Message,
                    MessageViewHolder>(
                    Message.class,
                    R.layout.item_message,
                    MessageViewHolder.class,
                    mFirebaseDatabaseReference.child(getMessageUrl()).orderByChild(FirebaseContract.Chats.NODE_CREATION_DATE)) {

                @Override
                protected Message parseSnapshot(DataSnapshot snapshot) {
                    Message friendlyMessage = super.parseSnapshot(snapshot);
                    if (friendlyMessage != null) {
                        friendlyMessage.setKey(snapshot.getKey());
                    }
                    return friendlyMessage;
                }

                @Override
                protected void populateViewHolder(final MessageViewHolder viewHolder,
                                                  Message friendlyMessage, int position) {
                    if (friendlyMessage.getUserKey().equals(Users_Repository.get().getCurrentUser().getId())) {
                        viewHolder.messageContainer.setGravity(Gravity.START);
                        viewHolder.messageTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                        viewHolder.messageTextView.setBackgroundResource(R.drawable.my_msg_background);
                    } else {
                        viewHolder.messageContainer.setGravity(Gravity.END);
                        viewHolder.messageTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                        viewHolder.messageTextView.setBackgroundResource(R.drawable.your_msg_background);
                    }
                    if (friendlyMessage.getText() != null) {
                        viewHolder.messageTextView.setText(friendlyMessage.getText());
                        viewHolder.messageTextView.setVisibility(TextView.VISIBLE);
                    } else {
                        String imageUrl = friendlyMessage.getPhotoUrl();
                        if (imageUrl != null)
                            if (imageUrl.startsWith("gs://")) {
                                StorageReference storageReference = FirebaseStorage.getInstance()
                                        .getReferenceFromUrl(imageUrl);
                                storageReference.getDownloadUrl().addOnCompleteListener(
                                        new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                if (task.isSuccessful()) {
                                                    String downloadUrl = task.getResult().toString();
                                                    Glide.with(viewHolder.messageImageView.getContext())
                                                            .load(downloadUrl)
                                                            .into(viewHolder.messageImageView);
                                                } else {
                                                    Log.w("MESSAGE_LIST", "Getting download url was not successful.",
                                                            task.getException());
                                                }
                                            }
                                        });
                            } else {
                                Glide.with(viewHolder.messageImageView.getContext())
                                        .load(friendlyMessage.getPhotoUrl())
                                        .into(viewHolder.messageImageView);
                            }
                        viewHolder.messageImageView.setVisibility(ImageView.VISIBLE);
                        viewHolder.messageTextView.setVisibility(TextView.GONE);
                    }

                    if (friendlyMessage.getText() != null) {
                        // write this message to the on-device index
                        FirebaseAppIndex.getInstance()
                                .update(getMessageIndexable(friendlyMessage));
                    }
                    // write this message to the on-device index
                    FirebaseAppIndex.getInstance().update(getMessageIndexable(friendlyMessage));

                    // log a view action on it
                    FirebaseUserActions.getInstance().end(getMessageViewAction(friendlyMessage));
                }
            };

            mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                    int lastVisiblePosition =
                            mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                    // If the recycler view is initially being loaded or the
                    // user is at the bottom of the list, scroll to the bottom
                    // of the list to show the newly added message.
                    if (lastVisiblePosition == -1 ||
                            (positionStart >= (friendlyMessageCount - 1) &&
                                    lastVisiblePosition == (positionStart - 1))) {
                        mMessageRecyclerView.scrollToPosition(positionStart);
                    }
                }
            });
            mMessageRecyclerView.setAdapter(mFirebaseAdapter);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        // Initialize ProgressBar and RecyclerView.
        mMessageRecyclerView = (RecyclerView) rootView.findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        mMessageEditText = (EditText) rootView.findViewById(R.id.messageEditText);


        mSendButton = (ImageButton) rootView.findViewById(R.id.sendButton);


        mAddMessageImageView = (ImageView) rootView.findViewById(R.id.addMessageImageView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mSharedPreferences
                .getInt(AllConstants.Keys.SimpleBundle.MESSAGE_LENGTH_KEY, DEFAULT_MSG_LENGTH_LIMIT))});
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Message friendlyMessage = new
                        Message(mMessageEditText.getText().toString(),
                        mUserKey,
                        "",
                        new Date().getTime() /* no image */);
                mFirebaseDatabaseReference.child(getMessageUrl())
                        .push().setValue(friendlyMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        PushNotification notification = new PushNotification();
                        notification.setChatMessage(friendlyMessage, Users_Repository.get().getCurrentUser().getName(), forumKey, chatKey);
                        notification.setFromUser(Users_Repository.get().getCurrentUser().getToken());
                        notification.setToUser(mChat.optOtherUserToken(Users_Repository.get().getCurrentUser().getId()));
                        notification.setType(PushNotification.TYPE_NEW_MESSAGE);
                        notification.pushNotification(mChat.optOtherUserToken(Users_Repository.get().getCurrentUser().getId()));
                    }
                });
                mMessageEditText.setText("");
            }
        });
        mAddMessageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });
        if (mChat != null) {
            mSendButton.setEnabled(false);
            mToolbar.setTitle(mChat.optName());
        }
        initAdapter();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    final Uri uri = data.getData();
                    Log.d(TAG, "Uri: " + uri.toString());

                    Message tempMessage = new Message(null, mUserKey, uri.toString(),
                            new Date().getTime());
                    mFirebaseDatabaseReference.child(getMessageUrl()).push()
                            .setValue(tempMessage, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError,
                                                       DatabaseReference databaseReference) {
                                    if (databaseError == null) {
                                        String key = databaseReference.getKey();
                                        StorageReference storageReference =
                                                FirebaseStorage.getInstance()
                                                        .getReference(Users_Repository.get().getCurrentUser().getId())
                                                        .child(key)
                                                        .child(uri.getLastPathSegment());

                                        putImageInStorage(storageReference, uri, key);
                                    } else {
                                        Log.w(TAG, "Unable to write message to database.",
                                                databaseError.toException());
                                    }
                                }
                            });
                }
            }
        }
    }

    private Indexable getMessageIndexable(Message friendlyMessage) {
        PersonBuilder sender = Indexables.personBuilder()
                .setIsSelf(mUserKey.equals(friendlyMessage.getText()))
                .setName(friendlyMessage.getUserKey())
                .setUrl(getFullMessagesUrl().concat(friendlyMessage.getKey()));

        PersonBuilder recipient = Indexables.personBuilder()
                .setName(mUserKey)
                .setUrl(getFullMessagesUrl().concat(friendlyMessage.getKey()));

        Indexable messageToIndex = Indexables.messageBuilder()
                .setName(friendlyMessage.getUserKey())
                .setUrl(getFullMessagesUrl().concat(friendlyMessage.getKey()))
                .setSender(sender)
                .setRecipient(recipient)
                .build();

        return messageToIndex;
    }

    private Action getMessageViewAction(Message friendlyMessage) {
        return new Action.Builder(Action.Builder.VIEW_ACTION)
                .setObject(friendlyMessage.getUserKey(), getFullMessagesUrl().concat(friendlyMessage.getKey()))
                .setMetadata(new Action.Metadata.Builder().setUpload(false))
                .build();
    }

    private void putImageInStorage(StorageReference storageReference, Uri uri, final String key) {
        storageReference.putFile(uri).addOnCompleteListener(getActivity(),
                new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Message friendlyMessage = new Message("", mUserKey,
                                    task.getResult().getMetadata().getDownloadUrl().toString(), new Date().getTime());
                            mFirebaseDatabaseReference.child(getMessageUrl()).child(key)
                                    .setValue(friendlyMessage);
                        } else {
                            Log.w(TAG, "Image upload task was not successful.",
                                    task.getException());
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onChatsReceived(HashMap<String, ArrayList<Chat>> singleChatData) {
    }

    @Override
    public void onChatReceived(Chat chat) {
        mChat = chat;
        mSendButton.setEnabled(true);
        mToolbar.setTitle(mChat.optName());
        initAdapter();

    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;
        public ImageView messageImageView;
        public LinearLayout messageContainer;

        public MessageViewHolder(View v) {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messageImageView = (ImageView) itemView.findViewById(R.id.messageImageView);
            messageContainer = (LinearLayout) itemView.findViewById(R.id.messageContainer);
        }
    }
}
