package com.example.employeeapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ShiftsActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView textViewShiftInfo;
    private DatabaseReference databaseReference;

    // Shift data fetched from Firebase
    private HashMap<String, Shift> shiftData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shifts);

        // Initialize views
        calendarView = findViewById(R.id.calendarView);
        textViewShiftInfo = findViewById(R.id.textViewShiftInfo);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("employees");

        // Initialize shift data from Firebase
        fetchShiftData();

        // Set listener for calendar item click
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Create a key in the format "YYYY-MM-DD"
                String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                displayShiftInfo(selectedDate);

                // Get the selected day view
                CalendarView cv = (CalendarView) view;
                LinearLayout ll = (LinearLayout) cv.getChildAt(0);
                LinearLayout ll2 = (LinearLayout) ll.getChildAt(0);
                for (int i = 0; i < ll2.getChildCount(); i++) {
                    View weekView = ll2.getChildAt(i);
                    if (weekView instanceof LinearLayout) {
                        LinearLayout llDays = (LinearLayout) weekView;
                        for (int j = 0; j < llDays.getChildCount(); j++) {
                            View dayView = llDays.getChildAt(j);
                            if (dayView instanceof TextView) {
                                TextView dayTextView = (TextView) dayView;
                                long dayMillis = cv.getDate();
                                int day = (int) (dayMillis / (1000 * 60 * 60 * 24)) + 1;
                                if (day == dayOfMonth) {
                                    // Change the color of the selected date
                                    dayTextView.setTextColor(Color.RED);
                                } else {
                                    // Reset the color of other dates
                                    dayTextView.setTextColor(Color.BLACK);
                                }
                            }
                        }
                    }
                }
            }
        });
        // Set listener for calendar item click
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Create a key in the format "YYYY-MM-DD"
                String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                displayShiftInfo(selectedDate);
            }
        });
    }

    // Fetch shift data from Firebase Realtime Database
    private void fetchShiftData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shiftData = new HashMap<>();
                for (DataSnapshot employeeSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot shiftSnapshot : employeeSnapshot.child("shifts").getChildren()) {
                        Shift shift = shiftSnapshot.getValue(Shift.class);
                        String shiftKey = shiftSnapshot.getKey();
                        shiftData.put(shiftKey, shift);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShiftsActivity.this, "Failed to fetch shift data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Display shift information for the selected date
    private void displayShiftInfo(String selectedDate) {
        if (shiftData != null && shiftData.containsKey(selectedDate)) {
            Shift shift = shiftData.get(selectedDate);
            String shiftInfo = "Start Time: " + shift.getStartTime() + "\n" +
                    "End Time: " + shift.getEndTime() + "\n" +
                    "Clock-in Status: " + shift.getClockInStatus();
            textViewShiftInfo.setText(shiftInfo);
        } else {
            textViewShiftInfo.setText("No shift recorded for this date.");
        }
    }


}
