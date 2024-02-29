package com.example.employeeapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.rxjava3.annotations.NonNull;

public class SalesTargetsActivity extends AppCompatActivity {
    private TextView shift1SalesText, shift2SalesText, shift3SalesText, shift1DateText, shift2DateText, shift3DateText   ;
    private EditText shift1AchievedInput, shift2AchievedInput, shift3AchievedInput;
    private Button saveButton;
    private DatabaseReference employeeRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        employeeRef = FirebaseDatabase.getInstance().getReference().child("employees")
                .child(currentUser.getUid());

        // Initialize views
        shift1SalesText = findViewById(R.id.shift1SalesText);
        shift2SalesText = findViewById(R.id.shift2SalesText);
        shift3SalesText = findViewById(R.id.shift3SalesText);
        shift1DateText = findViewById(R.id.shift1DateText);
        shift2DateText = findViewById(R.id.shift2DateText);
        shift3DateText = findViewById(R.id.shift3DateText);
        shift1AchievedInput = findViewById(R.id.shift1Achieved);
        shift2AchievedInput = findViewById(R.id.shift2Achieved);
        shift3AchievedInput = findViewById(R.id.shift3Achieved);
        saveButton = findViewById(R.id.saveButton);


        // Retrieve and display sales targets
        displaySalesTargets();

        // Save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSalesAchieved();
            }
        });
    }

    private void displaySalesTargets() {
        employeeRef.child("shifts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shiftSnapshot : snapshot.getChildren()) {
                    String shiftId = shiftSnapshot.getKey();
                    if (shiftId != null) {
                        Long salesTarget = shiftSnapshot.child("salesTarget").child("salesTarget").getValue(Long.class);
                        String shiftDate = shiftSnapshot.child("shiftDate").getValue(String.class);
                        if (shiftDate != null && salesTarget != null) {
                            String salesTargetText = "Shift " + shiftId.substring(5) + ", Sales Target: £" + salesTarget;
                            String shiftDateText =  " - Date: " + shiftDate;
                            switch (shiftId) {
                                case "shift1":
                                    shift1SalesText.setText(salesTargetText);
                                    shift1DateText.setText(shiftDateText);
                                    break;
                                case "shift2":
                                    shift2SalesText.setText(salesTargetText);
                                    shift2DateText.setText(shiftDateText);
                                    break;
                                case "shift3":
                                    shift3SalesText.setText(salesTargetText);
                                    shift1DateText.setText(shiftDateText);
                                    break;
                                // Add cases for more shifts here
                            }
                        }
                    }
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SalesTargetsActivity.this, "Failed to retrieve sales targets: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveSalesAchieved() {
        // Get achieved sales values from EditText inputs
        String shift1Achieved = shift1AchievedInput.getText().toString().trim();
        String shift2Achieved = shift2AchievedInput.getText().toString().trim();
        String shift3Achieved = shift3AchievedInput.getText().toString().trim();

        // Update the database with achieved sales values
        employeeRef.child("shifts").child("shift1").child("salesTarget").child("salesAchieved").setValue(shift1Achieved);
        employeeRef.child("shifts").child("shift2").child("salesTarget").child("salesAchieved").setValue(shift2Achieved);
        employeeRef.child("shifts").child("shift3").child("salesTarget").child("salesAchieved").setValue(shift3Achieved);

        // Display success message
        Toast.makeText(this, "Sales targets updated successfully", Toast.LENGTH_SHORT).show();
    }



}

