package com.limox.jesus.teambeta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Presenter.UserManagerPresenterImpl;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.Preferences;
import com.limox.jesus.teambeta.db.FirebaseContract;

public class Start_Activity extends AppCompatActivity implements UserManagerPresenterImpl.View {

    UserManagerPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.limox.jesus.teambeta.R.layout.activity_start);
        mPresenter = new UserManagerPresenterImpl(this);
        if (!Preferences.loginCurrentUser(getContext(), new FirebaseContract.FirebaseUserCallback() {
            @Override
            public void onUserObtained(String idUser) {
                mPresenter.getUser(idUser);
            }

            @Override
            public void onUnsuccessful(Task<AuthResult> task) {
                startLogin();
                finish();
            }
        }, this)) {
            startLogin();
            finish();
        } else {
            if (Users_Repository.get().getCurrentUser() != null) {
                String id = Preferences.getSelectedForum();
                if (id == null) {
                    startSelectProject();
                    finish();
                } else {
                    FirebaseContract.Forums.optForum(id, new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Users_Repository.get().setCurrentForum(dataSnapshot.getValue(Forum.class));
                            startHome();
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            startSelectProject();
                            finish();
                        }
                    });
                }
            }
        }

    }


    @Override
    public void onUserObtained(User tryUser) {
        if (tryUser != null) {
            Users_Repository.get().setCurrentUser(tryUser);
            String id = Preferences.getSelectedForum();
            if (id == null) {
                startSelectProject();
            } else {
                FirebaseContract.Forums.optForum(id, new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Users_Repository.get().setCurrentForum(dataSnapshot.getValue(Forum.class));
                        startHome();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        startSelectProject();
                    }
                });
            }
        } else {
            startLogin();
        }
        finish();
    }

    private void startHome() {
        startActivity(new Intent(Start_Activity.this, Home_Activity.class));
    }

    private void startLogin() {
        startActivity(new Intent(Start_Activity.this, Login_Activity.class));
    }

    private void startSelectProject() {
        startActivity(new Intent(Start_Activity.this, SelectProject_Activity.class));
    }

    @Override
    public Context getContext() {
        return Start_Activity.this;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onUserCreated() {

    }
}
