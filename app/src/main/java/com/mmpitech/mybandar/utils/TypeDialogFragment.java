package com.mmpitech.mybandar.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.activity.DataEntry;
import com.mmpitech.mybandar.app.AppConstant;

/**
 * Created by nyanlintun on 3/18/16.
 */
public class TypeDialogFragment extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomTheme_Dialog)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("ChooseType")
                .setMessage("Pleas Choose Your Type.")
                .setPositiveButton("Expense", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getActivity(), DataEntry.class);
                        i.putExtra(AppConstant.EXTRA_TYPE,1);
                        getActivity().startActivity(i);

                    }
                })
                .setNegativeButton("Income", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getActivity(), DataEntry.class);
                        i.putExtra(AppConstant.EXTRA_TYPE,0);
                        getActivity().startActivity(i);

                    }
                });

        return builder.create();
    }
}
