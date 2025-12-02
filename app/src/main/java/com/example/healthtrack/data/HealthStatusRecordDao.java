package com.example.healthtrack.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.healthtrack.model.HealthStatusRecord;
import java.util.List;

@Dao
public interface HealthStatusRecordDao {
    @Insert
    long insert(HealthStatusRecord record);

    @Query("SELECT * FROM health_status_records WHERE username = :username ORDER BY date DESC LIMIT 4")
    LiveData<List<HealthStatusRecord>> getRecentRecords(String username);
}
