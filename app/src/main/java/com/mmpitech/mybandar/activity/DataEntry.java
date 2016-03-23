package com.mmpitech.mybandar.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.adapter.DBAdapter;
import com.mmpitech.mybandar.app.AppConstant;
import com.mmpitech.mybandar.model.Data;
import com.mmpitech.mybandar.utils.L;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class DataEntry extends AppCompatActivity implements View.OnClickListener {

    private HorizontalScrollView hSV;
    private RelativeLayout rlCategory;
    private EditText edtNote;
    private TextView txtAmount, txtDate;
    private Toolbar toolbar;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnX;

    private DBAdapter dbAdapter;
    private int expensesCount, incomeCount;

    private Calendar c;
    private int type;

    private HashMap<String, Integer> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        hashMap = new HashMap<String, Integer>();
        hashMap.put(AppConstant.ID, 0);

        type = getIntent().getExtras().getInt(AppConstant.EXTRA_TYPE);

        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_left);
        expensesCount = AppConstant.EXPENSES.length;
        incomeCount = AppConstant.INCOME.length;

        init();
    }

    private void init() {
        c = Calendar.getInstance();
        dbAdapter = new DBAdapter(this);


        hSV = (HorizontalScrollView) findViewById(R.id.hSv);
        rlCategory = (RelativeLayout) findViewById(R.id.rlCategory);
        rlCategory.setGravity(Gravity.CENTER);

        createCategory(type);
        txtAmount = (TextView) this.findViewById(R.id.txtAmount);

        txtDate = (TextView) this.findViewById(R.id.txtDate);
        txtDate.setText(AppConstant.formatter.format(c.getTime()));
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDate();
            }
        });

        edtNote = (EditText) this.findViewById(R.id.edtNote);

        btnGroup();

    }

    private void createCategory(final int type) {

        int marginLeft = 8;
        int length;

        if (type == 0) {
            length = AppConstant.INCOME.length;
        } else {
            length = AppConstant.EXPENSES.length;
        }

        for (int i = 0; i < length; i++) {

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            params.leftMargin = marginLeft;
            params.rightMargin = 8;
            marginLeft = marginLeft + 100;

            L.m("i am loop");
            final CircleImageView img = new CircleImageView(this);

            if (hashMap.get(AppConstant.ID) == i) {
                img.setBorderWidth(3);
                img.setBorderColor(getResources().getColor(R.color.colorAccent));
                L.m("Image here");
            }

            if (type == 0) {
                img.setImageResource(AppConstant.INCOME[i]);
            } else {
                img.setImageResource(AppConstant.EXPENSES[i]);
            }
            img.setLayoutParams(params);
            img.setId(i);

            rlCategory.addView(img);
            final int id_ = img.getId();
            CircleImageView imgCategory = (CircleImageView) findViewById(id_);
            imgCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String categoryName = null;
                    if (type == 0) {
                        categoryName = AppConstant.STRING_INCOME[id_];
                    } else {
                        categoryName = AppConstant.STRING_EXPENSES[id_];
                    }


                    TSnackbar snackbar = TSnackbar.make(findViewById(android.R.id.content), "Category =" + categoryName, TSnackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.BLACK);


                    if (type == 0) {

                        snackbar.addIcon(AppConstant.INCOME[id_], 196);
                    } else {
                        snackbar.addIcon(AppConstant.EXPENSES[id_], 196);
                    }
                    View snackbarView = snackbar.getView();

                    TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(20);

                    CircleImageView c = (CircleImageView) v;
                    c.setBorderWidth(3);
                    c.setBorderColor(getResources().getColor(R.color.colorAccent));

                    if (hashMap.get(AppConstant.ID) != id_) {
                        hashMap.put(AppConstant.ID, id_);

                        //Remove all view and Creater
                        rlCategory.removeAllViewsInLayout();
                        createCategory(type);

                    }
                    snackbar.show();
                    if (snackbar.isShown()) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        snackbar.dismiss();
                    }

                    L.m("Key :" + hashMap.get(AppConstant.ID));
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_entry, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem settingsMenuItem = menu.findItem(R.id.action_save);
        SpannableString s = new SpannableString(settingsMenuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColorPrimary)), 0, s.length(), 0);
        settingsMenuItem.setTitle(s);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        String note = edtNote.getText().toString();
        String amount = txtAmount.getText().toString();
        String date = txtDate.getText().toString();
        int category = hashMap.get(AppConstant.ID);
        L.m("Category :" + category);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            saveToDb(type, category, amount, date, note);
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addDate() {


        final int mYear, mMonth, mDay;

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txtDate.setText(AppConstant.MONTHNAMES[monthOfYear] + " " + dayOfMonth + "," + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void saveToDb(int type, int category, String amount, String date, String note) {

        Data data = new Data();
        if (amount == null || amount.equals("0")) {
            L.t(DataEntry.this, "Please Enter Amount.");
        } else {
            data.setType(type);
            data.setCategory(category);
            data.setAmount(Integer.parseInt(amount));
            try {
                data.setDate(AppConstant.formatter.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            data.setNote(note);
            dbAdapter.addData(data);

            txtAmount.setText("");
            edtNote.setText("");

            finish();
        }

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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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

                break;

            case R.id.btn1:

                btnText = btnText + 1;
                txtAmount.setText(btnText);
                break;
            case R.id.btn2:

                btnText = btnText + 2;
                txtAmount.setText(btnText);

                break;
            case R.id.btn3:


                btnText = btnText + 3;
                txtAmount.setText(btnText);

                break;
            case R.id.btn4:

                btnText = btnText + 4;
                txtAmount.setText(btnText);

                break;
            case R.id.btn5:

                btnText = btnText + 5;
                txtAmount.setText(btnText);
                break;
            case R.id.btn6:

                btnText = btnText + 6;
                txtAmount.setText(btnText);
                break;
            case R.id.btn7:

                btnText = btnText + 7;
                txtAmount.setText(btnText);
                break;
            case R.id.btn8:

                btnText = btnText + 8;
                txtAmount.setText(btnText);
                break;
            case R.id.btn9:

                btnText = btnText + 9;
                txtAmount.setText(btnText);
                break;
            case R.id.btnX:

                if (btnText != null && btnText.length() > 0) {

                    btnText = btnText.substring(0, btnText.length() - 1);
                    txtAmount.setText(btnText);
                }

                if (btnText.equals("") || btnText == null) {
                    txtAmount.setText("0");
                }

                break;

            default:

                break;


        }


    }


}
