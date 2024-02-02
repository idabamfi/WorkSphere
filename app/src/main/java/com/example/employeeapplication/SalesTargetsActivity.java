package com.example.employeeapplication;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;

public class SalesTargetsActivity extends AppCompatActivity {
    private TextView textViewSalesTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        // Initialize UI elements
        textViewSalesTarget = findViewById(R.id.textViewSalesTarget);

        // Read data from Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("sales_targets");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder salesTargetInfo = new StringBuilder();

                    // Ensure days are displayed in a specific order (Monday to Sunday)
                    String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

                    for (String day : daysOfWeek) {
                        DataSnapshot daySnapshot = dataSnapshot.child(day);

                        if (daySnapshot.exists()) {
                            String date = daySnapshot.child("dateGiven").getValue(String.class);
                            int salesTarget = daySnapshot.child("salesTarget").getValue(Integer.class);
                            int salesDifference = daySnapshot.child("salesDifference").getValue(Integer.class);

                            salesTargetInfo.append("Day: ").append(day).append("\n");
                            salesTargetInfo.append("Date: ").append(date).append("\n");
                            salesTargetInfo.append("Sales Target: ").append(salesTarget).append("\n");
                            salesTargetInfo.append("Sales Difference: ").append(salesDifference).append("\n\n");
                        } else {
                            salesTargetInfo.append("No data available for ").append(day).append("\n\n");
                        }
                    }

                    textViewSalesTarget.setText(salesTargetInfo.toString());
                } else {
                    textViewSalesTarget.setText("No sales target data available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                textViewSalesTarget.setText("Error loading sales target data");
            }
        });
    }

}
