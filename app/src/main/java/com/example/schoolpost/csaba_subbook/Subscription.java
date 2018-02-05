/*
 * Main Activity
 *
 * Version 1.0
 *
 * 2/5/2018
 *
 * Copyright (c) 2018.
 */

package com.example.schoolpost.csaba_subbook;

/**
 * Represents a subsription object.
 *
 * @author csabanagy
 * @version 1.0
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

    /**
     * Gets the subscription name.
     *
     * @return String subscription name.
     */

    public String getName() {
        return name;
    }

    /**
     * Sets the subscription name.
     *
     * @param name subscription name.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the subscription date.
     *
     * @return String subscription date.
     */

    public String getDate() {
        return date;
    }

    /**
     * Sets the subscription date.
     *
     * @param date subscription date.
     */

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the subscription cost.
     *
     * @return String subscription cost.
     */

    public String getCost() {
        return cost;
    }

    /**
     * Sets the subscription cost.
     *
     * @param cost subscription cost.
     */

    public void setCost(String cost) {
        this.cost = cost;
    }

    /**
     * Gets the subscription comment.
     *
     * @return String subscription comment.
     */

    public String getComment() {
        return comment;
    }

    /**
     * Sets the subscription comment.
     *
     * @param comment subscription cost.
     */

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Returns a string concatenation of name, date and cost separated by newline characters.
     * Format ( Name:{name} \n Date:{date} \n Cost:${cost} )
     *
     * @return String
     */

    public String toString() {

        return "Name: " + name + " \n" + "Date: " + date + " \n" + "Cost: $" + cost;
    }

}

