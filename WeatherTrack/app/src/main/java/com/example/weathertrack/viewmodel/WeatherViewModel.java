package com.example.weathertrack.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.example.weathertrack.model.Weather;
import com.example.weathertrack.repository.WeatherRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class WeatherViewModel extends ViewModel {

    private final WeatherRepository repository;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final LiveData<List<Weather>> weeklyWeather;
    private final LiveData<Weather> latestWeather;

    public WeatherViewModel(WeatherRepository repository) {
        this.repository = repository;

        weeklyWeather = repository.getWeeklyWeather();
        latestWeather = repository.getLatestWeather();
    }

    public void refreshWeather(String city) {
        executorService.execute(() -> repository.fetchAndSaveWeather(city));
    }

    public LiveData<List<Weather>> getWeeklyWeather() {
        return weeklyWeather;
    }

    public LiveData<Weather> getLatestWeather() {
        return latestWeather;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final WeatherRepository repository;

        public Factory(WeatherRepository repository) {
            this.repository = repository;
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(WeatherViewModel.class)) {
                return (T) new WeatherViewModel(repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }

    }
}
