package com.example.employeeapplication;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.rxjava3.annotations.NonNull;

public class ShiftsActivity extends AppCompatActivity {
    private TextView textViewShiftInfo;
    private DatabaseReference databaseReference;

    // Shift data fetched from Firebase
    private Shift shift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shifts);

        // Initialize views
        CalendarView calendarView = findViewById(R.id.calendarView);
        textViewShiftInfo = findViewById(R.id.textViewShiftInfo);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("employees");

        // Set listener for calendar item click
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Create a key in the format "YYYY-MM-DD"
            String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            displayShiftInfo(selectedDate);
        });
    }

    // Display shift information for the selected date
    private void displayShiftInfo(String selectedDate) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot employeeSnapshot : dataSnapshot.getChildren()) {
                    if (employeeSnapshot.hasChild("shifts")) {
                        DataSnapshot shiftsSnapshot = employeeSnapshot.child("shifts");
                        for (DataSnapshot shiftSnapshot : shiftsSnapshot.getChildren()) {
                            Shift shift = shiftSnapshot.getValue(Shift.class);
                            if (shift != null && shift.getShiftDate().equals(selectedDate)) {
                                displayShiftDetails(shift);
                                return;
                            }
                        }
                    }
                }
                // If no shift found for the selected date, display a message
                textViewShiftInfo.setText("No shift recorded for this date.");
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull @NonNull DatabaseError databaseError) {
                Toast.makeText(ShiftsActivity.this, "Failed to fetch shift data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Display shift details in the TextView
    private void displayShiftDetails(Shift shift) {
        String shiftInfo = "Shift Date: " + shift.getShiftDate() + "\n" +
                "Start Time: " + shift.getStartTime() + "\n" +
                "End Time: " + shift.getEndTime() + "\n" +
                "Clock-in Status: " + shift.getClockInStatus();
        textViewShiftInfo.setText(shiftInfo);
    }
}
