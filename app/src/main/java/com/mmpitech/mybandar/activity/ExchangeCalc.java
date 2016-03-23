package com.mmpitech.mybandar.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.app.AppConstant;
import com.mmpitech.mybandar.model.Currency;
import com.mmpitech.mybandar.utils.L;
import com.mmpitech.mybandar.utils.SessionManager;
import com.rey.material.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class ExchangeCalc extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView txtShow, txtAmount;
    private Spinner spinner;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnX;

    String country[];
    private int selector = 0;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_calc);

        sessionManager = new SessionManager(this);
        init();

    }

    private void init() {

        country = getResources().getStringArray(R.array.country);

        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Exchange Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);

        txtShow = (TextView) this.findViewById(R.id.txtShow);
        txtAmount = (TextView) this.findViewById(R.id.txtEnter);

        spinner = (Spinner) this.findViewById(R.id.spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, country);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                selector = position;
                L.t(ExchangeCalc.this, "Selected :" + country[position]);
            }


        });

        btnGroup();

    }

    private void btnGroup() {


        btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(this);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8 = (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        btn9 = (Button) findViewById(R.id.btn9);
        btn9.setOnClickListener(this);
        btnX = (Button) findViewById(R.id.btnX);
        btnX.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int position = v.getId();

        String btnText = txtAmount.getText().toString();
        if (btnText.equals("0")) {
            btnText = "";
        }

        switch (position) {

            case R.id.btn0:

                btnText = btnText + 0;

                txtAmount.setText(btnText);

                showOnTextView(btnText, selector);
                break;

            case R.id.btn1:

                btnText = btnText + 1;
                txtAmount.setText(btnText);
                showOnTextView(btnText, selector);
                break;
            case R.id.btn2:

                btnText = btnText + 2;
                txtAmount.setText(btnText);
                showOnTextView(btnText, selector);

                break;
            case R.id.btn3:


                btnText = btnText + 3;
                txtAmount.setText(btnText);
                showOnTextView(btnText, selector);

                break;
            case R.id.btn4:

                btnText = btnText + 4;
                txtAmount.setText(btnText);
                showOnTextView(btnText, selector);

                break;
            case R.id.btn5:

                btnText = btnText + 5;
                txtAmount.setText(btnText);
                showOnTextView(btnText, selector);
                break;
            case R.id.btn6:

                btnText = btnText + 6;
                txtAmount.setText(btnText);
                showOnTextView(btnText, selector);
                break;
            case R.id.btn7:

                btnText = btnText + 7;
                txtAmount.setText(btnText);
                showOnTextView(btnText, selector);
                break;
            case R.id.btn8:

                btnText = btnText + 8;
                txtAmount.setText(btnText);
                showOnTextView(btnText, selector);
                break;
            case R.id.btn9:

                btnText = btnText + 9;
                txtAmount.setText(btnText);
                showOnTextView(btnText, selector);
                break;
            case R.id.btnX:

                if (btnText != null && btnText.length() > 0) {

                    btnText = btnText.substring(0, btnText.length() - 1);
                    txtAmount.setText(btnText);

                    showOnTextView(btnText, selector);
                }

                if (btnText.equals("") || btnText == null) {
                    txtAmount.setText("0");
                }

                break;

            default:

                break;


        }
    }

    private void showOnTextView(String val, int selector) {

        float values = calculation(val, selector);
        txtShow.setText(NumberFormat.getNumberInstance(Locale.US).format(values) + "Ks");
    }

    private float calculation(String enterText, int pos) {

        int result = 0;
        int enter;
        if (enterText.equals("")) {
            enter = 0;
        } else {

            enter = Integer.parseInt(enterText);
        }
        if (getValueFormJson(pos) == 0) {
            return result;
        }
        return (float) enter * getValueFormJson(pos);

    }

    private float getValueFormJson(int pos) {
        String[] codename = getResources().getStringArray(R.array.codename);
        String mJson = sessionManager.getJson();
        float value = 0;

        if (mJson == null) {

            L.t(ExchangeCalc.this, "Please load Currency.");
            return value;
        }
        try {
            JSONObject jsonObj = new JSONObject(mJson);
            JSONObject jsonRate = jsonObj.getJSONObject(AppConstant.RATE);

            String price = jsonRate.getString(codename[pos]);
            String f1 = price.replace(",", "");
            L.m("Price :" + price + "F1 :" + f1);
            value = Float.parseFloat(f1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return value;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int pos = item.getItemId();
        switch (pos) {
            case android.R.id.home:

                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
