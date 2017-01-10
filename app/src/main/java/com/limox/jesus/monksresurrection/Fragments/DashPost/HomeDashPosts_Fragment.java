package com.limox.jesus.monksresurrection.Fragments.DashPost;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.Adapters.PostTabsAdapter;
import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Repositories.Users_Repository;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeDashPosts_Fragment extends Fragment  {

    private Toolbar mToolbar;
    private CircleImageView mCiProfilePicture;
    private TextView mTxvCurrentTab;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PostTabsAdapter mAdapter;
    private OnHomeDashPostFragmentListener mCallback;
    private FloatingActionButton mfabAddPost;

  

    public interface OnHomeDashPostFragmentListener {
        void onOpenNavigatorDrawer();
        void startAddPost();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeDashPostFragmentListener)
            mCallback = (OnHomeDashPostFragmentListener) context;
        else
            throw new ClassCastException(context.toString()+" must implement OnHomeDashPostFragmentListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAdapter = new PostTabsAdapter(getChildFragmentManager(),getResources().getStringArray(R.array.dash_post_tabs));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_home_dash_posts,container,false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.hdp_tbTitle);
        mTabLayout= (TabLayout) rootView.findViewById(R.id.hdp_tabLayout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.hdp_vpContainer);
        mCiProfilePicture = (CircleImageView) rootView.findViewById(R.id.hdp_civProfilePicture);
        mTxvCurrentTab = (TextView) rootView.findViewById(R.id.hdp_txvProfileName);
        mfabAddPost = (FloatingActionButton) rootView.findViewById(R.id.hdp_fabAdd);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*  mViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                DrawFromBackTransformer trans = new DrawFromBackTransformer();
                trans.transformPage(page,position);
            }
        });*/
        mViewPager.setAdapter(mAdapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTxvCurrentTab.setText(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mCiProfilePicture.setImageResource(Users_Repository.get().getCurrentUser().getProfilePicture());
        mTxvCurrentTab.setText(mAdapter.getPageTitle(0));
        mCiProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onOpenNavigatorDrawer();
            }
        });
        mfabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.startAddPost();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mToolbar = null;
        mTabLayout = null;
        mViewPager = null;
    }

}
