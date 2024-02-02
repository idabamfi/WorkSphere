package com.example.employeeapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;

public class AdminHomepageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhomepage);

        // Locate the buttons by their IDs
        for (int i : new int[]{R.id.buttonSetSales, R.id.buttonSetShifts, R.id.buttonViewClockIn, R.id.buttonAddEmployee}) {
            findViewById(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view){
        Intent intent;
        if (view.getId() == R.id.buttonSetSales){
            intent = new Intent(this, SalesAdminActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.buttonSetShifts) {
            intent = new Intent(this, ShiftsActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.buttonViewClockIn) {
            intent = new Intent(this, SalesAdminActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.buttonAddEmployee) {
            intent = new Intent(this, PayActivity.class);
            startActivity(intent);
        }
    }



}
