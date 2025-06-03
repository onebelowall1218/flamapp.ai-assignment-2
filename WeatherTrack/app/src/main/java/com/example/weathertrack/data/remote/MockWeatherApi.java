package com.example.weathertrack.data.remote;

import com.example.weathertrack.model.Weather;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class MockWeatherApi {

    private static final String[] CONDITIONS = {"Sunny", "Cloudy", "Rainy", "Stormy", "Snowy", "Windy", "Foggy"};
    private final Random random = new Random();

    public Weather getWeatherForCity(String city, long timestamp) {

        float temp = 15 + random.nextFloat() * 15; // 15°C to 30°C
        int humidity = 40 + random.nextInt(60);    // 40% to 100%
        String condition = CONDITIONS[random.nextInt(CONDITIONS.length)];

//        long timestamp = System.currentTimeMillis();

        float minTemp = temp - random.nextFloat() * 5;
        float maxTemp = temp + random.nextFloat() * 5;

        return new Weather(city, temp, minTemp, maxTemp, humidity, condition, timestamp);
    }
    public List<Weather> generatePastMonthHourlyWeather(String city) {
        List<Weather> weatherList = new ArrayList<>();
        long now = System.currentTimeMillis();
        long oneHourMillis = 60 * 60 * 1000;

        for (int i = 720; i >= 1; i--) {
            long timestamp = now - (i * oneHourMillis);

            float temp = 15 + random.nextFloat() * 15; // 15°C to 30°C. Can change if I need it.
            int humidity = 40 + random.nextInt(60);    // 40% to 100%. Can change if I need it.
            String condition = CONDITIONS[random.nextInt(CONDITIONS.length)];
            float minTemp = temp - random.nextFloat() * 5;
            float maxTemp = temp + random.nextFloat() * 5;

            Weather w = new Weather(city, temp, minTemp, maxTemp, humidity, condition, timestamp);
            weatherList.add(w);
        }

        return weatherList;
    }

}
