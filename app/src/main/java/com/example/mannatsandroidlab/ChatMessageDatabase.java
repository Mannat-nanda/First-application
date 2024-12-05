package com.example.mannatsandroidlab;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ChatMessage.class}, version = 1, exportSchema = false)
public abstract class ChatMessageDatabase extends RoomDatabase {

    private static volatile ChatMessageDatabase INSTANCE;

    public abstract ChatMessageDAO chatMessageDao();

    public static ChatMessageDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ChatMessageDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ChatMessageDatabase.class, "chat_message_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
