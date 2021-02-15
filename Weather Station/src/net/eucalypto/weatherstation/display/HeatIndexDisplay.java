package net.eucalypto.weatherstation.display;

import net.eucalypto.weatherstation.interfaces.DisplayElement;
import net.eucalypto.weatherstation.interfaces.Observer;
import net.eucalypto.weatherstation.interfaces.Subject;

public class HeatIndexDisplay implements DisplayElement, Observer {
    private float temp;
    private float humidity;
    private float heatIndex;
    private Subject subject;


    public HeatIndexDisplay(Subject subject) {
        this.subject = subject;

        subject.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.printf("Heat index is %f%n", heatIndex);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temp = temp;
        this.humidity = humidity;

        heatIndex = calculateHeatIndex(temp, humidity);
        display();
    }

    private float calculateHeatIndex(float temp, float humidity) {
        float c1 = -8.785f;
        float c2 = 1.611f;
        float c3 = 2.339f;
        float c4 = -0.146f;
        float c5 = -0.0123f;
        float c6 = -0.0164f;
        float c7 = 0.00221f;
        float c8 = 0.000725f;
        float c9 = -0.000003582f;

        return c1
                + c2 * temp
                + c3 * humidity
                + c4 * temp * humidity
                + c5 * temp * temp
                + c6 * humidity * humidity
                + c7 * temp * temp * humidity
                + c8 * temp * humidity * humidity
                + c9 * temp * temp * humidity * humidity;
    }
}
