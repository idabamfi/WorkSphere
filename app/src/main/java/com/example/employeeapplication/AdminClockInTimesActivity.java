package com.example.employeeapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminClockInTimesActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_clockintimes);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("employees");

        fetchClockInTimes();
    }

    private void fetchClockInTimes() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            databaseReference.child(userId).child("shifts").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    LinearLayout layout = findViewById(R.id.layoutClockInTimes);
                    int id = 0;

                    for (DataSnapshot shiftSnapshot : dataSnapshot.getChildren()) {
                        Shift shift = shiftSnapshot.getValue(Shift.class);
                        if (shift != null) {
                            String shiftDate = shift.getShiftDate();
                            String shiftType = shift.getShiftType();
                            String clockInTime = shift.getClockInTime();

                            // TextView to display the shift details
                            TextView textViewShiftDetails = new TextView(AdminClockInTimesActivity.this);
                            textViewShiftDetails.setId(id++);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            params.setMargins(0, 0, 0, 20);
                            textViewShiftDetails.setLayoutParams(params);
                            textViewShiftDetails.setText(String.format("Date: %s\nType: %s\n", shiftDate, shiftType));

                            // Check clock in time and set its style accordingly
                            TextView textViewClockInTime = new TextView(AdminClockInTimesActivity.this);
                            textViewClockInTime.setId(id++);
                            textViewClockInTime.setLayoutParams(params);
                            textViewClockInTime.setText("Clock In Time: ");
                            if (clockInTime != null) {
                                if (clockInTime.compareTo(shiftType) < 0) {
                                    textViewClockInTime.append(clockInTime); // Clock in before start time
                                } else {
                                    textViewClockInTime.append("Late");
                                    textViewClockInTime.setTextColor(Color.RED);
                                    textViewClockInTime.setTypeface(null, android.graphics.Typeface.BOLD);
                                }
                            } else {
                                textViewClockInTime.append("Not available");
                            }

                            // Add TextViews to the layout
                            layout.addView(textViewShiftDetails);
                            layout.addView(textViewClockInTime);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                }
            });
        }
    }
}