package com.example.weathertrack.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.weathertrack.model.Weather;

import androidx.lifecycle.LiveData;

import java.util.List;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeather(Weather weather);

    // Use LiveData to observe changes
    @Query("SELECT * FROM weather ORDER BY timestamp DESC")
    LiveData<List<Weather>> getAllWeather();

    @Query("SELECT * FROM weather WHERE timestamp BETWEEN :startTimestamp AND :endTimestamp ORDER BY timestamp ASC")
    LiveData<List<Weather>> getWeatherBetween(long startTimestamp, long endTimestamp);

    @Query("SELECT * FROM weather ORDER BY timestamp DESC LIMIT 1")
    LiveData<Weather> getLatestWeather();
}
