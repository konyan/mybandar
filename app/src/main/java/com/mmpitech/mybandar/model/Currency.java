package com.mmpitech.mybandar.model;

/**
 * Created by zirate on 3/9/16.
 */
public class Currency {


    private int flags;
    private String price;
    private String country;
    public Currency(int flags,String country,String price){
        this.country  = country;
        this.flags = flags;
        this.price = price;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
