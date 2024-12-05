package com.example.mannatsandroidlab;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<ChatMessage> messageList = new ArrayList<>();
    private ChatMessageDatabase database; // Reference to Room database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_main);

        // Handle edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize database, RecyclerView, and adapter
        database = ChatMessageDatabase.getInstance(this);
        recyclerView = findViewById(R.id.recycler_view); // Ensure this ID matches your layout
        adapter = new MessageAdapter(this, messageList);
        recyclerView.setAdapter(adapter);

        // Load messages from database
        loadMessages();

        // Handle item long press for deletion
        adapter.setOnItemLongClickListener((message, position) -> showDeleteConfirmationDialog(message, position));
    }

    private void loadMessages() {
        // Load messages from the database in a background thread
        new Thread(() -> {
            List<ChatMessage> messagesFromDb = database.chatMessageDao().getAllMessages(); // Fetch messages
            runOnUiThread(() -> {
                messageList.addAll(messagesFromDb);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    private void showDeleteConfirmationDialog(ChatMessage message, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Message")
                .setMessage("Are you sure you want to delete this message?")
                .setPositiveButton("Yes", (dialog, which) -> deleteMessage(message, position))
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void deleteMessage(ChatMessage message, int position) {
        // Remove from the list
        messageList.remove(position);
        adapter.notifyItemRemoved(position);

        // Remove from the database
        new Thread(() -> database.chatMessageDao().deleteMessage(message)).start();

        // Show Snackbar with UNDO option
        showUndoSnackbar(message, position);
    }

    private void showUndoSnackbar(ChatMessage deletedMessage, int position) {
        Snackbar.make(recyclerView, "Message deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", v -> {
                    // Re-add the message to the list
                    messageList.add(position, deletedMessage);
                    adapter.notifyItemInserted(position);

                    // Reinsert into the database
                    new Thread(() -> database.chatMessageDao().insertMessage(deletedMessage)).start();
                })
                .show();
    }
}
