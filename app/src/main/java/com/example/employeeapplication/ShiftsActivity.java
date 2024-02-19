package com.example.employeeapplication;

import android.os.Bundle;
import android.widget.CalendarView;
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
    private HashMap<String, String> shiftData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shifts);

        // Initialize views
        calendarView = findViewById(R.id.calendarView);
        textViewShiftInfo = findViewById(R.id.textViewShiftInfo);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("shifts");

        // Initialize shift data from Firebase
        fetchShiftData();

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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shiftData = new HashMap<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shift shift = snapshot.getValue(Shift.class);
                    String shiftInfo = shift.getStartTime() + " - " + shift.getEndTime(); // Customize this as per your requirement
                    shiftData.put(shift.getShiftDate(), shiftInfo);
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
            textViewShiftInfo.setText(shiftData.get(selectedDate));
        } else {
            textViewShiftInfo.setText("No shift recorded for this date.");
        }
    }

}
