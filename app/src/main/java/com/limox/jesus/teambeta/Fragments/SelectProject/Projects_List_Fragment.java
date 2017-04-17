package com.limox.jesus.teambeta.Fragments.SelectProject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limox.jesus.teambeta.R;


public class Projects_List_Fragment extends Fragment {

    private OnProjectsListsFragmentListener mListener;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_projects_list, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProjectsListsFragmentListener) {
            mListener = (OnProjectsListsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnProjectsListsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnProjectsListsFragmentListener {
      void onProjectSelected(Bundle project);
    }
}
