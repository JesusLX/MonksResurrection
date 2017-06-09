package com.limox.jesus.teambeta.Utils;

import android.util.Patterns;

import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;

/**
 * Valida con sus metodos estáticos
 * all the returns are strings references
 */

public class Validate {

    public static int MESSAGE_OK = R.string.message_all_ok;

    public static int validateName(String name) {
        int referenceMensaje = R.string.message_all_ok;
        if (name.length() > AllConstants.USERNAME_MAX_LENGTH) {
            referenceMensaje = R.string.message_error_username_tooLong;
        }
        if (name.length() < AllConstants.USERNAME_MIN_LENGTH) {
            referenceMensaje = R.string.message_error_username_tooShort;
        }
        return referenceMensaje;
    }

    public static int validateEmail(String email) {
        int referenceMensaje = R.string.message_all_ok;
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            referenceMensaje = R.string.message_error_email_wrong;
        return referenceMensaje;
    }

    public static int validatePassword(String password) {
        int referenceMensaje = R.string.message_all_ok;
        if (password.length() > AllConstants.PASSWORD_MAX_LENGTH)
            referenceMensaje = R.string.message_error_password_tooLong;
        else if (password.length() <= AllConstants.PASSWORD_MIN_LENGTH)
            referenceMensaje = R.string.message_error_password_tooShort;
        else if (!containsDigit(password))
            referenceMensaje = R.string.message_error_password_case;
        else if (!containsChar(password))
            referenceMensaje = R.string.message_error_password_digit;

        return referenceMensaje;
    }

    private static boolean containsChar(String word) {
        for (char c : word.toCharArray()) {
            if (Character.isLetter(c)) return true;
        }
        return false;
    }

    private static boolean containsDigit(String word) {
        for (char c : word.toCharArray()) {
            if (Character.isDigit(c)) return true;
        }
        return false;
    }

    public static int validateRepeatedPassword(String password, String repeatedPassword) {
        int referenceMensaje = R.string.message_all_ok;

        if (!password.equals(repeatedPassword))
            referenceMensaje = R.string.message_error_password_match;

        return referenceMensaje;
    }

    public static int validateAccount(String userName, String password) {
        int messageError = R.string.message_error_nonexistent;
        User tmpUser;
        if ((tmpUser = Users_Repository.get().getUser(userName)) != null) {
            if (tmpUser.getName().equals(userName) && tmpUser.getPassword().equals(password))
                messageError = MESSAGE_OK;
        }
        return messageError;
    }
}
