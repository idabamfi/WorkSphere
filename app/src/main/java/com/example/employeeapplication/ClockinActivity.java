package com.example.employeeapplication;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
            writeDataToTag(tag);
            handleIntent(intent);
        }
    }
    private NdefMessage createNdefMessage() {
        // Create a custom payload to identify and trigger the app when scanned
        Intent clockInIntent = new Intent(this, ClockinActivity.class);
        startActivity(clockInIntent);
        String payload = "com.example.employeeapplication.Clockin";
        byte[] payloadBytes = payload.getBytes();
        NdefRecord record = NdefRecord.createExternal("com.example.employeeapplication",
                "clockin", payloadBytes);
        return new NdefMessage(new NdefRecord[]{record});
    }
    private void writeDataToTag(Tag tag) {
        NdefMessage message = createNdefMessage();
        if (message != null) {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                try {
                    ndef.connect();
                    ndef.writeNdefMessage(message);
                    Toast.makeText(this, "You have clocked in !", Toast.LENGTH_SHORT).show();
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
            processNdefIntent(intent);
            Intent clockInIntent = new Intent(this, ClockinActivity.class);
            startActivity(clockInIntent);
        } else {
            Toast.makeText(this, "You have clocked in !", Toast.LENGTH_SHORT).show();
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
//    private void saveTimeAndDate(String currentTimeAndDate) {
//        // Save the time and date in SharedPreferences or any other persistent storage mechanism
//        SharedPreferences preferences = getSharedPreferences("ClockinPrefs", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("lastClockInTime", currentTimeAndDate);
//        editor.apply();
//    }
}
