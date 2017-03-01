package com.limox.jesus.teambeta_sqlite.Fragments.Admins;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limox.jesus.teambeta_sqlite.Adapters.AdminPostTabsAdapter;
import com.limox.jesus.teambeta_sqlite.R;
import com.limox.jesus.teambeta_sqlite.Repositories.Users_Repository;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminsDashPosts_Fragment extends Fragment {

    private CircleImageView mCiProfilePicture;
    private TextView mTxvCurrentTab;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AdminPostTabsAdapter mAdapter;
    private OnAdminDashPostFragmentListener mCallback;

    private String[] mTabTitles;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setHasOptionsMenu(true);
        mTabTitles = getResources().getStringArray(R.array.admin_post_tabs);
        mAdapter = new AdminPostTabsAdapter(getChildFragmentManager(),mTabTitles);
        setRetainInstance(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      //  setHasOptionsMenu(false);
        mAdapter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_admins_dash_posts,container,false);
        mTabLayout= (TabLayout) rootView.findViewById(R.id.adp_tabLayout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.adp_vpContainer);
        mCiProfilePicture = (CircleImageView) rootView.findViewById(R.id.adp_civProfilePicture);
        mTxvCurrentTab = (TextView) rootView.findViewById(R.id.adp_txvProfileName);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        mTxvCurrentTab.setText(mTabTitles[0]);
        mCiProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onOpenNavigatorDrawer();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnAdminDashPostFragmentListener) {
            mCallback = (OnAdminDashPostFragmentListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAdminDashPostFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface OnAdminDashPostFragmentListener {
        void onOpenNavigatorDrawer();
    }
}
