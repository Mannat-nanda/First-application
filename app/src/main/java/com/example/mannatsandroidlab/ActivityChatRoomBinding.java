package com.example.mannatsandroidlab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mannatsandroidlab.R;

public class ActivityChatRoomBinding {

    private static ViewGroup parent;
    // Views from the layout
    public RecyclerView recyclerView;  // RecyclerView to display chat messages
    public View sendButton;  // Button to send messages
    public EditText editMessage;  // EditText to type messages
    public View receiveButton;

    // Private constructor to prevent direct instantiation
    private ActivityChatRoomBinding(View root) {
        // Initialize views using the root view
        recyclerView = root.findViewById(R.id.recyclerView);
        sendButton = root.findViewById(R.id.sendButton);
        editMessage = root.findViewById(R.id.editMessage);
        receiveButton = root.findViewById(R.id.receiveButton);
    }

    // Static method to inflate the binding from the layout
    public static ActivityChatRoomBinding inflate(LayoutInflater inflater) {
        // Inflate the layout and create an instance of the binding
        View view = inflater.inflate(R.layout.activity_chat_room, parent, false);
        return new ActivityChatRoomBinding(view);
    }

    // Method to return the root view of the layout
    public View getRoot() {
        return recyclerView.getRootView();
    }
}