package com.limox.jesus.teambeta.Fragments.CreatePost;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.AllConstants;


public class AddTags_Fragment extends Fragment {

    private String[] tags;
    private OnAddTagsFragmentListener mCallback;

    public AddTags_Fragment() {
        // Required empty public constructor
    }


    public static AddTags_Fragment newInstance(String[] tags) {
        AddTags_Fragment fragment = new AddTags_Fragment();
        Bundle args = new Bundle();
        args.putStringArray(AllConstants.Keys.SimpleBundle.ArrayTags, tags);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           tags = getArguments().getStringArray(AllConstants.Keys.SimpleBundle.ArrayTags);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_tags, container, false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCallback.onAddTagsClosing(tags);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnAddTagsFragmentListener) {
            mCallback = (OnAddTagsFragmentListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAddTagsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface OnAddTagsFragmentListener {
        void onAddTagsClosing(String[] tags);
    }
}
