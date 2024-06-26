package com.example.employeeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
        setContentView(R.layout.activity_main1);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        // Check if the current user is null before accessing their UID
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userUid = currentUser.getUid();
            DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("employees").child(userUid);

        } else {
            Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
        }
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
