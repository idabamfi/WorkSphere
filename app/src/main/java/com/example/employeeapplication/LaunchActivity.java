package com.example.employeeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        for (int i : new int[]{R.id.buttonAdmin, R.id.buttonEmployee}) {
            findViewById(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view){
        Intent intent;
        if (view.getId() == R.id.buttonAdmin){
            intent = new Intent(this, ClockinActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.buttonEmployee) {
            intent = new Intent(this, ShiftsActivity.class);
            startActivity(intent);
        }
    }
}
