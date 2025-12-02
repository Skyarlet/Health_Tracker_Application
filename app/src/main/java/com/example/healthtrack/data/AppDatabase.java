package com.example.healthtrack.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.healthtrack.model.HealthStatusRecord;
import com.example.healthtrack.model.User;

/**
 * Main Room Database definition.
 */
@Database(entities = {HealthStatusRecord.class, User.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "health_status_room";
    private static volatile AppDatabase INSTANCE;

    public abstract HealthStatusRecordDao healthStatusRecordDao();
    public abstract UserDao userDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    //fallbackToDestructiveMigration allows DB rebuild if version changes(simple for dev)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
