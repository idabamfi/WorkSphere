package com.example.employeeapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminClockInTimesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ShiftAdapter shiftAdapter;
    private List<Shift> shiftsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_clockintimes);

        recyclerView = findViewById(R.id.recyclerViewShifts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shiftsList = new ArrayList<>();
        shiftAdapter = new ShiftAdapter(shiftsList);
        recyclerView.setAdapter(shiftAdapter);

        // Get the current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference shiftsReference = FirebaseDatabase.getInstance().getReference()
                    .child("employees").child(userId).child("shifts");

            shiftsReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    shiftsList.clear();
                    for (DataSnapshot shiftSnapshot : dataSnapshot.getChildren()) {
                        Shift shift = shiftSnapshot.getValue(Shift.class);
                        shift.setEmployeeId(userId); // Set employee UID
                        shift.setEmployeeName(currentUser.getDisplayName()); // Set employee name
                        shiftsList.add(shift);
                    }
                    shiftAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(AdminClockInTimesActivity.this, "Failed to retrieve shifts: " +
                            databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
