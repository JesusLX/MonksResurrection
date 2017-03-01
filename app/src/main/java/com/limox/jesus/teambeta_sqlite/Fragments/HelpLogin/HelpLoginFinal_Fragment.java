package com.limox.jesus.teambeta_sqlite.Fragments.HelpLogin;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.limox.jesus.teambeta_sqlite.Model.User;
import com.limox.jesus.teambeta_sqlite.R;
import com.limox.jesus.teambeta_sqlite.Repositories.Users_Repository;

public class HelpLoginFinal_Fragment extends Fragment {

    String mEmail;
    User mUser;
    TextView mTxvUserName;
    TextView mTxvUserPassword;
    ImageView mIvImageProfile;

    public static HelpLoginFinal_Fragment newInstance(Bundle args) {

        HelpLoginFinal_Fragment fragment = new HelpLoginFinal_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_help_login_final,container,false);

        mTxvUserName = (TextView) rootView.findViewById(R.id.hlf_txvUserName);
        mTxvUserPassword = (TextView) rootView.findViewById(R.id.hlf_txvUserPassword);
        mIvImageProfile = (ImageView) rootView.findViewById(R.id.hlf_ivProfilePicture);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fill();
    }

    /**
     * Fill the views of the fragment
     */
    private void fill()
    {
        mEmail = getArguments().getString("email");
        mUser = Users_Repository.get().getUserByEmail(mEmail);

        mIvImageProfile.setImageResource( mUser.getProfilePicture());
        mTxvUserName.setText(mUser.getName());
        mTxvUserPassword.setText(mUser.getPassword());
    }
}
