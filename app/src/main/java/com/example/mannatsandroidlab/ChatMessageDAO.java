package com.example.mannatsandroidlab;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatMessageDAO {

    @Query("SELECT * FROM ChatMessage")
    List<ChatMessage> getAllMessages();

    @Insert
    long insertMessage(ChatMessage message);

    @Delete
    void deleteMessage(ChatMessage message);
}
