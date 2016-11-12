package com.limox.jesus.monksresurrection.Singleton;


import com.limox.jesus.monksresurrection.Model.User;
import com.limox.jesus.monksresurrection.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesus on 11/11/16.
 */

public class Users_Singleton {

    private static Users_Singleton mUsers_Singleton;
    List<User> mUsers;

    public static Users_Singleton getUsers_Singleton(){
        if (mUsers_Singleton == null){
            mUsers_Singleton = new Users_Singleton();
        }
        return mUsers_Singleton;
    }

    private Users_Singleton(){
        mUsers = new ArrayList<User>();
        //TODO Create example users
        addExampleUsers();

    }

    /**
     * Create 1 admin user and 2 users for examples
     */
    private void addExampleUsers(){
        if (mUsers != null){
            mUsers.add(new User(0,0,"Admin1","ejemplo1@gmail.com","123", R.drawable.monje,0));
            mUsers.add(new User(1,1,"User1","ejemplo2@gmail.com","123", R.drawable.monje,1));
            mUsers.add(new User(2,2,"User2","ejemplo3@gmail.com","123", R.drawable.monje,1));
        }
    }
    public List<User> getUsers(){
        return mUsers;
    }
    public void setUsers(List<User> users){
        this.mUsers = users;
    }
    public boolean addUser(User user){
        boolean allFine = false;
        if (!mUsers.contains(user)){
            mUsers.add(user);
            allFine = true;
        }
        return allFine;
    }
    public boolean delUser(User user){
        boolean allFine = false;
        if (mUsers.contains(user)){
            mUsers.remove(mUsers.indexOf(user));
            allFine = true;
        }
        return allFine;
    }
    public User getUserById(int idUser){
        User user = null;
        if (this.mUsers.contains(new User(idUser)))
         user = this.mUsers.get(mUsers.indexOf(new User(idUser)));

        return user;
    }
}
