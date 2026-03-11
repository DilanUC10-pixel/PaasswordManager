package com.example.paasswordmanager.models;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Account.class}, version = 1, exportSchema = false)
public abstract class AccountsRoomDB extends RoomDatabase {

    public abstract AccountDao accountDao();

    private static volatile AccountsRoomDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AccountsRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AccountsRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AccountsRoomDB.class, "accounts_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
