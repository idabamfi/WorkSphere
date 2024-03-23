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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NotificationActivity extends AppCompatActivity {
    private FirebaseRecyclerAdapter<NotificationMessage, NotificationViewHolder> adapter;
    private DatabaseReference notificationRef, employeeRef, managerRef;;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light);
        setContentView(R.layout.activity_notifications);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        notificationRef = FirebaseDatabase.getInstance().getReference().child("notifications");

        // Initialize DatabaseReference for employee and manager details
        employeeRef = FirebaseDatabase.getInstance().getReference().child("employees");
        managerRef = FirebaseDatabase.getInstance().getReference().child("management");

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

                // Set the sender name (employee name or manager name) based on the senderId
                if (model.getSenderId().equals(currentUser.getUid())) {
                    // Current user sent the message, display "Me" as the sender
                    holder.senderTextView.setText("Me");
                }

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
        TextView senderTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageText);
            timeTextView = itemView.findViewById(R.id.timeText);
            senderTextView = itemView.findViewById(R.id.senderText);
        }
    }
    private void sendMessage(String messageText, String senderId) {
        DatabaseReference newMessageRef = notificationRef.push();
        String messageId = newMessageRef.getKey();
        if (messageId != null) {
            String timestamp = getCurrentTime(); // Get current timestamp

            // Fetch employee/manager details based on senderId
            DatabaseReference senderRef;
            if (senderId.startsWith("employee")) {
                senderRef = employeeRef.child(senderId);
            } else if (senderId.startsWith("manager")) {
                senderRef = managerRef.child(senderId);
            } else {
                // Handle unexpected sender ID
                return;
            }
            // Read employee/manager details and send the message
            senderRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String senderName = dataSnapshot.child("employeeName").getValue(String.class);
                        if (senderName == null) {
                            senderName = dataSnapshot.child("managerName").getValue(String.class);
                        }
                        if (senderName != null) {
                            // Message senderName along with the messageText
                            String updatedMessageText = senderName + ": " + messageText;

                            // Create and send the message
                            NotificationMessage message = new NotificationMessage(messageId, timestamp, updatedMessageText, senderId);
                            newMessageRef.setValue(message);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle error
                }
            });
        }

    }
    private String getSenderNameFromId(String senderId, TextView senderTextView) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(senderId);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String senderName = dataSnapshot.child("employeeName").getValue(String.class);
                    if (senderName != null) {
                        senderTextView.setText(senderName);
                    } else if (dataSnapshot.hasChild("managerName")) {
                        // Manager name exists
                        senderName = dataSnapshot.child("managerName").getValue(String.class);
                    } if (senderName != null) {
                        senderTextView.setText(senderName);
                    } else {
                        senderTextView.setText("Unknown");
                    }
                } else {
                    senderTextView.setText("Unknown");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

        // Return a placeholder name while waiting for the database query to complete
        return "Unknown";
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
