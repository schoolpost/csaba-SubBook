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
                String comment = ((EditText) findViewById(R.id.commentField)).getText().toString();
                if (validateName(name) && validateDate(date) && validateCost(cost) && validateComment(comment)) {
                    Intent intentSave = new Intent();
                    intentSave.putExtra(Intent_Constants.SUBSCRIPTION_NAME_FIELD, name);
                    intentSave.putExtra(Intent_Constants.SUBSCRIPTION_COST_FIELD, cost);
                    intentSave.putExtra(Intent_Constants.SUBSCRIPTION_DATE_FIELD, date);
                    intentSave.putExtra(Intent_Constants.SUBSCRIPTION_COMMENT_FIELD, comment);
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
        if (name.length() > 20) {
            Toast.makeText(getApplicationContext(), "Name is too long! ( 20 character limit )", Toast.LENGTH_SHORT).show();
            return false;
        } else if (name.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter a Name!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Boolean validateCost(String cost) {
        if (cost.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter a price!", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Float costFloat = Float.parseFloat(cost);
            if (costFloat < 0) {
                Toast.makeText(getApplicationContext(), "Price input error!", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Price input error!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private Boolean validateDate(String date) {
        if (date.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter a date!", Toast.LENGTH_SHORT).show();
            return false;
        }

        SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatted.parse(date);
            return true;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Date format error!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private Boolean validateComment(String comment) {
        if (comment.length() > 30) {
            Toast.makeText(getApplicationContext(), "Comment is too long! ( 30 character limit )", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
