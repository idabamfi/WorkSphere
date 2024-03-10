package com.example.employeeapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SalesAdminActivity extends AppCompatActivity {

    private EditText editTextEmployeeName, editTextShiftDate, editTextSalesTarget;
    private Button updateButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_admin);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("employees");

        // Initialize UI elements
        editTextEmployeeName = findViewById(R.id.editTextEmployeeName);
        editTextShiftDate = findViewById(R.id.editTextShiftDate);
        editTextSalesTarget = findViewById(R.id.editTextSalesTarget);
        updateButton = findViewById(R.id.updateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSalesTarget();
            }
        });
    }

    private void updateSalesTarget() {
        String employeeName = editTextEmployeeName.getText().toString().trim();
        String shiftDate = editTextShiftDate.getText().toString().trim();
        String salesTarget = editTextSalesTarget.getText().toString().trim();

        // Check if any field is empty
        if (employeeName.isEmpty() || shiftDate.isEmpty() || salesTarget.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update database with new sales target
        databaseReference.child(employeeName).child("shifts").child(shiftDate).child("salesTarget").setValue(salesTarget)
                .addOnSuccessListener(aVoid -> {
                    // Database updated successfully
                    Toast.makeText(SalesAdminActivity.this, "Sales target updated for " + employeeName + " on " + shiftDate, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Error occurred while updating database
                    Toast.makeText(SalesAdminActivity.this, "Failed to update sales target: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}


