package com.limox.jesus.teambeta_sqlite.Fragments.GenericPosts;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limox.jesus.teambeta_sqlite.Adapters.PostAdapterRecycler;
import com.limox.jesus.teambeta_sqlite.Model.Post;
import com.limox.jesus.teambeta_sqlite.R;
import com.limox.jesus.teambeta_sqlite.Utils.AllConstants;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DashPost_Fragment extends Fragment {

    private PostAdapterRecycler mAdapter;
    private RecyclerView rvPosts;

   /* public interface OnDashPostFragmentListener{

    }*/


    public static DashPost_Fragment newInstance(Bundle postList) {
        DashPost_Fragment fragment = new DashPost_Fragment();
        fragment.setArguments(postList);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ArrayList<Post> mPostList = getArguments().getParcelableArrayList(AllConstants.Keys.Parcelables.ARRAYLIST_POST_PARCELABLE_KEY);
            mAdapter = new PostAdapterRecycler(mPostList,getContext());
        }
        if (savedInstanceState != null){
            ArrayList<Post> mPostList = savedInstanceState.getParcelableArrayList(AllConstants.Keys.Parcelables.ARRAYLIST_POST_PARCELABLE_KEY);
            mAdapter = new PostAdapterRecycler(mPostList,getContext());
        }
        setRetainInstance(true);

    }

/*    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(AllConstants.ARRAYLIST_POST_PARCELABLE_KEY,mAdapter.getAllPosts());
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dash_post, container, false);
        rvPosts = (RecyclerView) rootView.findViewById(R.id.dp_rvList);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPosts.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       /* if (context instanceof OnDashPostFragmentListener) {
            mCallback = (OnDashPostFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStartSettingsListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
