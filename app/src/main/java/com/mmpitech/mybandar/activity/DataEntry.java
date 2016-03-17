package com.mmpitech.mybandar.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.adapter.DBAdapter;
import com.mmpitech.mybandar.app.AppConstant;
import com.mmpitech.mybandar.model.Data;

import java.text.ParseException;
import java.util.Formatter;

public class DataEntry extends AppCompatActivity {

    private HorizontalScrollView hSV;
    private RelativeLayout rlCategory;
    private EditText edtNote;
    private TextView txtAmount, txtDate;


    private DBAdapter dbAdapter;
    //ImageView[] imgCategory;
    private int expensesCount, incomeCount;
    private int marginLeft = 8;
    private ImageView imgCategory;

    private Data data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);


        expensesCount = AppConstant.EXPENSES.length;
        incomeCount = AppConstant.INCOME.length;

        init();
    }

    private void init() {

        dbAdapter = new DBAdapter(this);

        hSV = (HorizontalScrollView) findViewById(R.id.hSv);
        rlCategory = (RelativeLayout) findViewById(R.id.rlCategory);
        createCategory();
        txtAmount = (TextView) this.findViewById(R.id.txtAmount);
        txtDate = (TextView) this.findViewById(R.id.txtDate);
        edtNote = (EditText) this.findViewById(R.id.edtNote);

    }

    private void createCategory() {
        for (int i = 0; i < AppConstant.EXPENSES.length; i++) {

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            params.leftMargin = marginLeft;
            params.rightMargin = 8;
            marginLeft = marginLeft + 100;

            ImageView img = new ImageView(this);
            img.setImageResource(AppConstant.EXPENSES[i]);
            img.setLayoutParams(params);
            img.setId(i);
            rlCategory.addView(img);

            final int id_ = img.getId();
            imgCategory = (ImageView) findViewById(id_);
            imgCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DataEntry.this, "Category :" + id_, Toast.LENGTH_LONG).show();
                    data.setCategory(id_);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String note = edtNote.getText().toString();
        String amount = txtAmount.getText().toString();
        String date = txtDate.getText().toString();

        data.setNote(note);
        data.setAmount(Integer.parseInt(amount));
        try {
            data.setDate(AppConstant.formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            dbAdapter.addData(true, data);

            txtAmount.setText("");
            edtNote.setText("");

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
