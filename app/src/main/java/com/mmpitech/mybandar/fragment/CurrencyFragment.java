package com.mmpitech.mybandar.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.adapter.CurrencyAdapter;
import com.mmpitech.mybandar.app.AppConstant;
import com.mmpitech.mybandar.model.Currency;
import com.mmpitech.mybandar.utils.L;
import com.mmpitech.mybandar.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.tajchert.sample.DotsTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrencyFragment extends Fragment {

    private ListView listView;
    private CurrencyAdapter adapter;
    private ArrayList<Currency> currencies;
    private TextView txtDate;
    private DotsTextView dots;

    private SessionManager sessionManager;
    private SimpleDateFormat simpleDateFormatDay;
    private Calendar calender;

    public CurrencyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calender = Calendar.getInstance();

        sessionManager = new SessionManager(getActivity());
        L.m("DATE :" + sessionManager.getDate());
        simpleDateFormatDay = new SimpleDateFormat("dd");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_currency, container, false);
        initializeScreen(root);
        return root;
    }


    private void initializeScreen(View v) {
        listView = (ListView) v.findViewById(R.id.listCurrency);
        currencies = new ArrayList<Currency>();
        adapter = new CurrencyAdapter(getActivity(), currencies);
        txtDate = (TextView) v.findViewById(R.id.txtDate);

        dots = (DotsTextView) v.findViewById(R.id.dots);

        if (sessionManager.getDate() == null) {
            new CurrencyAsyn(getActivity(), currencies,sessionManager.getJson()).execute();
        } else if (sessionManager.getDate().equals(simpleDateFormatDay.format(calender.getTime()))) {
            L.m("Json :" + sessionManager.getJson());
            new CurrencyAsyn(getActivity(), currencies,sessionManager.getJson()).execute();
        } else {
            new CurrencyAsyn(getActivity(), currencies,null).execute();
        }
    }


    private class CurrencyAsyn extends AsyncTask<Void, Void, ArrayList<Currency>> {

        private Context c;
        private ArrayList<Currency> currencies;
        int[] flags;
        String[] codename, country;
        SimpleDateFormat simpleDateFormat;
        private String mJson = null;

        public CurrencyAsyn(Context context, ArrayList<Currency> currencies, String localJson) {
            this.currencies = currencies;
            this.c = context;
            simpleDateFormat = new SimpleDateFormat("MMMMM d,yyyy");

            this.mJson = localJson;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dots.setVisibility(View.VISIBLE);
            dots.showAndPlay();

            flags = new int[]{R.mipmap.currency_usa, R.mipmap.currency_euro, R.mipmap.currency_singapore,
                    R.mipmap.currency_japan, R.mipmap.currency_malaysia, R.mipmap.currency_korea,
                    R.mipmap.currency_china, R.mipmap.currency_thai, R.mipmap.currency_india, R.mipmap.currency_vietnam};
            codename = c.getResources().getStringArray(R.array.codename);
            country = c.getResources().getStringArray(R.array.country);

        }

        @Override
        protected ArrayList<Currency> doInBackground(Void... params) {

            if(mJson ==  null){
                try {
                    mJson = onlineJsonParsing();

                    sessionManager.setJson(mJson);
                    L.m("i am online");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(mJson);
                JSONObject jsonRate = jsonObj.getJSONObject(AppConstant.RATE);

                for (int i = 0; i < flags.length; i++) {

                    String price = jsonRate.getString(codename[i]);
                    //  int p = Integer.parseInt(price);
                    currencies.add(new Currency(flags[i], country[i], price));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return currencies;
        }

        @Override
        protected void onPostExecute(ArrayList<Currency> currencies) {

            super.onPostExecute(currencies);
            dots.hideAndStop();
            dots.setVisibility(View.GONE);
            sessionManager.setDate(simpleDateFormatDay.format(calender.getTime()));
            txtDate.setText(AppConstant.formatter.format(calender.getTime()) + "");

            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }
    }

    private String onlineJsonParsing() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(AppConstant.CURRENCY_URL)
                .build();

        Response response = null;

        response = client.newCall(request).execute();
        String json = response.body().string();

        return json;
    }

}
