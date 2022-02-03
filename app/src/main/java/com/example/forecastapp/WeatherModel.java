package com.example.forecastapp;

public class WeatherModel {

    private String time;
    private double  windspeed;
    private String icon;
    private double temperature;

    public WeatherModel(String time, double windspeed, String icon, double  temperature) {
        this.time = time;
        this.windspeed = windspeed;
        this.icon = icon;
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double  getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
