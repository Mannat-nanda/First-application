package com.example.mannatsandroidlab;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ChatMessage.class}, version = 1, exportSchema = false)
public abstract class MessageDatabase extends RoomDatabase {

    private static volatile MessageDatabase INSTANCE;

    public abstract ChatMessageDAO chatMessageDAO();

    public static MessageDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MessageDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MessageDatabase.class, "message_database")
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull androidx.sqlite.db.SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.d("MessageDatabase", "Database created");
                                }
                            })
                            .fallbackToDestructiveMigration() // Handles version changes safely
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
