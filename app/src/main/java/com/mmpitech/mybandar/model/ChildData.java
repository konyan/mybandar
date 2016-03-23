package com.mmpitech.mybandar.model;

import java.util.Date;

/**
 * Created by nyanlintun on 3/18/16.
 */
public class ChildData {


    private String note;
    private int amount;
    private Date date;

    public ChildData() {
    }

    public ChildData(String note, int amount, Date date) {
        this.note = note;
        this.amount = amount;
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
