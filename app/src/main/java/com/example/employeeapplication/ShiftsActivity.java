package com.example.employeeapplication;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShiftsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
        setContentView(R.layout.activity_shifts);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            month += 1;

            String selectedDate = year + "-" + month + "-" + dayOfMonth;

            Toast.makeText(getApplicationContext(), "Selected date: " + selectedDate, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "You have no Shifts Scheduled ", Toast.LENGTH_SHORT).show();
        });
    }

}
