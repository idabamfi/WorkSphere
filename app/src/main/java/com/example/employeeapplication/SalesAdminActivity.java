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

    private EditText editTextDate, editTextSalesTarget;
    private Button addButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_admin);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("sales_targets");

        // Initialize UI elements
        editTextDate = findViewById(R.id.editTextDate);
        editTextSalesTarget = findViewById(R.id.editTextSalesTarget);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSalesTarget();
            }
        });
    }

    private void addSalesTarget() {
        String date = editTextDate.getText().toString().trim();
        String salesTarget = editTextSalesTarget.getText().toString().trim();

        // Check if both fields are not empty
        if (date.isEmpty() || salesTarget.isEmpty()) {
            Toast.makeText(this, "Please enter both date and sales target", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update database with new sales target
        databaseReference.child(date).setValue(salesTarget)
                .addOnSuccessListener(aVoid -> {
                    // Database updated successfully
                    Toast.makeText(SalesAdminActivity.this, "Sales target updated for " + date, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Error occurred while updating database
                    Toast.makeText(SalesAdminActivity.this, "Failed to update sales target: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}