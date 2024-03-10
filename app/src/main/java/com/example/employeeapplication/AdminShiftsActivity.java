package com.example.employeeapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AdminShiftsActivity extends AppCompatActivity {
    private EditText editTextEmployeeName, editTextShiftDate, editTextStartTime, editTextEndTime, editTextLunchBreak;
    private Button buttonSaveShift;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_shifts);

        editTextEmployeeName = editTextEmployeeName.findViewById(R.id.editTextEmployeeName);
        editTextShiftDate = editTextShiftDate.findViewById(R.id.editTextShiftDate);
        editTextStartTime = editTextStartTime.findViewById(R.id.editTextStartTime);
        editTextEndTime = editTextEndTime.findViewById(R.id.editTextEndTime);
        editTextLunchBreak = editTextLunchBreak.findViewById(R.id.editTextLunchBreak);

        buttonSaveShift = buttonSaveShift.findViewById(R.id.buttonSaveShift);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("employees");

        buttonSaveShift.setOnClickListener(v -> saveShiftData());
    }

    private void saveShiftData() {
        String employeeId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        String shiftId = databaseReference.child(employeeId).child("shifts").push().getKey();

        String employeeName = editTextEmployeeName.getText().toString().trim();
        String shiftDate = editTextShiftDate.getText().toString().trim();
        String startTime = editTextStartTime.getText().toString().trim();
        String endTime = editTextEndTime.getText().toString().trim();
        String lunchBreak = editTextLunchBreak.getText().toString().trim();

        Shift shift = new Shift(shiftId, shiftDate, startTime, endTime, lunchBreak);

        databaseReference.child(employeeName).child("shifts").child(shiftId).setValue(shift)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AdminShiftsActivity.this, "Shift data saved successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AdminShiftsActivity.this, "Failed to save shift data", Toast.LENGTH_SHORT).show();
                });
    }
}
