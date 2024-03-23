package com.example.employeeapplication;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClockinActivity extends AppCompatActivity {
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private TextView clockInText;
    private Button clockOutButton;

    private boolean clockedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
        setContentView(R.layout.activity_clockin);

        // Reference to TextView for displaying clock-in message
        clockInText = findViewById(R.id.clockInText);

        handleIntent(getIntent());

        // Initialize NFC adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // NFC is not supported on this device
            Toast.makeText(this, "You cannot clock in on this device", Toast.LENGTH_SHORT).show();
        }

        // Initialize clockOutButton after setContentView
        clockOutButton = findViewById(R.id.clockOutButton);

        clockOutButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                processClockOut();
            }
        });

        // Create a PendingIntent to handle NFC events
        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);
        // Initial message
        clockInText.setText("Scan to clock in");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            // Tag is discovered, handle clock-in process
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            handleIntent(intent);
            processClockIn();
        }
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        clockInText.setText("");
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            String clockType = intent.getStringExtra("clockType");
            if ("in".equals(clockType)) {
                processClockIn();
            } else if ("out".equals(clockType)) {
                processClockOut();
            }
        }
    }

//    private void processNdefIntent(Intent intent) {
//        // Handle the NFC tag content here and display clock-in information
//        clockInText.setText("You are Clocked in\n" + getCurrentTimeAndDate());
//    }

    private String getCurrentTimeAndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void processClockIn() {
        // Handle clock in logic here
        // Display clock in image and time

        // Display clock in image (tick)
        ImageView imageView = findViewById(R.id.clockImageView);
        imageView.setImageResource(R.drawable.ic_green_tick);

        // Display clock in time
        TextView clockInText = findViewById(R.id.clockInText);
        String currentTime = getCurrentTimeAndDate();
        clockInText.setText("You are Clocked in\n" + currentTime);

        Toast.makeText(this, "You have clocked in!", Toast.LENGTH_SHORT).show();

        // Update clock in status and time in Firebase Realtime Database
        String employeeId = "o7AtMzLaprRALRtdtccVvELFn6z2";
        String shiftId = "shift1";

        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference().child("employees").child(employeeId).child("shifts").child(shiftId);
        employeeRef.child("clockInStatus").setValue("yes");
        employeeRef.child("clockInTime").setValue(currentTime);

        // Show the clock out button after clocking in
        clockOutButton.setVisibility(View.VISIBLE);
    }

    private void processClockOut() {
        // Handle clock out logic here
        // Display clock out image and time

        // Display clock out image
        ImageView imageView = findViewById(R.id.clockImageView);
        imageView.setImageResource(R.drawable.ic_clockout);

        // Display clock out time
        String currentTime = getCurrentTimeAndDate();
        clockInText.setText("You are Clocked out\n" + currentTime);

        // Update clock out time in Firebase Realtime Database
        DatabaseReference clockOutRef = FirebaseDatabase.getInstance().getReference().child("clock_out_times").push();
        clockOutRef.child("time").setValue(currentTime);

        Toast.makeText(this, "You have clocked out at " + currentTime, Toast.LENGTH_SHORT).show();
    }
}
