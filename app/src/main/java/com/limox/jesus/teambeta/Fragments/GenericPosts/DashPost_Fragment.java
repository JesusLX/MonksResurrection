package com.limox.jesus.teambeta.Fragments.GenericPosts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.limox.jesus.teambeta.Adapters.PostArrayAdapter;
import com.limox.jesus.teambeta.Adapters.PostCursorAdapter;
import com.limox.jesus.teambeta.Interfaces.PostsListPresenter;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Presenter.PostsListsPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.AllConstants;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DashPost_Fragment extends Fragment implements PostsListPresenter.View {

    private PostArrayAdapter mAdapter;
    private ListView rvPosts;
    private PostsListPresenter mPresenter;

    private int typeList;
    private int idUser;


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
        setRetainInstance(true);
        mPresenter = new PostsListsPresenterImpl(this);


        if (getArguments() != null) {
            typeList =getArguments().getInt(AllConstants.TypeLists.TYPELIST_KEY);}
            idUser = getArguments().getInt(AllConstants.Keys.SimpleBundle.ID_USER_KEY,-1);
        if (savedInstanceState != null){
            typeList = savedInstanceState.getInt(AllConstants.TypeLists.TYPELIST_KEY);
            idUser = savedInstanceState.getInt(AllConstants.Keys.SimpleBundle.ID_USER_KEY,-1);
        }


        mAdapter = new PostArrayAdapter(getContext(), (PostArrayAdapter.OnPostViewHolderListener) getActivity());


    }

/*    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(AllConstants.ARRAYLIST_POST_PARCELABLE_KEY,mAdapter.getAllPosts());
    }*/

    @Override
    public void onStart() {
        super.onStart();
        switch (typeList){
            case Post.ALL:
                mPresenter.getAllPost(Post.ALL,idUser);
                break;
            case Post.FIXED:
                mPresenter.getAllPost(Post.FIXED,idUser);
                break;
            case Post.NOT_PUBLISHED:
                mPresenter.getAllPost(Post.NOT_PUBLISHED,idUser);
                break;
            case Post.PUBLISHED:
                mPresenter.getAllPost(Post.PUBLISHED,idUser);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dash_post, container, false);
        rvPosts = (ListView) rootView.findViewById(R.id.dp_lvList);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
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


    @Override
    public void setData(ArrayList<Post> posts) {
        mAdapter.setData(posts);
    }
}
