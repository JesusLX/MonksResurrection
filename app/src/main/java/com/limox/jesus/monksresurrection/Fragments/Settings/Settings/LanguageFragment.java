package com.limox.jesus.monksresurrection.Fragments.Settings.Settings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.limox.jesus.monksresurrection.Adapters.NavListViewAdapter;
import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Utils.NavItem;

import java.util.ArrayList;
import java.util.Locale;


public class LanguageFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private ListView lvLanguages;

    public LanguageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_language, container, false);
        lvLanguages = (ListView) rootView.findViewById(R.id.l_lvLanguages);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvLanguages.setAdapter(new NavListViewAdapter(getContext(),createArrayList(Locale.getAvailableLocales())));

    }

    ArrayList<NavItem> createArrayList(Locale[] languages){
        ArrayList<NavItem> tmpItems = new ArrayList<>();
        for (Locale language : languages) {
            if (!language.getDisplayCountry().isEmpty())
                tmpItems.add(new NavItem(language.getDisplayCountry()));
        }
        return tmpItems;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnAdminDashPostFragmentListener) {
            mListener = (OnAdminDashPostFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAdminDashPostFragmentListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
