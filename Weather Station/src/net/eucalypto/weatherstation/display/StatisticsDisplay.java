package net.eucalypto.weatherstation.display;

import net.eucalypto.weatherstation.interfaces.DisplayElement;
import net.eucalypto.weatherstation.interfaces.Observer;
import net.eucalypto.weatherstation.data.WeatherData;

public class StatisticsDisplay implements Observer, DisplayElement {
    private float maxTemp = 0.0f;
    private float minTemp = 200;
    private float tempSum = 0.0f;
    private int numReadings;
    private WeatherData weatherData;

    public StatisticsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.weatherData.registerObserver(this);
    }


    @Override
    public void display() {
        System.out.printf("Avg/Max/Min temperature = %.1f/%.1f/%.1f%n",
                (tempSum / numReadings), maxTemp, minTemp);
    }

    @Override
    public void update() {
        float temp = weatherData.getTemperature();
        tempSum += temp;
        numReadings++;
        if (temp > maxTemp)
            maxTemp = temp;

        if (temp < minTemp)
            minTemp = temp;

        display();
    }
}
