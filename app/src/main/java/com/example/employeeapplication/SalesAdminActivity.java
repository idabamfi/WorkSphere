package com.example.employeeapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SalesAdminActivity extends AppCompatActivity {

    private EditText editTextDay, editTextDate, editTextSalesTarget, editTextSalesDifference;
    private Button addButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_admin);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("sales_targets");

        // Initialize UI elements
        editTextDay = findViewById(R.id.editTextDay);
        editTextDate = findViewById(R.id.editTextDate);
        editTextSalesTarget = findViewById(R.id.editTextSalesTarget);
        editTextSalesDifference = findViewById(R.id.editTextSalesDifference);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSalesTarget();
            }
        });
    }

    private void addSalesTarget() {
        String day = editTextDay.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        int salesTarget = Integer.parseInt(editTextSalesTarget.getText().toString().trim());
        int salesDifference = Integer.parseInt(editTextSalesDifference.getText().toString().trim());

        // Create a new instance of the SalesTarget class
        SalesTarget salesTargetObj = new SalesTarget(date, "", salesDifference, salesTarget);

        // Push the new sales target to the Firebase Realtime Database
        databaseReference.child(day).setValue(salesTargetObj);
    }

    // Inner class representing a SalesTarget
    public static class SalesTarget {
        public String dateGiven;
        public String salesAchieved;
        public int salesDifference;
        public int salesTarget;

        public SalesTarget() {
            // Default constructor required for calls to DataSnapshot.getValue(SalesTarget.class)
        }

        public SalesTarget(String dateGiven, String salesAchieved, int salesDifference, int salesTarget) {
            this.dateGiven = dateGiven;
            this.salesAchieved = salesAchieved;
            this.salesDifference = salesDifference;
            this.salesTarget = salesTarget;
        }
    }
}