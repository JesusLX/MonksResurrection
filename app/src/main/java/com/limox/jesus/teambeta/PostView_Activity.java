package com.limox.jesus.teambeta;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.teambeta.Fragments.PostView.CommentsList_Fragment;
import com.limox.jesus.teambeta.Fragments.PostView.PostView_Fragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.Utils.AllConstants;

public class PostView_Activity extends AppCompatActivity implements HomeOfFragments, PostView_Fragment.OnPostViewFragmentListener {

    Bundle mPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        mPost = getIntent().getExtras();
        if (savedInstanceState == null){
            startPostViewFragment(mPost);
        }
    }

    void startPostViewFragment(Bundle post){

        startFragment(PostView_Fragment.newInstance(post) , true , AllConstants.PostViewTag );

    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack, String tag) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(R.id.activity_post_view, fragment,tag);
        ft.commit();
    }

    @Override
    public void startUserProfile(Bundle user) {
        Intent intent = new Intent(PostView_Activity.this, UserProfile_Activity.class);
        intent.putExtras(user);
        startActivity(intent);
    }

    @Override
    public void showPostComments() {
        startFragment(new CommentsList_Fragment(),false,AllConstants.CommentsViewTag);
    }

    @Override
    public void onBackPressed() {
        int count =getSupportFragmentManager().getBackStackEntryCount();
        if (count>1){
            super.onBackPressed();
        }else
            finish();

    }
}
