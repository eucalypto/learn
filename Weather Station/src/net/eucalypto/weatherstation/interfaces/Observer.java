package net.eucalypto.weatherstation.interfaces;

public interface Observer {
    void update(float temp, float humidity, float pressure);
}
