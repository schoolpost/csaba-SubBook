package com.example.schoolpost.csaba_subbook;

import java.util.Date;

/**
 * Created by schoo on 1/29/2018.
 */

public class Subscription {
    private String name;
    private Date date;
    private float cost;
    private String comment;

    Subscription(String name, Date date, float cost, String comment) {
        this.name = name;
        this.date = date;
        this.cost = cost;
        this.comment = comment;
    }

    Subscription(String name, float cost, String comment) {
        this.name = name;
        this.date = new Date();
        this.cost = cost;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws SubscriptionNameException {
        if (name.length() <= 20) {
            this.name = name;
        } else {
            throw new SubscriptionNameException();
        }
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) throws SubscriptionCostException {
        if (cost >= 0) {
            this.cost = cost;
        } else {
            throw new SubscriptionCostException();
        }
        this.cost = cost;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}

