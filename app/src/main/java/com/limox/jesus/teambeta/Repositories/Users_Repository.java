package com.limox.jesus.teambeta.Repositories;


import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesus on 11/11/16.
 */

public class Users_Repository {

    User currentUser;

    private static Users_Repository mUsers_Repository;
    List<User> mUsers;

    public static Users_Repository get() {
        if (mUsers_Repository == null) {
            mUsers_Repository = new Users_Repository();
        }
        return mUsers_Repository;
    }

    private Users_Repository() {
        mUsers = new ArrayList<User>();
        //TODO Create example users
        addExampleUsers();

    }

    /**
     * Create 1 admin user and 2 users for examples
     */
    private void addExampleUsers() {
        if (mUsers != null) {
           /* mUsers.add(new User(0, 0, "Admin1", "ejemplo1@gmail.com", "123", R.drawable.nino, AllConstants.ADMIN_TYPE_ID));
            mUsers.add(new User(1, 1, "User1", "ejemplo2@gmail.com", "123", R.drawable.monje, AllConstants.NORMALUSER_TYPE_ID));
            mUsers.add(new User(2, 2, "User2", "ejemplo3@gmail.com", "123", R.drawable.icomonge, AllConstants.NORMALUSER_TYPE_ID));*/
        }
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public void setUsers(List<User> users) {
        this.mUsers = users;
    }

    public boolean addUser(User user) {
        boolean allFine = false;
        if (!mUsers.contains(user)) {
            mUsers.add(user);
            allFine = true;
        }
        return allFine;
    }

    /*public boolean addUser(String userName, String password, String email) {
        boolean allFine = false;
        //User user = new User(0, mUsers.size(), userName, email, password, R.drawable.monje, AllConstants.NORMALUSER_TYPE_ID);
        if (!mUsers.contains(user)) {
            mUsers.add(user);
            allFine = true;
        }
        return allFine;
    }*/

    public boolean delUser(User user) {
        boolean allFine = false;
        if (mUsers.contains(user)) {
            mUsers.remove(mUsers.indexOf(user));
            allFine = true;
        }
        return allFine;
    }

    public User getUserById(int idUser) {
        User user = null;
        if (this.mUsers.contains(new User(idUser)))
            user = this.mUsers.get(mUsers.indexOf(new User(idUser)));

        return user;
    }

    public User getUserByEmail(String email) {
        User user = null;
        for (User tmpUser : mUsers) {
            if (tmpUser.getEmail().equals(email))
                return tmpUser;
        }
        return user;
    }


    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getUser(String userName) {
        //TODO No funciona el equals
        // if (mUsers.contains(new User(userName)))
        for (User tmpUser : mUsers) {
            if (tmpUser.getName().equals(userName))
                return tmpUser;

        }

        return null;

    }
    public boolean currentUserIsOwner(Post publicacion){
        boolean itIs = false;
        if (getCurrentUser().getIdUser() == publicacion.getIdUser()){
            itIs =true;
        }
        return itIs;

    }

    public boolean existUser(String userName) {
        boolean exist = false;
        for (User tmpUser : mUsers) {
            if (tmpUser.getName().equals(userName))
                exist = true;
        }
        return exist;
    }
}
