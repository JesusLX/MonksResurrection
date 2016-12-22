package com.limox.jesus.monksresurrection.Fragments.DashPost;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limox.jesus.monksresurrection.Adapters.PostPagerAdapter;
import com.limox.jesus.monksresurrection.R;

public class HomeDashPosts_Fragment extends Fragment  {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PostPagerAdapter mAdapter;
    private OnHomeDashPostFragmentListener mCallback;

    public interface OnHomeDashPostFragmentListener {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAdapter = new PostPagerAdapter(getActivity().getSupportFragmentManager(),getResources().getStringArray(R.array.dash_post_tabs));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_home_dash_posts,null);
        mToolbar = (Toolbar) rootView.findViewById(R.id.hdp_tbTitle);
        mTabLayout= (TabLayout) rootView.findViewById(R.id.hdp_tabLayout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.hdp_vpContainer);
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
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     *//*
    private SectionsPagerAdapter mSectionsPagerAdapter;

    *//**
     * The {@link ViewPager} that will host the section contents.
     *//*
    private ViewPager mViewPager;

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_dash_posts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.dp_tbTitle);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // With the floating buttons open a activity for create a post
                startActivity(new Intent(HomeDashPosts_Fragment.this,CreatePost_Activity.class));
                //TODO Ahora cierra la lista para poder actualizar los posts
                // TODO Hay que cambiarlo para que actualice
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dash_posts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startUserProfile(Bundle user) {
        UserProfile_Fragment up = UserProfile_Fragment.newInstance(user);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.activity_dash_post,up).commit();
    }

    @Override
    public void startPostView(Bundle post) {
 UserProfile_Fragment up = UserProfile_Fragment.newInstance(post);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.activity_dash_post,up).commit();
    }

    @Override
    public void onOpenPostView(Post post) {

    }


    *//**
     * A placeholder fragment containing a simple view.
     *//*
    public static class PlaceholderFragment extends Fragment {
        *//**
         * The fragment argument representing the section number for this
         * fragment.
         *//*
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        *//**
         * Returns a new instance of this fragment for the given section
         * number.
         *//*
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;
            rootView = inflater.inflate(R.layout.fragment_posts_list, container, false);
            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.fpp_rvPosts);
            PostAdapterRecycler par = null;
            //depending of the page show a list of other
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

                par = new PostAdapterRecycler(this.getContext(), AllConstants.FOR_PUBLISHED);

            }
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {

                par = new PostAdapterRecycler(this.getContext(), AllConstants.FOR_FIXES);

            }
            rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
            rv.setAdapter(par);



            return rootView;
        }
    }

    *//**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     *//*
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Bugs";
                case 1:
                    return "Fixes";
            }
            return null;
        }
    }*/
}
