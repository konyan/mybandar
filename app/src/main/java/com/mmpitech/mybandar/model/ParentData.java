package com.mmpitech.mybandar.model;

import java.util.Date;

/**
 * Created by zirate on 3/17/16.
 */
public class ParentData {


    String note;
    int amount,count,progress,type,category;
    Date date;

    public ParentData() {
    }

    public ParentData(int category, int amount, int count, int progress) {
        this.category = category;
        this.amount = amount;
        this.count = count;
        this.progress = progress;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
