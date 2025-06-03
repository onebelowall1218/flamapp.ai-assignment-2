package com.example.weathertrack.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.weathertrack.R;
import com.example.weathertrack.data.local.WeatherDao;
import com.example.weathertrack.data.local.WeatherDatabase;
import com.example.weathertrack.data.remote.MockWeatherApi;
import com.example.weathertrack.model.Weather;
import com.example.weathertrack.repository.WeatherRepository;
import com.example.weathertrack.ui.adapters.WeatherSummaryAdapter;
import com.example.weathertrack.viewmodel.WeatherViewModel;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import android.content.SharedPreferences;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.ExistingPeriodicWorkPolicy;

import java.util.concurrent.TimeUnit;

import com.example.weathertrack.workers.WeatherWorker;

import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private WeatherViewModel viewModel;

    private TextView tvCity, tvTemperature, tvHumidity, tvCondition, tvError;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvWeeklySummary;
    private WeatherSummaryAdapter adapter;

    private final String defaultCity = "Guwahati";  // I can change this.

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        setContentView(R.layout.activity_main);

        // Refresh every 6 hour (Schedule)
        PeriodicWorkRequest weatherWork =
                new PeriodicWorkRequest.Builder(WeatherWorker.class, 6, TimeUnit.HOURS)
                        .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "WeatherFetchWork",
                ExistingPeriodicWorkPolicy.KEEP,
                weatherWork
        );
        tvCity = findViewById(R.id.tvCity);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvCondition = findViewById(R.id.tvCondition);
        tvError = findViewById(R.id.tvError);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        rvWeeklySummary = findViewById(R.id.rvWeeklySummary);

        rvWeeklySummary.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherSummaryAdapter();
        rvWeeklySummary.setAdapter(adapter);

        WeatherDao weatherDao = WeatherDatabase.getInstance(getApplicationContext()).weatherDao();
        MockWeatherApi mockApi = new MockWeatherApi();
        WeatherRepository repository = new WeatherRepository(weatherDao, mockApi);

        viewModel = new ViewModelProvider(this, new WeatherViewModel.Factory(repository))
                .get(WeatherViewModel.class);

        SharedPreferences prefs = getSharedPreferences("weather_prefs", MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("first_run", true);

        if (isFirstRun) {
            new Thread(() -> {
                repository.generateAndInsertInitialData(defaultCity);
                prefs.edit().putBoolean("first_run", false).apply();
            }).start();
        }


        viewModel.getLatestWeather().observe(this, weather -> {
            if (weather != null) {
                tvError.setVisibility(View.GONE);
                updateCurrentWeatherUI(weather);
            } else {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("No current weather data.");
            }
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        });

        viewModel.getWeeklyWeather().observe(this, weatherList -> {
            if (weatherList != null && !weatherList.isEmpty()) {
                Collections.reverse(weatherList);
                adapter.setWeatherList(weatherList);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            progressBar.setVisibility(View.VISIBLE);
            viewModel.refreshWeather(defaultCity);
        });

        progressBar.setVisibility(View.VISIBLE);
        viewModel.refreshWeather(defaultCity);
    }

    private void changeBackgroundBasedOnCondition(String condition) {
        ImageView backgroundImage = findViewById(R.id.backgroundImage);

        int backgroundRes;

        switch (condition.toLowerCase()) {
            case "sunny":
                backgroundRes = R.drawable.bg_sunny;
                break;
            case "cloudy":
                backgroundRes = R.drawable.bg_cloudy;
                break;
            case "rainy":
                backgroundRes = R.drawable.bg_rainy;
                break;
            case "stormy":
                backgroundRes = R.drawable.bg_stormy;
                break;
            case "snowy":
                backgroundRes = R.drawable.bg_snowy;
                break;
            case "windy":
                backgroundRes = R.drawable.bg_windy;
                break;
            case "foggy":
                backgroundRes = R.drawable.bg_foggy;
                break;
            default:
                backgroundRes = R.drawable.bg_sunny;
                break;
        }

        backgroundImage.setImageResource(backgroundRes);
    }

    private void updateCurrentWeatherUI(Weather weather) {
        tvCity.setText(weather.getCity());
        tvTemperature.setText(String.format(Locale.getDefault(), "%.1f°C", weather.getTemperature()));
        tvHumidity.setText("Humidity: " + weather.getHumidity() + "%");
        tvCondition.setText(weather.getCondition());
        changeBackgroundBasedOnCondition(weather.getCondition());
    }
}
