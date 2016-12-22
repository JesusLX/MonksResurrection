package com.limox.jesus.monksresurrection;




import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Adapters.PostAdapterRecycler;
import com.limox.jesus.monksresurrection.Fragments.AboutMe.AboutMe_Fragment;
import com.limox.jesus.monksresurrection.Fragments.DashPost.HomeDashPosts_Fragment;
import com.limox.jesus.monksresurrection.Fragments.UserProfile.UserProfile_Fragment;
import com.limox.jesus.monksresurrection.Model.Post;

public class Home_Activity extends AppCompatActivity implements PostAdapterRecycler.OnPostViewHolderListener,UserProfile_Fragment.OnUserProfileFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        startDashPostFragment();

    }
    void startDashPostFragment(){
        HomeDashPosts_Fragment dpf = new HomeDashPosts_Fragment();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.ah_container,dpf);
        ft.commit();
    }

    private void startIndexFragment(){
        Index_Fragment inf = new Index_Fragment();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.ah_container,inf);
        ft.commit();
    }
    private void openAboutMe(){
        AboutMe_Fragment fr = AboutMe_Fragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.ah_container,fr);
        ft.commit();
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

    }

    @Override
    public void onOpenPostView(Post post) {

    }
}
