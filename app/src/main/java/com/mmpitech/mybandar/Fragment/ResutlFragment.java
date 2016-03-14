package com.mmpitech.mybandar.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.activity.DataEntry;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResutlFragment extends Fragment {


    FloatingActionButton fab;

    public ResutlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_resutl,null);

        fab  = (FloatingActionButton)v.findViewById(R.id.fabClick);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DataEntry.class);
                getActivity().startActivity(i);
            }
        });
        return v;
    }

}
