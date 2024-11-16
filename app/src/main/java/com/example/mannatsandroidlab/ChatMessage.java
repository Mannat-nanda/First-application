package com.example.mannatsandroidlab;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {
    private String message;  // Message text
    private String timeSent;  // Time the message was sent
    private boolean isSentButton;  // True if the message is sent, false if received

    // Constructor to create a new ChatMessage
    public ChatMessage(String message, boolean isSentButton) {
        this.message = message;
        this.timeSent = getCurrentTime();
        this.isSentButton = isSentButton;
    }

    // Method to get the current time in a desired format
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(" hh:mm a ,  dd/MM/yyyy"); // Format: "12:00 PM"
        return sdf.format(new Date()); // Return the formatted current time
    }



    // Getters for message properties
    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSentButton() {
        return isSentButton;
    }
}