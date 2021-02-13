package net.eucalypto;

public class WeatherStation {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);


        weatherData.setMeasurements(10, 65, 30.4f);
        weatherData.setMeasurements(22, 70, 29.2f);
        weatherData.setMeasurements(-3, 90, 29.2f);
    }
}
