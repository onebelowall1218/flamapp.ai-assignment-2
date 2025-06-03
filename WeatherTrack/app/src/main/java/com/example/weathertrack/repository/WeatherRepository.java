package com.example.weathertrack.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.weathertrack.data.local.WeatherDao;
import com.example.weathertrack.data.remote.MockWeatherApi;
import com.example.weathertrack.model.Weather;

import java.util.Calendar;
import androidx.lifecycle.Transformations;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;


public class WeatherRepository {
    private final WeatherDao weatherDao;
    private final MockWeatherApi mockApi;

    public WeatherRepository(WeatherDao weatherDao, MockWeatherApi mockApi) {
        this.weatherDao = weatherDao;
        this.mockApi = mockApi;
    }

    public void fetchAndSaveWeather(String city) {
        try {
            long timestamp = System.currentTimeMillis();
            Weather weather = mockApi.getWeatherForCity(city, timestamp);
            weatherDao.insertWeather(weather);
        } catch (Exception e) {
            Log.e("WeatherRepository", "API fetch error: " + e.getMessage());
        }
    }

    public void generateAndInsertInitialData(String city) {
        List<Weather> pastWeather = mockApi.generatePastMonthHourlyWeather(city);
        for (Weather w : pastWeather) {
            weatherDao.insertWeather(w);
        }
    }

    public LiveData<List<Weather>> getDailySummary(long startTimestamp, long endTimestamp) {
        return Transformations.map(weatherDao.getWeatherBetween(startTimestamp, endTimestamp), weatherList -> {
            Map<String, Weather> dailyMap = new LinkedHashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            for (Weather weather : weatherList) {
                String dayKey = sdf.format(new Date(weather.getTimestamp()));

                if (!dailyMap.containsKey(dayKey) ||
                        weather.getTimestamp() > dailyMap.get(dayKey).getTimestamp()) {
                    dailyMap.put(dayKey, weather);
                }
            }

            return new ArrayList<>(dailyMap.values());
        });
    }

    public LiveData<List<Weather>> getWeeklyWeather() {
        long now = System.currentTimeMillis();
        long sevenDaysAgo = now - 7L * 24 * 60 * 60 * 1000;

        return getDailySummary(sevenDaysAgo, now);
    }


    public LiveData<Weather> getLatestWeather() {
        return weatherDao.getLatestWeather();
    }

}
