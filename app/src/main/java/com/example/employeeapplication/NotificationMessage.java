package com.example.employeeapplication;

public class NotificationMessage {
    private String messageId;
    private String timestamp;
    private String message;
    private String senderId;

    public NotificationMessage() {
        // Default constructor required for Firebase
    }
    public NotificationMessage(String messageId, String timestamp, String message, String senderId) {
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.message = message;
        this.senderId = senderId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}


