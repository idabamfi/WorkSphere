package com.example.employeeapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;

public class HomepageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Locate the buttons by their IDs
        for (int i : new int[]{R.id.buttonClockin, R.id.buttonShifts, R.id.buttonTargets, R.id.buttonPay, R.id.buttonNotification}) {
            findViewById(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view){
        Intent intent;
        if (view.getId() == R.id.buttonClockin){
            intent = new Intent(this, ClockinActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.buttonShifts) {
            intent = new Intent(this, ShiftsActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.buttonTargets) {
            intent = new Intent(this, SalesTargetsActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.buttonPay) {
            intent = new Intent(this, PayActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.buttonNotification) {
            intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);
        }
    }



}
