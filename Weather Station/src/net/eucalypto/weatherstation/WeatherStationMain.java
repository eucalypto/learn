package net.eucalypto.weatherstation;

import net.eucalypto.weatherstation.data.WeatherData;
import net.eucalypto.weatherstation.display.CurrentConditionsDisplay;
import net.eucalypto.weatherstation.display.ForecastDisplay;
import net.eucalypto.weatherstation.display.HeatIndexDisplay;
import net.eucalypto.weatherstation.display.StatisticsDisplay;

public class WeatherStationMain {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);
        HeatIndexDisplay heatIndexDisplay = new HeatIndexDisplay(weatherData);


        weatherData.setMeasurements(10, 65, 30.4f);
        weatherData.setMeasurements(22, 70, 29.2f);
        weatherData.setMeasurements(-3, 90, 29.2f);
    }
}
