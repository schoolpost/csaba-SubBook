package com.example.schoolpost.csaba_subbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class SubscriptionActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_subscription);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);

        Intent intent = getIntent();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) findViewById(R.id.nameField)).getText().toString();
                String date = ((EditText) findViewById(R.id.dateField)).getText().toString();
                String cost = ((EditText) findViewById(R.id.costField)).getText().toString();
                if (validateName(name) && validateDate(date) && validateCost(cost)) {
                    Intent intentSave = new Intent();
                    intentSave.putExtra(Intent_Constants.SUBSCRIPTION_NAME_FIELD, name);
                    intentSave.putExtra(Intent_Constants.SUBSCRIPTION_COST_FIELD, cost);
                    intentSave.putExtra(Intent_Constants.SUBSCRIPTION_DATE_FIELD, date);
                    setResult(Intent_Constants.INTENT_RESULT_CODE, intentSave);
                    finish();
                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private Boolean validateName(String name) {
        if (name.length() > 20 || name.length() == 0) {
            Toast.makeText(getApplicationContext(), "Name Error", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private Boolean validateCost(String cost) {
        if (cost.length() == 0) {
            Toast.makeText(getApplicationContext(), "Price Error", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Float costFloat = Float.parseFloat(cost);
            if (costFloat < 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Price Error", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private Boolean validateDate(String date) {
        if (date.length() == 0) {
            Toast.makeText(getApplicationContext(), "Date Error", Toast.LENGTH_SHORT).show();
            return false;
        }

        SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-DD");
        try {
            formatted.parse(date);
            return true;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Date Error", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
