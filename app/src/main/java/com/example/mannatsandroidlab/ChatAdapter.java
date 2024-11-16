package com.example.mannatsandroidlab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyRowHolder> {

    private ArrayList<ChatMessage> messages;  // List of chat messages

    // Constructor to initialize the adapter with the messages list
    public ChatAdapter(ArrayList<ChatMessage> messages) {
        this.messages = messages;
    }

    // Create new row view in the RecyclerView
    @NonNull
    @Override
    public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        // Inflate the appropriate layout based on whether the message is sent or received
        if (viewType == 0) {  // Sent message
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_message, parent, false);
        } else {  // Received message
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recieve_message, parent, false);
        }

        // Return a new holder for the row view
        return new MyRowHolder(view);
    }

    // Bind data to the row view
    @Override
    public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
        // Get the chat message at the given position
        ChatMessage message = messages.get(position);
        // Bind the message text and time to the TextViews in the row
        holder.messageText.setText(message.getMessage());
        holder.timeText.setText(message.getTimeSent());
    }

    // Returns the total number of messages
    @Override
    public int getItemCount() {
        return messages.size();
    }

    // Determines the type of view for each item (sent or received message)
    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messages.get(position);
        return message.isSentButton() ? 0 : 1;  // 0 for sent, 1 for received
    }

    // Inner class that holds references to the views in each row
    public class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        // Constructor to bind the TextViews to the row layout
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);  // Message text view
            timeText = itemView.findViewById(R.id.timeDate);  // Time text view
        }
    }
}