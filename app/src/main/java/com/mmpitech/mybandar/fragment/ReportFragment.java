package com.mmpitech.mybandar.fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.androidadvance.topsnackbar.TSnackbar;
import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.adapter.DBAdapter;
import com.mmpitech.mybandar.adapter.MyFlipperAdapter;
import com.mmpitech.mybandar.app.AppConstant;
import com.mmpitech.mybandar.model.ParentData;
import com.mmpitech.mybandar.utils.L;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment implements View.OnClickListener {


    private PieChartView chart;
    private PieChartData data;

    private TextView txtNet;
    private AdapterViewFlipper flipper;
    private ImageView imgPre, imgNext;

    private boolean hasLabels = false;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = true;
    private boolean hasLabelForSelected = false;


    private int totalExpanseByMonth, totalIncomeByMonth;

    public ReportFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);
        initialize(rootView);
        return rootView;
    }

    private void initialize(View v) {
        chart = (PieChartView) v.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());

        txtNet = (TextView) v.findViewById(R.id.txtNet);

        flipper = (AdapterViewFlipper) v.findViewById(R.id.flipper);


        imgNext = (ImageView) v.findViewById(R.id.imgNext);
        imgNext.setOnClickListener(this);
        imgPre = (ImageView) v.findViewById(R.id.imgPre);
        imgPre.setOnClickListener(this);

    }


    private void reset() {
        chart.setCircleFillRatio(1.0f);
        hasLabels = false;
        hasLabelsOutside = false;
        hasCenterCircle = false;
        hasLabelForSelected = false;
    }

    private void generateData(String month) throws ParseException {
//        int numValues = 6;

        List<SliceValue> values = new ArrayList<SliceValue>();
        List<ParentData> parentDatas = generateDataFromDb(month);
        for (ParentData p : parentDatas) {

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
// generate random color
            int color = generator.getRandomColor();
            SliceValue sliceValue = new SliceValue((float) (p.getAmount() * 100) / totalExpanseByMonth(1), color);
            values.add(sliceValue);

        }

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");

        //Text1
        data.setCenterText1(totalIncomeByMonth + "Ks");
        data.setCenterText1Color(getActivity().getResources().getColor(R.color.colorPrimary));
        data.setCenterText1Typeface(tf);
        data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));

        //Text2
        data.setCenterText2(totalExpanseByMonth + "Ks");
        data.setCenterText2Typeface(tf);
        data.setCenterText2Color(Color.RED);
        data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));


        chart.setPieChartData(data);
    }

    private void toggleLabelsOutside() throws ParseException {
        // has labels have to be true:P
        hasLabelsOutside = !hasLabelsOutside;
        if (hasLabelsOutside) {
            hasLabels = true;
            hasLabelForSelected = false;
            chart.setValueSelectionEnabled(hasLabelForSelected);
        }
        chart.setCircleFillRatio(0.7f);

        if (monthArray(removeDuplicateItem()).length != 0) {
            generateData(AppConstant.MONTHNAMES[(monthArray(removeDuplicateItem())[0] - 1)]);
            imgNext.setVisibility(View.VISIBLE);
            imgPre.setVisibility(View.VISIBLE);
        } else {
            imgNext.setVisibility(View.GONE);
            imgPre.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.imgNext) {
            totalExpanseByMonth = 0;
            totalIncomeByMonth = 0;
            try {
                int[] month = monthArray(removeDuplicateItem());
                if (flipper.getDisplayedChild() < month.length - 1) {
                    String m = AppConstant.MONTHNAMES[month[flipper.getDisplayedChild() + 1] - 1];
                    generateData(m);
                } else {
                    String m = AppConstant.MONTHNAMES[(monthArray(removeDuplicateItem())[0] - 1)];
                    generateData(m);

                }
                txtNet.setText("Net = " + (totalIncomeByMonth - totalExpanseByMonth) + " Ks");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            flipper.showNext();
            flipper.stopFlipping();
        }
        if (v.getId() == R.id.imgPre) {
            totalExpanseByMonth = 0;
            totalIncomeByMonth = 0;
            try {
                int[] month = monthArray(removeDuplicateItem());
                Log.d("mylog", "Child :" + flipper.getDisplayedChild());
                if (flipper.getDisplayedChild() == 0) {
                    Log.d("mylog", "I am " + month[flipper.getCount() - 1]);
                    String m = AppConstant.MONTHNAMES[month[flipper.getCount() - 1] - 1];
                    Log.d("mylog", "Month :" + m);
                    generateData(m);
                } else {
                    Log.d("mylog", "I am " + month[flipper.getDisplayedChild() - 1]);
                    String m = AppConstant.MONTHNAMES[month[flipper.getDisplayedChild() - 1] - 1];
                    Log.d("mylog", "Month :" + m);
                    generateData(m);
                }
                txtNet.setText("Net = " + (totalIncomeByMonth - totalExpanseByMonth) + " Ks");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            flipper.showPrevious();
            flipper.stopFlipping();
        }

    }


    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
//            Toast.makeText(getActivity(), AppConstant.STRING_EXPENSES[arcIndex] + value.getValue(), Toast.LENGTH_SHORT).show();

            int pos = 0;
            int amount = 0;
            try {
                pos = generateDataFromDb("March").get(arcIndex).getCategory();
                amount = generateDataFromDb("March").get(arcIndex).getAmount();
            } catch (ParseException e) {
                e.printStackTrace();
            }


            TSnackbar snackbar = TSnackbar.make(getView(),
                    AppConstant.STRING_EXPENSES[pos] + " = " + amount, TSnackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(Color.BLACK);


            snackbar.addIcon(AppConstant.EXPENSES[pos], 196);
            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20);

            snackbar.show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

    private ArrayList<ParentData> generateDataFromDb(String realMonth) throws ParseException {

        List<ParentData> dbParents = new DBAdapter(getActivity()).getReportData();
        ArrayList<ParentData> parentDatas = new ArrayList<ParentData>();

        for (ParentData p : dbParents) {
            String date = AppConstant.formatToMonthFullName.format(p.getDate());
            if (realMonth.equals(date)) {
                parentDatas.add(p);

                totalExpanseByMonth(p.getAmount());
                Log.d("mylog", "Date :" + totalExpanseByMonth);
            }
        }

        totalAountForIncome(realMonth);
        return parentDatas;
    }

    private int totalExpanseByMonth(int amount) {

        totalExpanseByMonth = totalExpanseByMonth + amount;
        return totalExpanseByMonth;

    }

    private int totalAountForIncome(String incomeMonth) {

        try {
            List<ParentData> parentDataList = new DBAdapter(getActivity()).getTotalAmountForIncome();
            for (ParentData p : parentDataList) {
                String m = AppConstant.formatToMonthFullName.format(p.getDate());
                if (m.equals(incomeMonth)) {
                    totalIncomeByMonth = totalIncomeByMonth + p.getAmount();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return totalIncomeByMonth;

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("mylog", "Rsu");
        totalExpanseByMonth = 0;
        totalIncomeByMonth = 0;
        try {
            flipper.setAdapter(new MyFlipperAdapter(getActivity(), monthArray(removeDuplicateItem())));
            toggleLabelsOutside();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtNet.setText("Net = " + (totalIncomeByMonth - totalExpanseByMonth) + " Ks");


    }



    private List<String> dateToString() throws ParseException {
        List<Date> dates = new DBAdapter(getActivity()).getGroupDate();
        List<String> strings = new ArrayList<String>();


        for (Date s : dates) {
            String month = AppConstant.formatToMonth.format(s);
            strings.add(month);

        }
        return strings;

    }

    private List<String> removeDuplicateItem() throws ParseException {


        List<String> strings = dateToString();
// add elements to al, including duplicates
        Set<String> hs = new HashSet<>();
        hs.addAll(strings);
        strings.clear();
        strings.addAll(hs);


        return strings;

    }

    private int[] monthArray(List<String> strings) {
        int[] monthArray = new int[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            monthArray[i] = Integer.parseInt(s);

        }
        Arrays.sort(monthArray);
        return monthArray;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("mylog", "OnAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("mylog", "onDetach");
    }
}

