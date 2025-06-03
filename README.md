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
- Scroll down to refresh manually

### Architecture

- MVVM pattern with Repository, ViewModel, and Room database.
- Background tasks handled by WorkManager(WeatherWorker).
- LiveData for reactive UI updates.
- Language: Java
  
<p float="left">
  <img src="https://github.com/user-attachments/assets/db87f78c-8a25-44f3-96d1-12a8e4969c51" width="200" alt="Home Screen"/>
  <img src="https://github.com/user-attachments/assets/2edb9a09-ad12-45df-8942-265731911506" width="200" alt="Weather App Main"/>
  <img src="https://github.com/user-attachments/assets/4ab2de93-9877-4226-b8a9-b010dbcaa903" width="200" alt="Weather App Weekly"/>
  <img src="https://github.com/user-attachments/assets/5e237960-78fd-4025-8ca7-91cf375ee53d" width="200" alt="Weather App Daily"/>
</p>



### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/WeatherTrack.git
