package com.mmpitech.mybandar.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mmpitech.mybandar.Fragment.CurrencyFragment;
import com.mmpitech.mybandar.Fragment.ReportFragment;
import com.mmpitech.mybandar.Fragment.ResutlFragment;
import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

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
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) this.findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) this.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setUpIcon(tabLayout);
    }

    private void setUpIcon(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(R.drawable.chart);
        tabLayout.getTabAt(1).setIcon(R.drawable.result);
        tabLayout.getTabAt(2).setIcon(R.drawable.currency);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new ReportFragment());
        adapter.addFragment(new ResutlFragment());
        adapter.addFragment(new CurrencyFragment());

        viewPager.setAdapter(adapter);
    }
}
