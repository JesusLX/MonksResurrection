package com.limox.jesus.teambeta;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.teambeta.Fragments.SelectProject.Projects_List_Fragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.AllConstants;

public class SelectProject_Activity extends AppCompatActivity implements HomeOfFragments,Projects_List_Fragment.OnProjectsListsFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_project);

        if (savedInstanceState == null){
            startFragment(new Projects_List_Fragment(),false, AllConstants.FragmentTag.ProjListTag);
        }
    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack, String tag) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(com.limox.jesus.teambeta.R.id.aa_container, fragment, tag);
        ft.commit();
    }

    @Override
    public void onProjectSelected(Bundle project) {

    }
}
