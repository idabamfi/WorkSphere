package com.example.employeeapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
        setContentView(R.layout.activity_main1);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("employees").child(userUid);

        employeeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Employee data exists, retrieve it
                    Employee employee = dataSnapshot.getValue(Employee.class);
                    // Do something with the employee data
                } else {
                    // Employee data not found
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
    public void logIn(View view){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        navigateToHomepageActivity();
                        Toast.makeText(MainActivity.this, "You are Signed in", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Sign in Unsuccessful, Please re-check your credentials ", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void navigateToHomepageActivity() {
        Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
        startActivity(intent);
        finish();
    }


}
