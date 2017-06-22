package com.limox.jesus.teambeta.Fragments.Settings;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.ExternalUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class About_Fragment extends Fragment {

    ImageView ivIsologo;

    public About_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        ivIsologo = (ImageView) rootView.findViewById(R.id.isologo);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivIsologo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExternalUtils.openBrowser(getContext(), AllConstants.WEB);
            }
        });
    }
}
