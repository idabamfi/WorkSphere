package com.example.employeeapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminShiftsActivity extends AppCompatActivity {
    private Spinner spinnerEmployeeName, spinnerShiftType;
    private EditText editTextShiftDate, editTextLunchBreak;
    private Button buttonSaveShift;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_shifts);

        // Initialize database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("employees");

        // Initialize views
        spinnerEmployeeName = findViewById(R.id.spinnerEmployeeName);
        spinnerShiftType = findViewById(R.id.spinnerShiftType);
        editTextShiftDate = findViewById(R.id.editTextShiftDate);
        editTextLunchBreak = findViewById(R.id.editTextLunchBreak);
        buttonSaveShift = findViewById(R.id.buttonSaveShift);

        // Populate spinners
        populateEmployeeSpinner();
        populateShiftTypeSpinner();

        // Save shift button click listener
        buttonSaveShift.setOnClickListener(v -> saveShiftData());
    }

    private void populateEmployeeSpinner() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> employeeNames = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String employeeName = snapshot.child("employeeName").getValue(String.class);
                    if (employeeName != null) {
                        employeeNames.add(employeeName);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminShiftsActivity.this, android.R.layout.simple_spinner_item, employeeNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerEmployeeName.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AdminShiftsActivity.this, "Failed to retrieve employee names", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateShiftTypeSpinner() {
        // Populate spinner with shift types
        List<String> shiftTypes = new ArrayList<>();
        shiftTypes.add("Morning Shift (8:00 - 13:00)");
        shiftTypes.add("Afternoon Shift (13:00 - 17:00)");
        shiftTypes.add("Evening Shift (17:00 - 22:00)");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, shiftTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShiftType.setAdapter(adapter);
    }

    private void saveShiftData() {
        String employeeName = spinnerEmployeeName.getSelectedItem().toString();
        String shiftType = spinnerShiftType.getSelectedItem().toString();
        String shiftDate = editTextShiftDate.getText().toString().trim();
        String lunchBreak = editTextLunchBreak.getText().toString().trim();

        String employeeId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference employeeRef = databaseReference.child(employeeId);

        String shiftId = employeeRef.child("shifts").push().getKey();

        Shift shift = new Shift(shiftId, shiftDate, shiftType, lunchBreak);

        employeeRef.child("shifts").child(shiftId).setValue(shift)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AdminShiftsActivity.this, "Shift data saved successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AdminShiftsActivity.this, "Failed to save shift data", Toast.LENGTH_SHORT).show();
                });
    }
}