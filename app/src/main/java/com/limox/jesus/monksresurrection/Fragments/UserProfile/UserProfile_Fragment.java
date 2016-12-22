package com.limox.jesus.monksresurrection.Fragments.UserProfile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limox.jesus.monksresurrection.Model.Post;
import com.limox.jesus.monksresurrection.Model.User;
import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

public class UserProfile_Fragment extends Fragment {

    private OnUserProfileFragmentListener mCallback;
    private User mUser;

    public UserProfile_Fragment() {
        // Required empty public constructor
    }


    public static UserProfile_Fragment newInstance(Bundle args) {
        UserProfile_Fragment fragment = new UserProfile_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(AllConstants.USER_PARCELABLE_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container);


        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserProfileFragmentListener) {
            mCallback = (OnUserProfileFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUserProfileFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    public interface OnUserProfileFragmentListener {
        void onOpenPostView(Post post);
    }
}
