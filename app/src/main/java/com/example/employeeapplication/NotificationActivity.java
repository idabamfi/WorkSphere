package com.example.employeeapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NotificationActivity extends AppCompatActivity {
    private FirebaseListAdapter<NotificationMessage> adapter;
    private DatabaseReference notificationRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
        setContentView(R.layout.activity_notifications);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        notificationRef = FirebaseDatabase.getInstance().getReference().child("notifications");

        ImageButton sendButton = findViewById(R.id.sendButton);
        final EditText messageInput = findViewById(R.id.messageInput);
        ListView messageList = findViewById(R.id.messageList);

        Query query = notificationRef.orderByChild("timestamp").limitToLast(50);
        FirebaseListOptions<NotificationMessage> options = new FirebaseListOptions.Builder<NotificationMessage>()
                .setQuery(query, NotificationMessage.class)
                .setLayout(R.layout.message_item)
                .build();

        adapter = new FirebaseListAdapter<NotificationMessage>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull NotificationMessage model, int position) {
                TextView messageTextView = v.findViewById(R.id.messageText);
                TextView timeTextView = v.findViewById(R.id.timeText); // Add this line
                messageTextView.setText(model.getMessage());
                timeTextView.setText(model.getTimestamp()); // Add this line
            }
        };

        messageList.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageInput.getText().toString().trim();
                if (!TextUtils.isEmpty(messageText)) {
                    // Send the message
                    sendMessage(messageText, currentUser.getUid());
                    messageInput.setText("");
                }
            }
        });
    }
    private void sendMessage(String messageText, String senderId) {
        DatabaseReference newMessageRef = notificationRef.push();
        String messageId = newMessageRef.getKey();
        if (messageId != null) {
            String timestamp = getCurrentTime(); // Get current timestamp
            NotificationMessage message = new NotificationMessage(messageId, timestamp, messageText, senderId);
            newMessageRef.setValue(message);
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date());
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
