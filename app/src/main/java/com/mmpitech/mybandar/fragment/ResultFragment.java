package com.mmpitech.mybandar.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.adapter.DBAdapter;
import com.mmpitech.mybandar.adapter.MyExpandAdapter;
import com.mmpitech.mybandar.model.ChildData;
import com.mmpitech.mybandar.model.ParentData;
import com.mmpitech.mybandar.utils.L;
import com.mmpitech.mybandar.utils.MyAnimateExpandableListView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {


    FloatingActionButton fab;

    private MyAnimateExpandableListView expandableListView;
    private List<ParentData> mParentList;
    private HashMap<ParentData, List<ChildData>> mChild;
    private MyExpandAdapter adapter;

    int totalExpanse = 0;
    int totalIncome = 0;

    private int width,height;

    public ResultFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
         width = metrics.widthPixels;

        View v = inflater.inflate(R.layout.fragment_result, null);

        L.m("Total Income/Expanse :" + totalIncome +"/"+totalExpanse);
//        try {
//            Log.d("mylog", "Child :" + dbAdapter.getChildData(1));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        initialize(v);
        return v;
    }

    private void initialize(View v) {

        try {
            prepareListData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        totalExpanse = new DBAdapter(getActivity()).getTotalAmount(1);
        totalIncome = new DBAdapter(getActivity()).getTotalAmount(0);

        adapter = new MyExpandAdapter(getActivity(), mParentList, mChild, totalExpanse, totalIncome);

        expandableListView = (MyAnimateExpandableListView) v.findViewById(R.id.lvExp);

        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (expandableListView.isGroupExpanded(groupPosition)) {
                    expandableListView.collapseGroupWithAnimation(groupPosition);
                } else {
                    expandableListView.expandGroupWithAnimation(groupPosition);
                }

                return true;
            }
        });
    }

    private void prepareListData() throws ParseException {
        mParentList = new ArrayList<ParentData>();
        mParentList = new DBAdapter(getActivity()).getParentData();
        mChild = new HashMap<ParentData, List<ChildData>>();

        for(int i=0; i< mParentList.size() ; i++){
            ParentData p = mParentList.get(i);
            mChild.put(p,new DBAdapter(getActivity()).getChildData(p.getCategory()));
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        totalExpanse = new DBAdapter(getActivity()).getTotalAmount(1);
        totalIncome = new DBAdapter(getActivity()).getTotalAmount(0);

        try {
            prepareListData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapter = new MyExpandAdapter(getActivity(), mParentList, mChild, totalExpanse, totalIncome);
        expandableListView.setAdapter(adapter);
        Log.d("mylog", "I am resume.");
    }



}
