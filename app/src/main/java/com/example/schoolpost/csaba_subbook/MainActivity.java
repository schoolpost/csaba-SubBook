package com.example.schoolpost.csaba_subbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "sub_list.sav";
    private ListView listView;
    private ArrayList<Subscription> subscriptions;
    private ArrayAdapter<Subscription> adapter;
    private static float totalCost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addButton = (Button) findViewById(R.id.addButton);
        TextView summaryCost = (TextView) findViewById(R.id.totalCost);
        listView = (ListView) findViewById(R.id.SubscriptionList);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSubscription(view);
            }
        });

        summaryCost.setText("$" + String.format("%.2f", totalCost));

    }


    public void addSubscription(View view) {
        Intent intentAdd = new Intent(this, SubscriptionActivty.class);
        startActivityForResult(intentAdd, Intent_Constants.INTENT_REQUEST_CODE);

    }


}
