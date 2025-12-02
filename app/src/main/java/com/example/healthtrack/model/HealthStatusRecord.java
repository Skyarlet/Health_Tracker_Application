package com.example.healthtrack.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Health Status 数据模型类
 * 字段：
 *  - id: 自增主键
 *  - date: 事件所属日期，格式 "yyyy-MM-dd"（比如 "2025-11-28"）
 *  - sleep_time: 当天的睡眠时间（比如“8.4”，单位是小时）
 *  - screen_time: 当天的手机屏幕使用时间（比如“10.2”，单位同样是小时）
 *  - steps: 当天的行走步数（比如“5000”，单位是步）
 *  - weight：记录每天的体重的变化（比如”72.8“，单位是kg）
 */
@Entity(tableName = "health_status_records")
public class HealthStatusRecord {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String username; //Foreign key (logical) to User
    private String date;
    private float sleep_time;
    private float screen_time;
    private int steps;
    private float weight;

    public HealthStatusRecord() {}

    public HealthStatusRecord(String username, String date, float sleep_time, float screen_time, int steps, float weight) {
        this.username = username;
        this.date = date;
        this.sleep_time = sleep_time;
        this.screen_time = screen_time;
        this.steps = steps;
        this.weight = weight;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public float getSleep_time() { return sleep_time; }
    public void setSleep_time(float sleep_time) { this.sleep_time = sleep_time; }

    public float getScreen_time() { return screen_time; }
    public void setScreen_time(float screen_time) { this.screen_time = screen_time; }

    public int getSteps() { return steps; }
    public void setSteps(int steps) { this.steps = steps; }

    public float getWeight(){ return weight; }
    public void setWeight(float weight){ this.weight = weight; }

    @NonNull
    @Override
    public String toString() {
        return "HealthStatusRecord{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", sleep_time='" + sleep_time + '\'' +
                ", screen_time='" + screen_time + '\'' +
                ", steps=" + steps + '\'' +
                ", weight=" + weight +
                '}';
    }
}

