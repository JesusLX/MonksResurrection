package com.limox.jesus.teambeta.Presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.CommentaryManagerPresenter;
import com.limox.jesus.teambeta.Model.Commentary;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.UIUtils;
import com.limox.jesus.teambeta.db.FirebaseContract;

import java.util.ArrayList;

/**
 * Presenter class to manage Chats messages
 * Created by Jesus on 15/06/2017.
 */
public class CommentaryManagerPresenterImpl implements CommentaryManagerPresenter {

    private View mView;

    public CommentaryManagerPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getComments(String forumKey, String postKey) {
        if (UIUtils.isNetworkAvailable(mView.getContext())) {
            final ArrayList<User> users = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Forums.ROOT_NODE)
                    .child(forumKey)
                    .child(FirebaseContract.Posts.ROOT_NODE)
                    .child(postKey).child(FirebaseContract.COMMENTS.ROOT_NODE).orderByChild(FirebaseContract.COMMENTS.NODE_DATE).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    final ArrayList<Commentary> comments = new ArrayList<>();
                    for (DataSnapshot comm :
                            dataSnapshot.getChildren()) {
                        final Commentary c = comm.getValue(Commentary.class);
                        c.setKey(comm.getKey());
                        if (users.contains(c.getKeyUser())) {
                            User tmp = users.get(users.indexOf(c));
                            c.setUserName(tmp.getName());
                            c.setUserImgProf(tmp.getProfilePicture());
                            comments.add(c);
                        } else {
                            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.User.ROOT_NODE).child(c.getKeyUser()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot userDataSnapshot) {
                                    User user = new User();
                                    user.setIdUser(userDataSnapshot.getKey());
                                    user.setName(userDataSnapshot.child(FirebaseContract.User.NODE_NAME).getValue().toString());
                                    user.setProfilePicture(userDataSnapshot.child(FirebaseContract.User.NODE_PHOTO_URL).getValue().toString());
                                    c.setKeyUser(user.getId());
                                    c.setUserName(user.getName());
                                    c.setUserImgProf(user.getProfilePicture());
                                    users.add(user);
                                    comments.add(c);
                                    if (comments.size() == dataSnapshot.getChildrenCount())
                                        mView.onCommentsObtained(comments);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    if (comments.size() == dataSnapshot.getChildrenCount())
                        mView.onCommentsObtained(comments);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    UIUtils.snackBar(mView.getView(), R.string.connection_error);
                }
            });
        } else {
            UIUtils.snackBar(mView.getView(), R.string.connection_error);
        }
    }

    @Override
    public void sendComment(String forumKey, String postKey, Commentary commentary) {
        if (UIUtils.isNetworkAvailable(mView.getContext())) {
            String key = FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Forums.ROOT_NODE)
                    .child(forumKey)
                    .child(FirebaseContract.Posts.ROOT_NODE)
                    .child(postKey).child(FirebaseContract.COMMENTS.ROOT_NODE).push().getKey();

            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Forums.ROOT_NODE)
                    .child(forumKey)
                    .child(FirebaseContract.Posts.ROOT_NODE)
                    .child(postKey).child(FirebaseContract.COMMENTS.ROOT_NODE)
                    .child(key).setValue(commentary);
        } else {
            UIUtils.snackBar(mView.getView(), R.string.connection_error);
        }
    }
}
