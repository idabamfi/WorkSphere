package com.example.employeeapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PayActivity extends AppCompatActivity {

    private TextView textViewPayDate, textViewHourlyPay, textViewAccumulatedPay;
    private DatabaseReference databaseReference;
    private String employeeId;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
        setContentView(R.layout.activity_pay);

        textViewPayDate = findViewById(R.id.textViewPayDate);
        textViewHourlyPay = findViewById(R.id.textViewHourlyPay);
        textViewAccumulatedPay = findViewById(R.id.textViewAccumulatedPay);

        // Get employee ID from Intent or wherever you store it
        employeeId = "ochv3pOEJiabJjvYySg059ujoWk1"; // Example employee ID

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("employees").child(employeeId);

        // Retrieve pay data from Firebase
        fetchPayData();
    }
    private void fetchPayData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Retrieve pay data from dataSnapshot
                String payDate = dataSnapshot.child("pay").child("payDate").getValue(String.class);
                double hourlyPay = dataSnapshot.child("pay").child("hourlyPay").getValue(Double.class);
                double accumulatedPay = calculateAccumulatedPay(dataSnapshot.child("shifts"), hourlyPay);

                // Display pay data
                textViewPayDate.setText("Pay Date: " + payDate);
                textViewHourlyPay.setText("Hourly Pay: $" + hourlyPay);
                textViewAccumulatedPay.setText("Accumulated Pay: $" + accumulatedPay);

                // Update accumulated pay in the database
                dataSnapshot.child("pay").child("accumulatedPay").getRef().setValue(accumulatedPay);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PayActivity.this, "Failed to fetch pay data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private double calculateAccumulatedPay(DataSnapshot shiftsSnapshot, double hourlyPay) {
        double totalHoursWorked = 0.0;

        // Iterate through each shift of the employee
        for (DataSnapshot shiftSnapshot : shiftsSnapshot.getChildren()) {
            double shiftHours = shiftSnapshot.child("totalHours").getValue(Double.class);
            totalHoursWorked += shiftHours;
        }

        // Calculate accumulated pay
        return totalHoursWorked * hourlyPay;
    }


}
