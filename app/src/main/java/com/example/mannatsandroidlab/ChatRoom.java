package com.example.mannatsandroidlab;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.mannatsandroidlab.ActivityChatRoomBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatRoom extends AppCompatActivity {
    private com.example.mannatsandroidlab.ChatRoomViewModel chatModel;
    private ArrayList<ChatMessage> messages = new ArrayList<>();
    private com.example.mannatsandroidlab.ChatAdapter adapter;
    private com.example.mannatsandroidlab.ActivityChatRoomBinding binding;

    private ChatMessageDAO chatMessageDAO; // DAO for database operations
    private Executor thread; // Background thread for database operations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Step 5: Initialize the Database
        MessageDatabase db = Room.databaseBuilder(
                getApplicationContext(),
                MessageDatabase.class,
                "chat_database"
        ).build();
        chatMessageDAO = db.chatMessageDAO(); // Get DAO
        thread = Executors.newSingleThreadExecutor(); // Initialize executor for background tasks

        // Test the database
        testDatabase();

        // Step 6: Perform Database Operations - Fetch messages from database
        thread.execute(() -> {
            List<ChatMessage> dbMessages = chatMessageDAO.getAllMessages();
            runOnUiThread(() -> {
                messages.addAll(dbMessages); // Add messages to RecyclerView
                adapter.notifyDataSetChanged();
            });
        });

        // Initialize ViewBinding
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Step 7: Handle RecyclerView and User Interactions
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Adapter with message list
        adapter = new ChatAdapter(messages, message -> {
            // On message click, display a toast (Optional)
            String getMessageText = message.getMessageText(); // Use the getter method
            Toast.makeText(this, "Selected: " + getMessageText, Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        // Handle Send Button
        binding.sendButton.setOnClickListener(view -> {
            String messageText = binding.editMessage.getText().toString();
            if (!messageText.isEmpty()) {
                ChatMessage newMessage = new ChatMessage(messageText, true);
                messages.add(newMessage);
                adapter.notifyItemInserted(messages.size() - 1);

                // Insert new message into the database
                thread.execute(() -> chatMessageDAO.insertMessage(newMessage));

                binding.editMessage.setText(""); // Clear input field
            }
        });

        // Handle Receive Button
        binding.receiveButton.setOnClickListener(view -> {
            String messageText = binding.editMessage.getText().toString();
            if (!messageText.isEmpty()) {
                ChatMessage newMessage = new ChatMessage(messageText, false);
                messages.add(newMessage);
                adapter.notifyItemInserted(messages.size() - 1);

                // Insert new message into the database
                thread.execute(() -> chatMessageDAO.insertMessage(newMessage));

                binding.editMessage.setText(""); // Clear input field
            }
        });

        // Handle Delete Button
        binding.deleteButton.setOnClickListener(view -> {
            if (!messages.isEmpty()) {
                ChatMessage messageToDelete = messages.get(messages.size() - 1); // Select the last message
                messages.remove(messageToDelete);
                adapter.notifyItemRemoved(messages.size());

                // Delete message from database
                thread.execute(() -> chatMessageDAO.deleteMessage(messageToDelete));
            }
        });
    }

    /**
     * Test the database by inserting a sample message and fetching all messages.
     */
    private void testDatabase() {
        thread.execute(() -> {
            // Insert a sample message
            ChatMessage sampleMessage = new ChatMessage("Test Message", true);
            long id = chatMessageDAO.insertMessage(sampleMessage);
            Log.d("ChatRoom", "Inserted message with ID: " + id);

            // Fetch all messages
            List<ChatMessage> dbMessages = chatMessageDAO.getAllMessages();
            for (ChatMessage msg : dbMessages) {
                Log.d("ChatRoom", "Message: " + msg.getMessageText() + ", Timestamp: " + msg.getTimestamp());
            }
        });
    }
}
