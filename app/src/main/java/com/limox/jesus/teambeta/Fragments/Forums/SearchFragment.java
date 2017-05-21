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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.limox.jesus.teambeta.Adapters.RecyclerView.ForumsListRecyclerAdapter;
import com.limox.jesus.teambeta.Adapters.RecyclerView.TagsRecyclerAdapter;
import com.limox.jesus.teambeta.Interfaces.SearchManager;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Presenter.SearchManagerImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.AllConstants;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements SearchManager.View, TagsRecyclerAdapter.OnTagsViewHolderListener, ForumsListRecyclerAdapter.ForumsListRecyclerAdapterListener {

    private ForumsListFragment.OnForumsListFragmentListener mListener;
    private SearchManagerImpl mPresenter;
    private TagsRecyclerAdapter mTagsAdapter;
    private ForumsListRecyclerAdapter mForumsAdapter;
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
        mTagsAdapter = new TagsRecyclerAdapter(new ArrayList<String>(), this);
        mForumsAdapter = new ForumsListRecyclerAdapter(getContext(), new ArrayList<Forum>(), this);

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
        rvTags.setAdapter(mTagsAdapter);
        rvTags.setLayoutManager(new LinearLayoutManager(getContext()));
        rvForums.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvForums.setAdapter(mForumsAdapter);
        edtSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (!event.isShiftPressed()) {
                        // the user is done typing.
                        mPresenter.searchCoincidences(v.getText().toString());

                        return true; // consume.
                    }
                }
                return false; // pass on to other listeners.
            }
        });
        edtSearchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                refresh(hasFocus);
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
    public void onNamesFound(ArrayList<String> tags) {
        mTagsAdapter.setData(tags);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onForumsFound(ArrayList<Forum> forums) {
        if (forums.size() == 0) {
            //TODO poner que no hay nada
        } else if (forums.size() == 1) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AllConstants.Keys.Parcelables.FORUM, forums.get(0));
            mListener.startViewForum(bundle);
        } else {
            mForumsAdapter.addAll(forums);
            refresh(false);
        }
    }

    @Override
    public void onItemPressed(String tag) {
        mPresenter.searchForums(tag);
    }

    @Override
    public void onForumClicked(Forum forum) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AllConstants.Keys.Parcelables.FORUM, forum);
        mListener.startViewForum(bundle);
    }

    private void refresh(boolean hasFocus) {
        if (hasFocus) {
            rvTags.setVisibility(View.VISIBLE);
            rvForums.setVisibility(View.GONE);
        } else {
            rvTags.setVisibility(View.GONE);
            rvForums.setVisibility(View.VISIBLE);
        }
    }
}
