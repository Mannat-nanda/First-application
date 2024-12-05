package com.example.mannatsandroidlab;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mannatsandroidlab.ActivityChatRoomBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatRoom extends AppCompatActivity {
    private ChatRoomViewModel chatModel;
    private ArrayList<ChatMessage> messages = new ArrayList<>();
    private ChatAdapter adapter;
    private ActivityChatRoomBinding binding;

    private ChatMessageDAO chatMessageDAO; // DAO for database operations
    private Executor thread; // Background thread for database operations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Step 1: Define RoomDatabase.Callback
        RoomDatabase.Callback callback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull androidx.sqlite.db.SupportSQLiteDatabase db) {
                super.onCreate(db);
                Log.d("ChatRoom", "Database created");
                thread.execute(() -> {
                    chatMessageDAO.insertMessage(new ChatMessage("Welcome to the chat!", true));
                });
            }
        };

        // Step 2: Build the Room Database and attach the callback
        MessageDatabase db = Room.databaseBuilder(
                        getApplicationContext(),
                        MessageDatabase.class,
                        "chat_database"
                ).addCallback(callback) // Attach callback
                .build();

        chatMessageDAO = db.chatMessageDAO(); // Get DAO
        thread = Executors.newSingleThreadExecutor(); // Initialize executor for background tasks

        // Step 3: Perform initial database operations
        thread.execute(() -> {
            List<ChatMessage> dbMessages = chatMessageDAO.getAllMessages();
            runOnUiThread(() -> {
                messages.addAll(dbMessages); // Add messages to RecyclerView
                adapter.notifyDataSetChanged();
            });
        });

        // Step 4: Initialize ViewBinding
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Step 5: Initialize RecyclerView and Adapter
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(messages, message -> {
            String getMessageText = message.getMessageText();
            Toast.makeText(this, "Selected: " + getMessageText, Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Step 6: Handle Send Button
        binding.sendButton.setOnClickListener(view -> {
            String messageText = binding.editMessage.getText().toString();
            if (!messageText.isEmpty()) {
                ChatMessage newMessage = new ChatMessage(messageText, true);
                messages.add(newMessage);
                adapter.notifyItemInserted(messages.size() - 1);

                thread.execute(() -> chatMessageDAO.insertMessage(newMessage));
                binding.editMessage.setText("");
            }
        });

        // Step 7: Handle Receive Button
        binding.receiveButton.setOnClickListener(view -> {
            String messageText = binding.editMessage.getText().toString();
            if (!messageText.isEmpty()) {
                ChatMessage newMessage = new ChatMessage(messageText, false);
                messages.add(newMessage);
                adapter.notifyItemInserted(messages.size() - 1);

                thread.execute(() -> chatMessageDAO.insertMessage(newMessage));
                binding.editMessage.setText("");
            }
        });

        // Step 8: Handle Delete Button
        binding.deleteButton.setOnClickListener(view -> {
            if (!messages.isEmpty()) {
                ChatMessage messageToDelete = messages.get(messages.size() - 1);
                messages.remove(messageToDelete);
                adapter.notifyItemRemoved(messages.size());

                thread.execute(() -> chatMessageDAO.deleteMessage(messageToDelete));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_1) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete ChatMessage")
                    .setMessage("Are you sure you want to delete this message?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Deletion logic (optional)
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else if (id == R.id.item_about) {
            Toast.makeText(this, "Version 1.0, created by YourName", Toast.LENGTH_SHORT).show();
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
