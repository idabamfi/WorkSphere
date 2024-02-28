package com.example.employeeapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
    private FirebaseRecyclerAdapter<NotificationMessage, NotificationViewHolder> adapter;
    private DatabaseReference notificationRef;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
        setContentView(R.layout.activity_notifications);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        notificationRef = FirebaseDatabase.getInstance().getReference().child("notifications");

        ImageButton sendButton = findViewById(R.id.sendButton);
        final EditText messageInput = findViewById(R.id.messageInput);
        RecyclerView recyclerView = findViewById(R.id.messageList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true); // Display the latest message at the bottom
        recyclerView.setLayoutManager(layoutManager);

        Query query = notificationRef.orderByChild("timestamp").limitToLast(50);
        FirebaseRecyclerOptions<NotificationMessage> options =
                new FirebaseRecyclerOptions.Builder<NotificationMessage>()
                        .setQuery(query, NotificationMessage.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<NotificationMessage, NotificationViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull NotificationViewHolder holder, int position, @NonNull NotificationMessage model) {
                holder.messageTextView.setText(model.getMessage());
                holder.timeTextView.setText(model.getTimestamp());
            }

            @NonNull
            @Override
            public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
                return new NotificationViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);

        sendButton.setOnClickListener(v -> {
            String messageText = messageInput.getText().toString().trim();
            if (!TextUtils.isEmpty(messageText)) {
                // Send the message
                sendMessage(messageText, currentUser.getUid());
                messageInput.setText("");
            }
        });
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView timeTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageText);
            timeTextView = itemView.findViewById(R.id.timeText);
        }
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
