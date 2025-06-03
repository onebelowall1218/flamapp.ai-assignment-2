package com.example.weathertrack.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertrack.R;
import com.example.weathertrack.model.Weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherSummaryAdapter extends RecyclerView.Adapter<WeatherSummaryAdapter.ViewHolder> {

    private List<Weather> weatherList;

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherSummaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather_summary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherSummaryAdapter.ViewHolder holder, int position) {
        if (weatherList == null || weatherList.size() <= position) return;

        Weather weather = weatherList.get(position);

        holder.tvDay.setText(getDayLabel(weather.getTimestamp()));
        String minMax = String.format(Locale.getDefault(), "%.1f° - %.1f°", weather.getMinTemp(), weather.getMaxTemp());
        holder.tvMinMax.setText(minMax);
        holder.tvCondition.setText(weather.getCondition());
        int iconRes = getIconForCondition(weather.getCondition());
        holder.ivConditionIcon.setImageResource(iconRes);
    }

    private int getIconForCondition(String condition) {
        switch (condition.toLowerCase()) {
            case "sunny":
                return R.drawable.ic_sunny;
            case "cloudy":
                return R.drawable.ic_cloudy;
            case "rainy":
                return R.drawable.ic_rainy;
            case "stormy":
                return R.drawable.ic_stormy;
            case "snowy":
                return R.drawable.ic_snowy;
            case "windy":
                return R.drawable.ic_windy;
            case "foggy":
                return R.drawable.ic_foggy;
            default:
                return R.drawable.ic_sunny;
        }
    }


    @Override
    public int getItemCount() {
        return weatherList == null ? 0 : weatherList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay, tvMinMax, tvCondition;
        ImageView ivConditionIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvMinMax = itemView.findViewById(R.id.tvMinMax);
            tvCondition = itemView.findViewById(R.id.tvCondition);
            ivConditionIcon = itemView.findViewById(R.id.ivConditionIcon);
        }
    }

    private String getDayLabel(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE", Locale.getDefault());

        long now = System.currentTimeMillis();
        long oneDay = 24 * 60 * 60 * 1000L;

        if (isSameDay(timestamp, now)) {
            return "Today";
        } else if (isSameDay(timestamp, now + oneDay)) {
            return "Tomorrow";
        } else {
            return sdf.format(date);
        }
    }

    private boolean isSameDay(long ts1, long ts2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return sdf.format(new Date(ts1)).equals(sdf.format(new Date(ts2)));
    }
}
