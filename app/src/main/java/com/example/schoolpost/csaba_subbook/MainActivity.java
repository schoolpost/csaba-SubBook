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

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "sub_list.sav";
    private ListView listView;
    private ArrayList<Subscription> subscriptions;
    private ArrayAdapter<Subscription> adapter;
    private static float totalCost = 0.0f;
    private TextView summaryCost;

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
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSubscription(view);
            }
        });


    }


    public void addSubscription(View view) {
        Intent intentAdd = new Intent();
        intentAdd.setClass(MainActivity.this, SubscriptionActivty.class);
        startActivityForResult(intentAdd, Intent_Constants.INTENT_REQUEST_CODE);

    }

    public void calcTotal() {
        Float sum = 0.0f;
        for (int i = 0; i < subscriptions.size(); i++) {
            sum += Float.parseFloat(subscriptions.get(i).getCost());
        }
        totalCost = sum;
        summaryCost.setText("$" + String.format("%.2f", totalCost));

    }

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
    }

    @Override
    protected void onStart() {

        super.onStart();
        loadFromFile();
        calcTotal();

        adapter = new ArrayAdapter<Subscription>(this, R.layout.list_item, subscriptions);
        listView.setAdapter(adapter);

    }

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
