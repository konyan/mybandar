package com.mmpitech.mybandar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.app.AppConstant;
import com.mmpitech.mybandar.model.ChildData;
import com.mmpitech.mybandar.model.ParentData;
import com.mmpitech.mybandar.utils.MyAnimateExpandableListView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by nyanlintun on 3/19/16.
 */
public class MyExpandAdapter extends MyAnimateExpandableListView.AnimatedExpandableListAdapter {

    private Context mContext;
    private List<ParentData> mParentList;
    private HashMap<ParentData, List<ChildData>> mChild;
    private LayoutInflater inflater;
    Typeface tfThin,tfRegular;
    private int mTotalExpanse,mTotalIncome;

    public MyExpandAdapter(Context context, List<ParentData> list, HashMap<ParentData, List<ChildData>> child,int totalExpanse,int totalIncome) {

        this.mContext = context;
        this.mParentList = list;
        this.mChild = child;

        this.mTotalExpanse = totalExpanse;
        this.mTotalIncome = totalIncome;

        inflater = LayoutInflater.from(context);
        tfThin = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        tfRegular = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf");

    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;
        ChildHolder holder;
        if (v == null) {
            holder = new ChildHolder();
            v = inflater.inflate(R.layout.child_row, parent, false);
            holder.txtAmount = (TextView) v.findViewById(R.id.txtAmount);
            holder.txtAmount.setTypeface(tfRegular);
            holder.txtAmount.setTextSize(16);
            holder.txtNote = (TextView) v.findViewById(R.id.txtNote);
            holder.txtNote.setTypeface(tfRegular);
            holder.txtNote.setTextSize(14);
            holder.txtDate = (TextView) v.findViewById(R.id.txtDate);
            holder.txtDate.setTypeface(tfThin);
            holder.txtDate.setTextSize(12);
            holder.imgCircle = (ImageView)v.findViewById(R.id.imgCircle);

            v.setTag(holder);
        } else {
            holder = (ChildHolder) v.getTag();
        }

        ChildData c = mChild.get(mParentList.get(groupPosition)).get(childPosition);
        holder.txtAmount.setText(c.getAmount()+"");
        holder.txtNote.setText(c.getNote()+"");
        holder.txtDate.setText(AppConstant.formatter2.format(c.getDate())+"");

        if(mParentList.get(groupPosition).getType()==0){
            holder.imgCircle.setImageResource(R.drawable.circle_green);
        }else {
            holder.imgCircle.setImageResource(R.drawable.circle_red);
        }

        return v;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return mChild.get(mParentList.get(groupPosition)).size();
    }

    @Override
    public int getGroupCount() {
        return mParentList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChild.get(mParentList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        ParentHolder holder;

        if (v == null) {
            holder = new ParentHolder();
            v = inflater.inflate(R.layout.parent_row, parent, false);
            holder.txtTotalAmount = (TextView) v.findViewById(R.id.txtTotalAmount);
            holder.txtCategory = (TextView) v.findViewById(R.id.txtCategory);
            holder.imgCount = (ImageView) v.findViewById(R.id.imgCount);
            holder.imgCategory = (ImageView) v.findViewById(R.id.imgCategory);
            holder.imgExp = (ImageView) v.findViewById(R.id.imgExp);
            holder.numberProgressBar = (NumberProgressBar) v.findViewById(R.id.progress);

            holder.txtTotalAmount.setTypeface(tfThin);
            holder.txtTotalAmount.setTextSize(16);
            holder.txtCategory.setTypeface(tfRegular);
            holder.txtCategory.setTextSize(18);

            v.setTag(holder);
        } else {
            holder = (ParentHolder) v.getTag();
        }

        ParentData d = mParentList.get(groupPosition);

        if(isExpanded){
            holder.imgExp.setImageResource(R.drawable.arrow_up);
        }else {
            holder.imgExp.setImageResource(R.drawable.arrow_down);
        }

        if(d.getType() == 0){
            //Income
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(d.getCount() + "", Color.rgb(00, 96, 88));

//            holder.txtCategory.setText(AppConstant.STRING_INCOME[d.getCategory()]);
            holder.imgCount.setImageDrawable(drawable);
//            holder.imgCategory.setImageResource(AppConstant.INCOME[d.getCategory()]);


            holder.numberProgressBar.setReachedBarColor(Color.rgb(00, 96, 88));
            holder.numberProgressBar.setProgress((d.getAmount() * 100) / mTotalIncome);

            holder.imgCategory.setImageResource(AppConstant.INCOME[d.getCategory()]);
            holder.txtCategory.setText(AppConstant.STRING_INCOME[d.getCategory()]);

        }else if(d.getType() == 1){
            //Expanse
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(d.getCount() + "", Color.RED);

            holder.imgCount.setImageDrawable(drawable);
            holder.numberProgressBar.setReachedBarColor(Color.RED);
            holder.numberProgressBar.setProgress((d.getAmount() * 100) / mTotalExpanse);

            holder.imgCategory.setImageResource(AppConstant.EXPENSES[d.getCategory()]);
            holder.txtCategory.setText(AppConstant.STRING_EXPENSES[d.getCategory()]);
        }
        holder.txtTotalAmount.setText(d.getAmount() + " Ks ");
        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private class ParentHolder {
        TextView txtCategory, txtTotalAmount;
        ImageView imgCategory, imgCount,imgExp;
        NumberProgressBar numberProgressBar;

    }

    private class ChildHolder {
        TextView txtAmount,txtNote,txtDate;
        ImageView imgCircle;
    }
}
