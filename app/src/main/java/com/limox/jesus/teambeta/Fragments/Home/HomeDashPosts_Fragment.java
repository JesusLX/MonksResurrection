package com.limox.jesus.teambeta.Fragments.Home;

import android.app.Activity;
import android.graphics.PorterDuff;
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

import com.limox.jesus.teambeta.Adapters.TabsAdapter.HomePostTabsAdapter;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;

import java.lang.reflect.Field;

import de.hdodenhof.circleimageview.CircleImageView;
import it.sephiroth.android.library.picasso.Picasso;

public class HomeDashPosts_Fragment extends Fragment  {

    private Toolbar mToolbar;
    private CircleImageView mCiProfilePicture;
    private TextView mTxvCurrentTab;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private HomePostTabsAdapter mAdapter;
    private OnHomeDashPostFragmentListener mCallback;
    private FloatingActionButton mfabAddPost;


    public interface OnHomeDashPostFragmentListener {
        void onOpenNavigatorDrawer();
        void startAddPost();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnHomeDashPostFragmentListener)
            mCallback = (OnHomeDashPostFragmentListener) activity;
        else
            throw new ClassCastException(activity.toString()+" must implement OnHomeDashPostFragmentListener");
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mAdapter = new HomePostTabsAdapter(getChildFragmentManager(),getResources().getStringArray(R.array.dash_post_tabs));
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

        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);


        Picasso.with(getContext()).load(Users_Repository.get().getCurrentUser().getProfilePicture()).into(mCiProfilePicture);

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
        int[] icons = {R.drawable.ic_action_bug,R.drawable.ic_action_fix};
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(icons[i]);
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                mTxvCurrentTab.setText(mAdapter.getTitles()[tab.getPosition()]);

                //mTxvCurrentTab.setText(mAdapter.getPageTitle(mAdapter.getItemPosition(tab)));
                //   tab.getIcon().setColorFilter(getContext().getResources().getColor(R.color.tabIndicatorColor), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                tab.getIcon().setColorFilter(getContext().getResources().getColor(R.color.tabUnSelectColor), PorterDuff.Mode.SRC_IN);
            }//

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTxvCurrentTab.setText(mAdapter.getTitles()[0]);
        // mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getIcon().setColorFilter(getContext().getResources().getColor(R.color.tabIndicatorColor), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mToolbar = null;
        mTabLayout = null;
        mViewPager = null;
    }

}
