# Flamapp.ai Android Assignment 2

##  Q1. N-Queens Problem

1. Uses backtracking to explore all possible configurations
2. In each call goes to next row and calls backtracking for each valid position in the row.
3. Columns, Diagonal1 and Diagona2 are used as constraints

##  Q2. Circular Dependency Checker
1. This problem simply translates to DFS for cycles in dependency graph


##  Q4. Weather App
### WeatherTrack

WeatherTrack is an Android app that fetches and displays weather data, including a weekly summary with daily weather conditions. The app uses Room for local data storage and WorkManager to periodically fetch weather updates every 6 hours.

### Features

- Fetches current and hourly weather data from a mock API(Random Generation of weather conditions in real time, not from static JSON).
- Stores weather data locally using Room database.
- Displays weekly weather summary with daily max/min temperature and weather conditions.
- Periodic background updates every 6 hours using WorkManager.
- Simple, clean UI with RecyclerView.

### Architecture

- MVVM pattern with Repository, ViewModel, and Room database.
- Background tasks handled by WorkManager(WeatherWorker).
- LiveData for reactive UI updates.
- Language: Java

  ![image](https://github.com/user-attachments/assets/916d6d3b-c7b7-4b4c-a9ba-b7c8015c7a2e)


### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/WeatherTrack.git
