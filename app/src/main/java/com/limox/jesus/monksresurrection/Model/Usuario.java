package com.limox.jesus.monksresurrection.Model;

import android.media.Image;

/**
 * Created by jesus on 8/11/16.
 */

public class Usuario {
    String mNombre;
    String mPassword;
    String mEmail;
    Image mFPerfil;

    public Usuario(String mNombre, String mPassword, String mEmail) {
        this.mNombre = mNombre;
        this.mPassword = mPassword;
        this.mEmail = mEmail;
    }

    public Usuario(String mNombre, String mPassword, String mEmail, Image mFPerfil) {
        this.mNombre = mNombre;
        this.mPassword = mPassword;
        this.mEmail = mEmail;
        this.mFPerfil = mFPerfil;
    }

    public String getmNombre() {
        return mNombre;
    }

    public void setmNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public Image getmFPerfil() {
        return mFPerfil;
    }

    public void setmFPerfil(Image mFPerfil) {
        this.mFPerfil = mFPerfil;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "mNombre='" + mNombre + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mEmail='" + mEmail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        boolean esIgual = true;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) esIgual = false;

        Usuario usuario = (Usuario) o;

        if (!getmNombre().equals(usuario.getmNombre())) esIgual = false;
        if (!getmEmail().equals(usuario.getmEmail())) esIgual = false;
        return esIgual;

    }

    @Override
    public int hashCode() {
        int result = getmNombre().hashCode();
        result = 31 * result + getmEmail().hashCode();
        return result;
    }
}
