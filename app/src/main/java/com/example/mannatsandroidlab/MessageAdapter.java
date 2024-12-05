package com.example.mannatsandroidlab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private final Context context;
    private final List<ChatMessage> messageList; // Use ChatMessage instead of Message
    private OnItemLongClickListener longClickListener;

    // Constructor
    public MessageAdapter(Context context, List<ChatMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    // Interface for long-click callback
    public interface OnItemLongClickListener {
        void onItemLongClick(ChatMessage message, int position); // Use ChatMessage
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage chatMessage = messageList.get(position); // Get ChatMessage from list
        String messageContent = chatMessage.getMessageText(); // Get the message text

        holder.messageTextView.setText(messageContent); // Bind the message text to the TextView

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(chatMessage, position); // Pass ChatMessage to callback
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size(); // Return the size of the message list
    }

    // ViewHolder class
    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.message_text_view); // Ensure this ID matches your XML
        }
    }
}
