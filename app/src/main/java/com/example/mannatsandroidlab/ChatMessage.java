package com.example.mannatsandroidlab;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {

    @PrimaryKey(autoGenerate = true)
    private int id; // Unique ID for each message

    @ColumnInfo(name = "message_text")
    private String messageText; // Message content

    @ColumnInfo(name = "is_sent")
    private boolean isSent; // True for sent messages, false for received

    @ColumnInfo(name = "timestamp")
    private String timestamp; // Timestamp when the message was created

    // Default constructor for Room
    public ChatMessage() {}

    // Constructor to initialize a new ChatMessage
    public ChatMessage(String messageText, boolean isSent) {
        this.messageText = messageText;
        this.isSent = isSent;
        this.timestamp = getCurrentTime(); // Automatically set the timestamp
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText; // Getter for messageText
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText; // Setter for messageText
    }

    public boolean isSent() {
        return isSent; // Getter for isSent
    }

    public void setSent(boolean sent) {
        isSent = sent; // Setter for isSent
    }

    public String getTimestamp() {
        return timestamp; // Getter for timestamp
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp; // Setter for timestamp
    }

    // Utility method to get the current timestamp
    private String getCurrentTime() {
        return java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
    }
}
