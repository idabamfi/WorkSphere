package com.example.employeeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHomepageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhomepage);

        // Locate the buttons by their IDs
        for (int i : new int[]{R.id.buttonSetSales, R.id.buttonSetShifts, R.id.buttonViewClockIn, R.id.buttonAddEmployee, R.id.buttonNotification}) {
            findViewById(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view){
        Intent intent;
        if (view.getId() == R.id.buttonSetSales){
            intent = new Intent(this, SalesAdminActivity.class);
            startActivity(intent);
            // change to admin shifts
        } else if (view.getId() == R.id.buttonSetShifts) {
            intent = new Intent(this, AdminShiftsActivity.class);
            startActivity(intent);

        } else if (view.getId() == R.id.buttonViewClockIn) {
            intent = new Intent(this, AdminClockInTimesActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.buttonAddEmployee) {
            //change activity
           intent = new Intent(this, EmployeeInformationActivity.class);
            startActivity(intent);
        }  else if (view.getId() == R.id.buttonNotification) {
            intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);
        }
    }



}
