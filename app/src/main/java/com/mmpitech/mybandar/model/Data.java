package com.mmpitech.mybandar.model;


import java.util.Date;

/**
 * Created by zirate on 3/17/16.
 */
public class Data {

    private int category;
    private int amount;
    private Date date;
    private String note;


    public Data(int category, int amount, Date date, String note) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.note = note;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
