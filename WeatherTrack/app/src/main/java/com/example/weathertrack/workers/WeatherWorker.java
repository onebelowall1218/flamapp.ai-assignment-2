package com.example.weathertrack.workers;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.weathertrack.data.local.WeatherDatabase;
import com.example.weathertrack.repository.WeatherRepository;
import com.example.weathertrack.data.remote.MockWeatherApi;

public class WeatherWorker extends Worker {

    public WeatherWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        WeatherRepository repository = new WeatherRepository(
                WeatherDatabase.getInstance(getApplicationContext()).weatherDao(),
                new MockWeatherApi()
        );

        repository.fetchAndSaveWeather("SampleCity");
        return Result.success();
    }
}
