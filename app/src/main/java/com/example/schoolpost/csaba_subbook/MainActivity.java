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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Represents the main activty of the application.
 *
 * @author csabanagy
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {

//    Concepts and structure sourced from https://www.youtube.com/watch?v=3QHgJnPPnqQ&t=1227s

    private static final String FILENAME = "sub_list.sav";
    private ListView listView;
    private ArrayList<Subscription> subscriptions;
    private ArrayAdapter<Subscription> adapter;
    private static float totalCost = 0.0f;
    private TextView summaryCost;

    /**
     * Initializes the UI Elements and creates an empty listview to hold the subscriptions.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addButton = (Button) findViewById(R.id.addButton);
        summaryCost = (TextView) findViewById(R.id.totalCost);
        listView = (ListView) findViewById(R.id.subscriptionList);
        subscriptions = new ArrayList<Subscription>();
        adapter = new ArrayAdapter<Subscription>(this, R.layout.list_item, subscriptions);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * When a list item is clicked.
             * @param adapterView adapterview
             * @param view view
             * @param i item index
             * @param l item index
             */

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SubscriptionEditActivity.class);
                intent.putExtra(Intent_Constants.SUBSCRIPTION_NAME_DATA, subscriptions.get(i).getName());
                intent.putExtra(Intent_Constants.SUBSCRIPTION_DATE_DATA, subscriptions.get(i).getDate());
                intent.putExtra(Intent_Constants.SUBSCRIPTION_COST_DATA, subscriptions.get(i).getCost());
                intent.putExtra(Intent_Constants.SUBSCRIPTION_COMMENT_DATA, subscriptions.get(i).getComment());
                intent.putExtra(Intent_Constants.SUBSCRIPTION_INDEX, i);
                startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE_TWO);

            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the add button is clicked.
             */
            @Override
            public void onClick(View view) {
                addSubscription(view);
            }
        });


    }

    /**
     * Invoked when the add button is pressed and creates a new intent.
     *
     * @param view view object
     */


    public void addSubscription(View view) {
        Intent intentAdd = new Intent();
        intentAdd.setClass(MainActivity.this, SubscriptionActivty.class);
        startActivityForResult(intentAdd, Intent_Constants.INTENT_REQUEST_CODE);

    }

    /**
     * Iterates through the subscriptions array and calculates the total cost.
     */

    public void calcTotal() {
        Float sum = 0.0f;
        for (int i = 0; i < subscriptions.size(); i++) {
            sum += Float.parseFloat(subscriptions.get(i).getCost());
        }
        totalCost = sum;
        summaryCost.setText("$" + String.format("%.2f", totalCost));

    }

    /**
     * Represents the callback function when an Intent returns a result to the current activity.
     * Handles different tasks based on intent result codes.
     *
     * @param requestCode intent request code
     * @param resultCode  intent result code
     * @param data        intent data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Intent_Constants.INTENT_REQUEST_CODE) {
            String name = data.getStringExtra(Intent_Constants.SUBSCRIPTION_NAME_FIELD);
            String date = data.getStringExtra(Intent_Constants.SUBSCRIPTION_DATE_FIELD);
            String cost = data.getStringExtra(Intent_Constants.SUBSCRIPTION_COST_FIELD);
            String comment = data.getStringExtra(Intent_Constants.SUBSCRIPTION_COMMENT_FIELD);
            Subscription sub = new Subscription(name, date, cost, comment);
            subscriptions.add(sub);
            calcTotal();
            adapter.notifyDataSetChanged();
            saveInFile();
        }

        if (resultCode == Intent_Constants.INTENT_REQUEST_CODE_TWO) {
            String name = data.getStringExtra(Intent_Constants.SUBSCRIPTION_NAME_FIELD_EDIT);
            String date = data.getStringExtra(Intent_Constants.SUBSCRIPTION_DATE_FIELD_EDIT);
            String cost = data.getStringExtra(Intent_Constants.SUBSCRIPTION_COST_FIELD_EDIT);
            String comment = data.getStringExtra(Intent_Constants.SUBSCRIPTION_COMMENT_FIELD_EDIT);
            int index = data.getIntExtra(Intent_Constants.SUBSCRIPTION_INDEX, -1);

            subscriptions.get(index).setName(name);
            subscriptions.get(index).setDate(date);
            subscriptions.get(index).setCost(cost);
            subscriptions.get(index).setComment(comment);

            calcTotal();
            adapter.notifyDataSetChanged();
            saveInFile();

        }

        if (resultCode == Intent_Constants.INTENT_DELETE_CODE) {
            int index = data.getIntExtra(Intent_Constants.SUBSCRIPTION_INDEX, -1);
            subscriptions.remove(index);
            calcTotal();
            adapter.notifyDataSetChanged();
            saveInFile();

        }
    }

    /**
     * Called at the start of the app, loads the GSON from file,
     * calculates the sum of the subscription costs and populates the
     * listview with the subscription objects.
     */

    @Override
    protected void onStart() {

        super.onStart();
        loadFromFile();
        calcTotal();

        adapter = new ArrayAdapter<Subscription>(this, R.layout.list_item, subscriptions);
        listView.setAdapter(adapter);

    }

    /**
     * Loads the GSON file into a subscription arraylist.
     */

    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-25
            Type listType = new TypeToken<ArrayList<Subscription>>() {
            }.getType();
            subscriptions = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subscriptions = new ArrayList<Subscription>();
        } catch (IOException e) {
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    /**
     * Saves the subscriptions GSON into a file.
     */

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(subscriptions, out);
            out.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Called when activity is destroyed.
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
