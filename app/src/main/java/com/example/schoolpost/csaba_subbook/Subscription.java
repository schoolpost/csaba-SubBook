package com.example.schoolpost.csaba_subbook;

/**
 * Created by schoo on 1/29/2018.
 */

public class Subscription {
    private String name;
    private String date;
    private String cost;
    private String comment;

    Subscription(String name, String date, String cost, String comment) {
        this.name = name;
        this.date = date;
        this.cost = cost;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {


        this.cost = cost;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {

        return "Name: " + name + " \n" + "Date: " + date + " \n" + "Cost: $" + cost;
    }

}

