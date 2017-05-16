package com.limox.jesus.teambeta.Fragments.Forums;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.limox.jesus.teambeta.Adapters.RecyclerView.TagsRecyclerAdapter;
import com.limox.jesus.teambeta.Interfaces.SearchManager;
import com.limox.jesus.teambeta.Presenter.SearchManagerImpl;
import com.limox.jesus.teambeta.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements SearchManager.View, TagsRecyclerAdapter.OnTagsViewHolderListener {

    private ForumsListFragment.OnForumsListFragmentListener mListener;
    private SearchManagerImpl mPresenter;
    private TagsRecyclerAdapter mTagsAdapter;
    private EditText edtSearchBar;
    private RecyclerView rvTags;
    private RecyclerView rvForums;
    private static SearchFragment instance;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment getInstance() {
        if (instance == null)
            instance = new SearchFragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SearchManagerImpl(this);
        mTagsAdapter = new TagsRecyclerAdapter(null, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        edtSearchBar = (EditText) rootView.findViewById(R.id.edtSearch);
        rvTags = (RecyclerView) rootView.findViewById(R.id.rvTags);
        rvForums = (RecyclerView) rootView.findViewById(R.id.rvForums);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTags.setLayoutManager(new LinearLayoutManager(getContext()));
        rvForums.setLayoutManager(new GridLayoutManager(getContext(), 2));
        edtSearchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rvTags.setVisibility(View.VISIBLE);
                    rvForums.setVisibility(View.GONE);
                } else {
                    rvTags.setVisibility(View.GONE);
                    rvForums.setVisibility(View.VISIBLE);
                }
            }
        });
        edtSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mPresenter.searchCoincidences(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ForumsListFragment.OnForumsListFragmentListener) {
            mListener = (ForumsListFragment.OnForumsListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnForumsListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onTagsFound(ArrayList<String> tags) {
        mTagsAdapter.setData(tags);
    }

    @Override
    public void onItemPressed(String tag) {
        mPresenter.searchForums(tag);
    }
}
