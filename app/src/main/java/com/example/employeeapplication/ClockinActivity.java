package com.example.employeeapplication;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class ClockinActivity extends AppCompatActivity {
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private TextView clockInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
        setContentView(R.layout.activity_clockin);
        handleIntent(getIntent());
        //processNdefIntent(getIntent());
         //Initialize NFC adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // NFC is not supported on this device
            Toast.makeText(this, "You cannot clock in on this device", Toast.LENGTH_SHORT).show();
        }

        // Create a PendingIntent to handle NFC events
        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);

        // Reference to TextView for displaying clock-in message
        clockInText = findViewById(R.id.clockInText);
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
        }
    }
    private NdefMessage createNdefMessage(boolean clockIn) {
        Intent clockIntent = new Intent(this, ClockinActivity.class);
        if (clockIn) {
            // Clock In
            clockIntent.putExtra("clockType", "in");
            startActivity(clockIntent);
            String payload = "com.example.employeeapplication.Clockin";
            byte[] payloadBytes = payload.getBytes();
            NdefRecord record = NdefRecord.createExternal("com.example.employeeapplication",
                    "clockin", payloadBytes);
            return new NdefMessage(new NdefRecord[]{record});
        } else {
            // Clock Out
            clockIntent.putExtra("clockType", "out");
            startActivity(clockIntent);
            String payload = "com.example.employeeapplication.Clockout";
            byte[] payloadBytes = payload.getBytes();
            NdefRecord record = NdefRecord.createExternal("com.example.employeeapplication",
                    "clockout", payloadBytes);
            return new NdefMessage(new NdefRecord[]{record});
        }
    }
    private void writeDataToTag(Tag tag, boolean clockIn) {
        NdefMessage message = createNdefMessage(clockIn);
        if (message != null) {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                try {
                    ndef.connect();
                    ndef.writeNdefMessage(message);
                    if (clockIn) {
                        Toast.makeText(this, "You have clocked in!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "You have clocked out!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        ndef.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        handleIntent(getIntent());
        processNdefIntent(getIntent());
    }
    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            String clockType = intent.getStringExtra("clockType");
            if ("in".equals(clockType)) {
                processClockIn();
            } else if ("out".equals(clockType)) {
                processClockOut();
            }
        } else {
            Toast.makeText(this, "You have clocked in!", Toast.LENGTH_SHORT).show();
        }
    }

    private void processNdefIntent(Intent intent) {
        // Handle the NFC tag content here and display clock-in information
        clockInText.setText("You are Clocked in\n" + getCurrentTimeAndDate());
        //saveTimeAndDate(getCurrentTimeAndDate());
    }
    private String getCurrentTimeAndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void clockOut(View view) {

        String currentTime = getCurrentTimeAndDate();

        // You need to replace "your_firebase_reference" with your actual Firebase reference
        DatabaseReference clockOutRef = FirebaseDatabase.getInstance().getReference().child("clock_out_times").push();
        clockOutRef.child("time").setValue(currentTime);

        Toast.makeText(this, "You have clocked out at " + currentTime, Toast.LENGTH_SHORT).show();
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

        // Update clock in status and time in Firebase Realtime Database
        String employeeId = "ochv3pOEJiabJjvYySg059ujoWk1"; // Replace with actual employee ID
        String shiftId = "shift1"; // Replace with actual shift ID

        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference().child("employees").child(employeeId).child("shifts").child(shiftId);
        employeeRef.child("clockInStatus").setValue("yes");
        employeeRef.child("clockInTime").setValue(currentTime);
    }

    private void processClockOut() {
        // Handle clock out logic here
        // Display clock out image and time

        // Display clock out image
        ImageView imageView = findViewById(R.id.clockImageView);
        imageView.setImageResource(R.drawable.ic_clockout);

        // Display clock out time
        TextView clockInText = findViewById(R.id.clockInText);
        String currentTime = getCurrentTimeAndDate();
        clockInText.setText("You are Clocked out\n" + currentTime);

        // Update clock out status and time in Firebase Realtime Database
        String employeeId = "ochv3pOEJiabJjvYySg059ujoWk1"; // Replace with actual employee ID
        String shiftId = "shift1"; // Replace with actual shift ID

        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference().child("employees").child(employeeId).child("shifts").child(shiftId);
        employeeRef.child("clockInStatus").setValue("no");
        employeeRef.child("clockInTime").setValue(currentTime);
    }
}
