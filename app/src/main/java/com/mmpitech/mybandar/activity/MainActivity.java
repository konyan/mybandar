package com.mmpitech.mybandar.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.mmpitech.mybandar.fragment.CurrencyFragment;
import com.mmpitech.mybandar.fragment.ReportFragment;
import com.mmpitech.mybandar.fragment.ResultFragment;
import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.adapter.ViewPagerAdapter;
import com.mmpitech.mybandar.app.AppConstant;
import com.mmpitech.mybandar.utils.MyFab;

public class MainActivity extends AppCompatActivity implements ViewGroup.OnClickListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private MaterialSheetFab materialSheetFab;

    private TextView txtExcCalc, txtExpanse, txtIncome;
    private RelativeLayout rlExchange, rlExpanse, rlIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {

        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        viewPager = (ViewPager) this.findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) this.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setUpIcon(tabLayout);
//
        txtExcCalc = (TextView) this.findViewById(R.id.txtExCalc);
        txtExcCalc.setOnClickListener(this);
        txtExpanse = (TextView) this.findViewById(R.id.txtExpanse);
        txtExpanse.setOnClickListener(this);
        txtIncome = (TextView) this.findViewById(R.id.txtIncome);
        txtIncome.setOnClickListener(this);

        rlExchange = (RelativeLayout) this.findViewById(R.id.rlExchange);
        rlExchange.setOnClickListener(this);
        rlExpanse = (RelativeLayout) this.findViewById(R.id.rlExpanse);
        rlExpanse.setOnClickListener(this);
        rlIncome = (RelativeLayout) this.findViewById(R.id.rlIncome);
        rlIncome.setOnClickListener(this);

        MyFab fab = (MyFab) findViewById(R.id.fab);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.colorAccent);
        int fabColor = getResources().getColor(R.color.colorAccent);

        // Initialize material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay,
                sheetColor, fabColor);
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                // Called when the material sheet's "show" animation starts.
            }

            @Override
            public void onSheetShown() {
                // Called when the material sheet's "show" animation ends.
            }

            @Override
            public void onHideSheet() {
                // Called when the material sheet's "hide" animation starts.
            }

            public void onSheetHidden() {
                // Called when the material sheet's "hide" animation ends.
            }
        });

    }

    private void setUpIcon(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(R.drawable.chart);
        tabLayout.getTabAt(1).setIcon(R.drawable.result);
        tabLayout.getTabAt(2).setIcon(R.drawable.currency);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new ReportFragment());
        adapter.addFragment(new ResultFragment());
        adapter.addFragment(new CurrencyFragment());

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.rlExchange:
                Intent i = new Intent(this, ExchangeCalc.class);
                startActivity(i);
                materialSheetFab.hideSheet();

                break;
            case R.id.rlExpanse:
                Intent intentExpanse = new Intent(this, DataEntry.class);
                intentExpanse.putExtra(AppConstant.EXTRA_TYPE, 1);
                startActivity(intentExpanse);
                materialSheetFab.hideSheet();

                break;
            case R.id.rlIncome:
                Intent intentIncome = new Intent(this, DataEntry.class);
                intentIncome.putExtra(AppConstant.EXTRA_TYPE, 0);
                startActivity(intentIncome);
                materialSheetFab.hideSheet();

                break;

            case R.id.txtExCalc:
                Intent iText = new Intent(this, ExchangeCalc.class);
                startActivity(iText);
                materialSheetFab.hideSheet();

                break;
            case R.id.txtExpanse:
                Intent intentTextExpanse = new Intent(this, DataEntry.class);
                intentTextExpanse.putExtra(AppConstant.EXTRA_TYPE, 1);
                startActivity(intentTextExpanse);
                materialSheetFab.hideSheet();

                break;
            case R.id.txtIncome:
                Intent intentTextIncome = new Intent(this, DataEntry.class);
                intentTextIncome.putExtra(AppConstant.EXTRA_TYPE, 0);
                startActivity(intentTextIncome);
                materialSheetFab.hideSheet();

                break;


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
