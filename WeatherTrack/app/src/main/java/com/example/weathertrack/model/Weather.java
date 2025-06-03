package com.example.weathertrack.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "weather")
public class Weather {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String city;

    private float temperature;

    private float minTemp;

    private float maxTemp;
    private int humidity;

    private String condition;

    private long timestamp;  // Unix time in milliseconds

    // Constructor
    public Weather(@NonNull String city, float temperature, float minTemp, float maxTemp, int humidity, String condition, long timestamp) {
        this.city = city;
        this.temperature = temperature;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
