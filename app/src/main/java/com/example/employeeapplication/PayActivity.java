package com.example.employeeapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
        setContentView(R.layout.activity_pay);
    }
}
