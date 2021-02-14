package net.eucalypto.weatherstation.display;

import net.eucalypto.weatherstation.interfaces.DisplayElement;
import net.eucalypto.weatherstation.interfaces.Observer;
import net.eucalypto.weatherstation.data.WeatherData;

public class CurrentConditionsDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private WeatherData weatherData;


    public CurrentConditionsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.printf("Current conditions: %.1f Degrees Celsius and %.1f%% humidity%n", temperature, humidity);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }
}
