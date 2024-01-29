package com.example.employeeapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        firebaseAuth = FirebaseAuth.getInstance();
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
