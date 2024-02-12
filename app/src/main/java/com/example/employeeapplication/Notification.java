package com.example.employeeapplication;

public class Notification {
    private String timestamp;
    private String message;
    private String senderId;

    // Constructor without parameters
    public Notification() {
    }

    // Constructor with parameters
    public Notification(String timestamp, String message, String senderId) {
        this.timestamp = timestamp;
        this.message = message;
        this.senderId = senderId;
    }

    // Getters and setters
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
