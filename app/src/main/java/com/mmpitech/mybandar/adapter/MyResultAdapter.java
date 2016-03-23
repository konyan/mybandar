package com.mmpitech.mybandar.adapter;

/**
 * Created by zirate on 3/17/16.
 */

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.mmpitech.mybandar.R;
import com.mmpitech.mybandar.app.AppConstant;
import com.mmpitech.mybandar.model.ChildData;
import com.mmpitech.mybandar.model.ParentData;

import java.util.HashMap;
import java.util.List;


public class MyResultAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<ParentData> mParentList;
    private HashMap<ParentData, List<ChildData>> mChild;
    private LayoutInflater inflater;

    private int mTotalExpanse,mTotalIncome;

    public MyResultAdapter(Context context, List<ParentData> list, HashMap<ParentData, List<ChildData>> child,int totalExpanse,int totalIncome) {

        this.mContext = context;
        this.mParentList = list;
        this.mChild = child;

        this.mTotalExpanse = totalExpanse;
        this.mTotalIncome = totalIncome;

        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getGroupCount() {
        return mParentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChild.get(mParentList.get(groupPosition)).size();
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
        return false;
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
            holder.numberProgressBar = (NumberProgressBar) v.findViewById(R.id.progress);
            v.setTag(holder);
            Log.d("mylog","Holder :" + holder);
        } else {
            holder = (ParentHolder) v.getTag();
        }

        ParentData d = mParentList.get(groupPosition);

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
// generate random color
        int color = generator.getRandomColor();



        if(d.getType() == 0){
            //Income
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(d.getCount() + "", Color.rgb(00,96,88));

//            holder.txtCategory.setText(AppConstant.STRING_INCOME[d.getCategory()]);
            holder.imgCount.setImageDrawable(drawable);
//            holder.imgCategory.setImageResource(AppConstant.INCOME[d.getCategory()]);

            Log.d("mylog","Total :" + mTotalIncome / d.getAmount());

            holder.numberProgressBar.setReachedBarColor(Color.rgb(00, 96, 88));
            holder.numberProgressBar.setProgress((d.getAmount()*100)/mTotalIncome);

        }else if(d.getType() == 1){
            //Expanse
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(d.getCount() + "", Color.RED);

            holder.imgCount.setImageDrawable(drawable);
            Log.d("mylog", "Total 1: " + mTotalIncome);
            Log.d("mylog", "Total :" + mTotalExpanse / d.getAmount());
            holder.numberProgressBar.setReachedBarColor(Color.RED);
            holder.numberProgressBar.setProgress((d.getAmount() * 100) / mTotalExpanse);
        }
        holder.imgCategory.setImageResource(AppConstant.EXPENSES[d.getCategory()]);
        holder.txtCategory.setText(AppConstant.STRING_EXPENSES[d.getCategory()]);
        holder.txtTotalAmount.setText(d.getAmount() + " Ks ");
        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;
        ChildHolder holder;
        if (v == null) {
            holder = new ChildHolder();
//            v = inflater.inflate(R.layout.child_row, parent, false);
//            holder.txtChild = (TextView) v.findViewById(R.id.txtChild);
            v.setTag(holder);
        } else {
            holder = (ChildHolder) v.getTag();
        }

        ChildData c = mChild.get(mParentList.get(groupPosition)).get(childPosition);
        holder.txtChild.setText(c.getAmount()+"");

        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class ParentHolder {
        TextView txtCategory, txtTotalAmount;
        ImageView imgCategory, imgCount;
        NumberProgressBar numberProgressBar;

    }

    private class ChildHolder {
        TextView txtChild;
    }
}
